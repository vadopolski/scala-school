package java2scala.tagless

sealed trait Filter

final case class Product(
    color: String,
    price: BigDecimal
)

object Filter extends App {
  final case class ColorIs(color: String)     extends Filter
  final case class PriceLT(upper: BigDecimal) extends Filter
  final case class And(x: Filter, y: Filter)  extends Filter

  def show(filter: Filter): String = filter match {
    case ColorIs(color) => s"color = $color"
    case PriceLT(upper) => s"price <= $upper"
    case And(x, y)      => s"${show(x)} AND ${show(y)} "
  }

  def filter(flt: Filter)(product: Product): Boolean = flt match {
    case ColorIs(color) => product.color == color
    case PriceLT(upper) => product.price <= upper
    case And(x, y)      => filter(x)(product) && filter(y)(product)
  }

  val isRed: Filter = ColorIs("red")
  val priceLT100    = PriceLT(100)
  val combined      = And(isRed, priceLT100)

  println(show(isRed))
  println(show(priceLT100))
  println(show(combined))

  val products = List(Product("yellow", 50), Product("red", 50), Product("red", 200))
  println(products.filter(filter(isRed)))
  println(products.filter(filter(priceLT100)))
  println(products.filter(filter(combined)))
}
