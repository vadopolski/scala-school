package java2scala.homeworks.funcs

object FiniteEquivalences {
  val boolToThree: Equivalent[Boolean => Three, (Three, Three)]              = ???
  val threeToBool: Equivalent[Three => Boolean, (Boolean, Boolean, Boolean)] = ???

  val boolToBoolToBool: Equivalent[Boolean => Boolean => Boolean, (Boolean => Boolean) => Boolean] = ???

  val threeToUnit: Equivalent[Three => Unit, Unit]  = ???
  val unitToThree: Equivalent[Unit => Three, Three] = ???
}
