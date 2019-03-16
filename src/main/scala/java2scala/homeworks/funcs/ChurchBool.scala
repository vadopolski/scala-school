package java2scala.homeworks.funcs

trait ChurchBool {
  def cif[A](ifTrue: => A)(ifFalse: => A): A

  def toBool: Boolean = ???

  def unary_! : ChurchBool             = ???
  def &&(that: ChurchBool): ChurchBool = ???
  def ||(that: ChurchBool): ChurchBool = ???
}

object ChurchBool {
  def apply(x: Boolean): ChurchBool = ???

  val True: ChurchBool  = ???
  val False: ChurchBool = ???
}
