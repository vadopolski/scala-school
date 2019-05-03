package java2scala.shop
import java.util.UUID

import io.circe.derivation.annotations.JsonCodec
import cats.implicits._

@JsonCodec
final case class Product(
    id: UUID,
    name: String,
    description: String,
    price: Option[Price]
)

object Product {
  implicit val key: Key[Product] = new Key[Product] {
    def key(a: Product): UUID           = a.id
    def notFound(uuid: UUID): Throwable = ProductNotFound(uuid)

    def multiple(uuid: UUID): Throwable = MultipleProducts(uuid)
  }
}

@JsonCodec
final case class Price(
    minerals: Option[BigDecimal],
    gas: Option[BigDecimal],
    supply: Option[BigDecimal]
)

@JsonCodec
final case class User(
    id: UUID,
    name: String
)
object User {
  implicit val key: Key[User] = new Key[User] {
    def key(a: User): UUID              = a.id
    def notFound(uuid: UUID): Throwable = UserNotFound(uuid)
    def multiple(uuid: UUID): Throwable = MultipleUsers(uuid)
  }
}

@JsonCodec
final case class ProductItem(
    productId: UUID,
    quantity: BigDecimal
)

object ProductItem {
//  implicit val key: Key[ProductItem] = new Key[ProductItem] {
//    def key(a: ProductItem): UUID       = a.productId
//    def notFound(uuid: UUID): Throwable = ProductNotFound(uuid)
//
//    def multiple(uuid: UUID): Throwable = MultipleProducts  }
}

@JsonCodec
final case class Cart(
    id: UUID,
    user: UUID,
    items: Map[UUID, BigDecimal]
) {
  def addProduct(id: UUID, quantity: BigDecimal): Cart =
    copy(items = items + (id -> items.get(id).fold(quantity)(_ + quantity)))

  def removeProduct(id: UUID): Cart = copy(items = items - id)
}

abstract class StacklessException(message: String) extends Exception(message, null, false, false)

final case class ProductNotFound(id: UUID)  extends StacklessException(s"Product $id not found")
final case class MultipleProducts(id: UUID) extends StacklessException(s"Multiple products with id $id")
final case class CartNotFound(id: UUID)     extends StacklessException(s"Cart $id not found")
final case class UserNotFound(id: UUID)     extends StacklessException(s"User $id not found")
final case class MultipleUsers(id: UUID)    extends StacklessException(s"Multiple users with id $id")
