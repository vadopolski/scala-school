package java2scala.tagless

trait FilterTF[T] {
  def colorIs(color: String): T
  def priceLT(upper: BigDecimal): T
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
    }

  implicit def filterInt: FilterTF[Product => Boolean] =
    new FilterTF[Product => Boolean] {
      def colorIs(color: String): Product => Boolean     = _.color == color
      def priceLT(upper: BigDecimal): Product => Boolean = _.price <= upper
    }

  def isRed[T: FilterTF]: T   = colorIs[T]("red")
  def priceLT100[T: FilterTF] = FilterTF[T].priceLT(100)

  println(isRed[String])
  println(priceLT100[String])

  val products = List(Product("yellow", 50), Product("red", 50), Product("red", 200))
  println(products.filter(isRed[Product => Boolean]))
  println(products.filter(priceLT100[Product => Boolean]))

}
