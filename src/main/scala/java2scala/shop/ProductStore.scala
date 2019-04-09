package java2scala.shop
import java.io.{BufferedReader, InputStreamReader}
import java.util.UUID

import akka.stream.scaladsl.Source
import cats.effect.{ContextShift, IO}
import io.circe.parser._

import scala.collection.JavaConverters._
import cats.implicits._

import scala.concurrent.ExecutionContext

trait ProductStore {
  def all: IO[List[Product]]

  def getById(id: UUID): IO[Option[Product]]

  def byId(id: UUID): IO[Product] = getById(id).flatMap(_.liftTo[IO](ProductNotFound(id)))
}

object ProductStore {
  private class ListStore(products: List[Product], byIdMap: Map[UUID, Product]) extends ProductStore {
    def all: IO[List[Product]]                 = IO.pure(products)
    def getById(id: UUID): IO[Option[Product]] = IO.pure(byIdMap.get(id))
  }

  private def makeListStore(products: List[Product]): IO[ListStore] =
    products
      .groupBy(_.id)
      .toList
      .traverse {
        case (id, List(product)) => IO.pure(id -> product)
        case (id, _)             => IO.raiseError(MultipleProducts(id))
      }
      .map(byId => new ListStore(products, byId.toMap))

  def fromResource(name: String, blocking: ExecutionContext)(implicit basic: ContextShift[IO]): IO[ProductStore] = {
    val read: IO[List[Product]] = IO {
      val stream = getClass.getResourceAsStream(name)
      val reader = new BufferedReader(new InputStreamReader(stream))
      val total  = reader.lines().iterator.asScala.mkString
      decode[List[Product]](total): Either[Throwable, List[Product]]
    }.rethrow

    for {
      _        <- IO.shift(blocking)
      products <- read
      store    <- makeListStore(products)
      _        <- IO.shift(basic)
    } yield store
  }
}
