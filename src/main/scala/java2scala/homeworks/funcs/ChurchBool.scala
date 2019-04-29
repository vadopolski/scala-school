package java2scala.homeworks.funcs

trait ChurchBool {
  def cif[A](ifTrue: => A)(ifFalse: => A): A

//  def toBool: Boolean = ???

  def unary_! : ChurchBool             = cif(ChurchBool.False)(ChurchBool.True)
  def &&(that: ChurchBool): ChurchBool = cif(that)(ChurchBool.False)
  def ||(that: ChurchBool): ChurchBool = cif(ChurchBool.True)(that)
}

object ChurchBool {
  def apply(x: Boolean): ChurchBool = new ChurchBool {
    def cif[A](ifTrue: => A)(ifFalse: => A): A = if (x) ifTrue else ifFalse
  }

  val True: ChurchBool  = ChurchBool(true)
  val False: ChurchBool = ChurchBool(false)
}
