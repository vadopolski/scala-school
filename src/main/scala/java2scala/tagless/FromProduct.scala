package java2scala.tagless

import cats.{Eq, Order, Show}
import simulacrum.typeclass
import cats.syntax.order._
import cats.syntax.show._
import cats.instances.string._
import cats.instances.bigDecimal._
import Conditional.ops._
import FromProduct.ops._
import ToProduct.ops._

@typeclass
trait FromProduct[F[_]] {
  def price: F[BigDecimal]
  def color: F[String]
}

object FromProduct {
  implicit def show: FromProduct[ShowF] = new FromProduct[ShowF] {
    def price: String = "[price]"
    def color: String = "[color]"
  }
  implicit def prod: FromProduct[ProdF] = new FromProduct[ProdF] {
    def price: ProdF[BigDecimal] = p => p.price
    def color: ProdF[String]     = p => p.color
  }
}

trait Comparison[F[_], A] {
  def eq1(x: F[A], y: F[A]): F[Boolean]
  def gt(x: F[A], y: F[A]): F[Boolean]
}

object Comparison {
  def apply[F[_], A](implicit comp: Comparison[F, A]): Comparison[F, A] = comp

  implicit def show[A]: Comparison[ShowF, A] = new Comparison[ShowF, A] {
    def eq1(x: ShowF[A], y: ShowF[A]): ShowF[Boolean] = s"$x == $y"
    def gt(x: ShowF[A], y: ShowF[A]): ShowF[Boolean]  = s"$x <= $y"
  }

  implicit def prod[A: Order]: Comparison[ProdF, A] = new Comparison[ProdF, A] {
    def eq1(x: ProdF[A], y: ProdF[A]): ProdF[Boolean] = p => x(p) === y(p)
    def gt(x: ProdF[A], y: ProdF[A]): ProdF[Boolean]  = p => x(p) >= y(p)
  }
}

trait Const[F[_], A] {
  def value(a: A): F[A]
}

object Const {
  def apply[F[_], A](implicit cnst: Const[F, A]) = cnst

  implicit def show[A: Show]: Const[ShowF, A] = new Const[ShowF, A] {
    def value(a: A): ShowF[A] = a.show
  }

  implicit def prod[A]: Const[ProdF, A] = new Const[ProdF, A] {
    def value(a: A): ProdF[A] = _ => a
  }
}

@typeclass
trait ToProduct[F[_]] {
  def product(price: F[BigDecimal], color: F[String]): F[Product]
}

object ToProduct {
  implicit val show: ToProduct[ShowF] = new ToProduct[ShowF] {
    def product(price: String, color: String): String =
      s"Product(price = $price, color = $color"
  }

  implicit val prod: ToProduct[ProdF] = new ToProduct[ProdF] {
    def product(price: ProdF[BigDecimal], color: ProdF[String]): ProdF[Product] =
      p => Product(color(p), price(p))
  }
}

@typeclass
trait Conditional[F[_]] {
  def ifThenElse[A](cond: F[Boolean], th: F[A], el: F[A]): F[A]
}

object Conditional {
  implicit val show: Conditional[ShowF] = new Conditional[ShowF] {
    def ifThenElse[A](cond: String, th: String, el: String): String =
      s"if ($cond) then $th else $el"
  }

  implicit val prod: Conditional[ProdF] = new Conditional[ProdF] {
    def ifThenElse[A](cond: ProdF[Boolean], th: ProdF[A], el: ProdF[A]): ProdF[A] =
      p => if (cond(p)) th(p) else el(p)
  }
}

object TypedTF extends App {
  def mutate[
      F[_]: FromProduct: ToProduct: Const[?[_], String]: Const[?[_], BigDecimal]: Conditional: Comparison[?[_], String]] =
    Conditional[F].ifThenElse(
      Comparison[F, String]
        .eq1(FromProduct[F].color, Const[F, String].value("red")),
      ToProduct[F].product(
        FromProduct[F].price,
        Const[F, String].value("yellow")
      ),
      ToProduct[F].product(
        Const[F, BigDecimal].value(100),
        FromProduct[F].color
      )
    )

  println(mutate[ShowF])

  println(mutate[ProdF].apply(Product(color = "red", price = 50)))
  println(mutate[ProdF].apply(Product(color = "yellow", price = 50)))

}
