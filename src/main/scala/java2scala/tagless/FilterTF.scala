package java2scala.tagless

import java2scala.tagless.FilterTF.colorIs




trait FilterTF[T] {
  def colorIs(color: String): T
  def priceLT(upper: BigDecimal): T
  def and(x: T, y: T): T
}

object FilterTF extends App {
  def apply[T](implicit filterTF: FilterTF[T]): FilterTF[T] = filterTF

  def colorIs[T](color: String)(implicit fi: FilterTF[T]): T =
    fi.colorIs(color)
  def priceLT[T](upper: BigDecimal)(implicit fi: FilterTF[T]): T =
    fi.priceLT(upper)

  implicit def showInt[T]: FilterTF[String] =
    new FilterTF[String] {
      def colorIs(color: String): String     = s"color = $color"
      def priceLT(upper: BigDecimal): String = s"price <= $upper"
      def and(x: String, y: String): String  = s"($x) AND ($y)"
    }

//  type ProductPred = Product => Boolean

  implicit def filterInt: FilterTF[Product => Boolean] =
    new FilterTF[Product => Boolean] {
      def colorIs(color: String): Product => Boolean     = _.color == color
      def priceLT(upper: BigDecimal): Product => Boolean = _.price <= upper
      def and(x: Product => Boolean, y: Product => Boolean): Product => Boolean =
        p => x(p) && y(p)
    }

}

trait Or[T] {
  def or(x: T, y: T): T
}

object Or {
  def apply[T](implicit or: Or[T]): Or[T] = or

  implicit def showInt: Or[String] = new Or[String] {
    def or(x: String, y: String): String = s"($x) OR ($y)"
  }

  implicit def filterInt: Or[Product => Boolean] = new Or[Product => Boolean] {
    def or(x: Product => Boolean, y: Product => Boolean): Product => Boolean =
      p => x(p) || y(p)
  }
}

object FilterTFApp extends App {
  def isRed[T: FilterTF]: T      = colorIs[T]("red")
  def priceLT100[T: FilterTF]: T = FilterTF[T].priceLT(100)
  def combined[T: FilterTF]: T   = FilterTF[T].and(isRed[T], priceLT100[T])

  println(isRed[String])
  println(priceLT100[String])
  println(combined[String])

  val products = List(Product("yellow", 50), Product("red", 50), Product("red", 200))
  println(products.filter(isRed[Product => Boolean]))
  println(products.filter(priceLT100[Product => Boolean]))
  println(products.filter(combined[Product => Boolean]))

  def fltOr[T: FilterTF: Or]: T =
    Or[T].or(combined, FilterTF.colorIs("yellow"))

  def fltOr2[T: FilterTF: Or]: T =
    FilterTF[T].and(
      Or[T].or(
        FilterTF[T].colorIs("yellow"), FilterTF[T].colorIs("red")),
      FilterTF[T].priceLT(60)
    )

  println("-" * 10 + "[ OR ]" + "-" * 10)

  println(fltOr[String])
  println(fltOr2[String])

  println(products.filter(fltOr[Product => Boolean]))
  println(products.filter(fltOr2[Product => Boolean]))

}
