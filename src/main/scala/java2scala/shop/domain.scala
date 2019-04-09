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

@JsonCodec
final case class Price(
    minerals: BigDecimal,
    gas: BigDecimal,
    supply: BigDecimal
)

@JsonCodec
final case class User(
    id: UUID,
    name: String
)

@JsonCodec
final case class ProductItem(
    productId: UUID,
    quantity: BigDecimal
)

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
final case class MultipleProducts(id: UUID) extends StacklessException(s"Product $id not found")
final case class CartNotFound(id: UUID) extends StacklessException(s"Cart $id not found")
