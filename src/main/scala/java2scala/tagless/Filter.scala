package java2scala.tagless

import java2scala.tagless.Filter.{And, ColorIs, PriceLT}
import java2scala.tagless.OrFilter.Simple

sealed trait Filter

final case class Product(
    color: String,
    price: BigDecimal
)

object Filter {
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

}

sealed trait OrFilter

object OrFilter extends App {
  final case class Simple(x: Filter)            extends OrFilter
  final case class Or(x: OrFilter, y: OrFilter) extends OrFilter

  def show(flt: OrFilter): String = flt match {
    case Simple(x) => Filter.show(x)
    case Or(x, y)  => s"${show(x)} OR ${show(y)}"
  }

  def filter(flt: OrFilter)(product: Product): Boolean =
    flt match {
      case Simple(x) => Filter.filter(x)(product)
      case Or(x, y)  => filter(x)(product) || filter(y)(product)
    }

}

object FilterApp extends App {
  lazy val isRed: Filter = ColorIs("red")
  lazy val priceLT100    = PriceLT(100)
  lazy val combined      = And(isRed, priceLT100)

  println(Filter.show(isRed))
  println(Filter.show(priceLT100))
  println(Filter.show(combined))

  lazy val products = List(Product("yellow", 50), Product("red", 50), Product("red", 200))
  println(products.filter(Filter.filter(isRed)))
  println(products.filter(Filter.filter(priceLT100)))
  println(products.filter(Filter.filter(combined)))

  lazy val fltOr: OrFilter = OrFilter.Or(Simple(combined), Simple(ColorIs("yellow")))
  println("-" * 10 + "[ OR ]" + "-" * 10)

  println(OrFilter.show(fltOr))

  println(products.filter(OrFilter.filter(fltOr)))
}
