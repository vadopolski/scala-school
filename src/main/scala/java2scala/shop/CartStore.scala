package java2scala.shop
import java.util.UUID

import cats.data.OptionT
import cats.effect.IO
import cats.effect.concurrent.Ref
import java2scala.shop.IOCartStore.State
import cats.implicits._

trait CartStore {
  def getOrCreate(user: UUID): IO[Cart]
  def get(id: UUID): IO[Cart]
  def putProduct(cart: UUID, product: UUID, quantity: BigDecimal): IO[Unit]
  def removeProduct(cart: UUID, product: UUID): IO[Unit]
}

object CartStore {}

final case class IOCartStore(state: Ref[IO, State]) extends CartStore {

  def getOrCreate(user: UUID): IO[Cart] =
    OptionT(state.get.map(_.byUser.get(user))).getOrElseF {
      for {
        id  <- IO(UUID.randomUUID())
        ref <- Ref[IO].of(Cart(id, user, Map()))
        res <- state.modify(_.addNewCartRef(id, user, ref))
      } yield res
    }.flatMap(_.get)

  private def getRef(id: UUID): IO[Ref[IO, Cart]] =
    state.get.flatMap(_.byID.get(id).liftTo[IO](CartNotFound(id)))

  private def updateRef(id: UUID)(f: Cart => Cart): IO[Unit] = getRef(id).flatMap(_.update(f))

  def get(id: UUID): IO[Cart] = getRef(id).flatMap(_.get)

  def putProduct(cart: UUID, product: UUID, quantity: BigDecimal): IO[Unit] =
    updateRef(cart)(_.addProduct(product, quantity))
  def removeProduct(cart: UUID, product: UUID): IO[Unit] =
    updateRef(cart)(_.removeProduct(product))
}

object IOCartStore {
  final case class State(byID: Map[UUID, Ref[IO, Cart]], byUser: Map[UUID, Ref[IO, Cart]]) {
    def addNewCartRef(id: UUID, userId: UUID, cartRef: Ref[IO, Cart]): (State, Ref[IO, Cart]) =
      byUser.get(userId) match {
        case Some(ref) => this -> ref
        case None      => State(byID + (id -> cartRef), byUser + (userId -> cartRef)) -> cartRef
      }
  }
}
