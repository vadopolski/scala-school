package java2scala.tagless

sealed trait Filter

final case class Product(
    color: String,
    price: BigDecimal
)

object Filter extends App {
  final case class ColorIs(color: String)     extends Filter
  final case class PriceLT(upper: BigDecimal) extends Filter

  def show(filter: Filter): String = filter match {
    case ColorIs(color) => s"color = $color"
    case PriceLT(upper) => s"price <= $upper"
  }

  def filter(filter: Filter)(product: Product): Boolean = filter match {
    case ColorIs(color) => product.color == color
    case PriceLT(upper) => product.price <= upper
  }

  val isRed: Filter = ColorIs("red")
  val priceLT100    = PriceLT(100)

  println(show(isRed))
  println(show(priceLT100))

  val products = List(Product("yellow", 50), Product("red", 50), Product("red", 200))
  println(products.filter(filter(isRed)))
  println(products.filter(filter(priceLT100)))
}
