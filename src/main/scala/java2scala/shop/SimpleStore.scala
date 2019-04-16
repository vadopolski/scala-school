package java2scala.shop
import java.io.{BufferedReader, InputStreamReader}
import java.util.UUID

import cats.effect.{ContextShift, IO}
import io.circe.Decoder
import cats.implicits._

import scala.collection.JavaConverters._
import io.circe.parser._

import scala.concurrent.ExecutionContext

trait SimpleStore[A] {
  def all: IO[List[A]]

  def getById(id: UUID): IO[Option[A]]

  def byId(id: UUID): IO[A]
}

object SimpleStore {

  private class ListStore[A: Key](items: List[A], byIdMap: Map[UUID, A]) extends SimpleStore[A] {
    def all: IO[List[A]]                 = IO.pure(items)
    def getById(id: UUID): IO[Option[A]] = IO.pure(byIdMap.get(id))
    def byId(id: UUID): IO[A]            = getById(id).flatMap(_.liftTo[IO](Key.notFound[A](id)))
  }

  private def makeListStore[A: Key](items: List[A]): IO[ListStore[A]] =
    items
      .groupBy(Key.key[A])
      .toList
      .traverse {
        case (id, List(a)) => IO.pure(id -> a)
        case (id, _)       => IO.raiseError(Key.multiple[A](id))
      }
      .map(byId => new ListStore(items, byId.toMap))

  def fromResource[A: Key: Decoder](name: String, blocking: ExecutionContext)(
      implicit basic: ContextShift[IO]): IO[SimpleStore[A]] = {
    val read: IO[List[A]] = IO {
      val stream = getClass.getResourceAsStream(name)
      val reader = new BufferedReader(new InputStreamReader(stream))
      val total  = reader.lines().iterator.asScala.mkString
      decode[List[A]](total): Either[Throwable, List[A]]
    }.rethrow

    for {
      _        <- IO.shift(blocking)
      products <- read
      store    <- makeListStore(products)
      _        <- IO.shift(basic)
    } yield store
  }

}
