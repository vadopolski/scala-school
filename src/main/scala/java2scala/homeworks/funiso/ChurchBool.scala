package java2scala.homeworks.funiso

trait ChurchBool {
  def apply[A](ifTrue: => A)(ifFalse: => A): A

  def toBool: Boolean = ???

  def unary_! : ChurchBool             = ???
  def &&(that: ChurchBool): ChurchBool = ???
  def ||(that: ChurchBool): ChurchBool = ???
}
