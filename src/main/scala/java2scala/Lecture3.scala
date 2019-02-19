package java2scala

object Lecture3 extends App {
  import Color._
  sealed trait Color {
    def name: String = this match {
      case Red   => "red"
      case Blue  => "blue"
      case Green => "green"
    }
  }

  object Color {
    case object Red   extends Color
    case object Blue  extends Color
    case object Green extends Color
  }

//  def main(args: Array[String]): Unit = {
//    println(Red.name)
//    println(Green.name)
//  }
//  val VeryRed = Mix(Red, Red)
  val veryRed = Mix(Red, Red)

  sealed trait GenColor {
    def show: String = this match {
      case White                           => "white"
      case Black                           => "black"
      case Base(gen)                       => s"base color ${gen.name}"
      case Mix(Green, Green)               => s"the greenest"
      case `veryRed`                       => s"the redest"
      case Mix(col1, col2) if col1 == col2 => s"very very $col1"
      case Mix(Blue, col @ (Red | Green))  => s"little bit sad ${col.name}"
      case Mix(first, second)              => s"mix of ${first.name} and ${second.name}"
    }
  }

  case object White                           extends GenColor
  case object Black                           extends GenColor
  case class Base(color: Color)               extends GenColor
  case class Mix(first: Color, second: Color) extends GenColor

//  case class Lol(override val color: Color) extends Base(color)

  println(White.show)
  println(Base(Green).show)
  println(Mix(Red, Blue).show)
  println(Mix(Red, Red).show)
  println(Mix(Blue, Red).show)
  println(Mix(Green, Green).show)

  println(null == Red)
  println(Red == null)

  println(100000 == (100000: Integer))
  println(100000 eq (100000: Integer))
  println(127 eq (127: Integer))
  
}
