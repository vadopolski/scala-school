package java2scala.homeworks.funiso

object FiniteEquivalences {
  // напишите правильный тип вместо FILL_ME, не содержащий функций внутри
  // правильный тип должен быть разновидностью кортежа (x, y, ...)
  // и реализуйте изоморфизмы
  type FILL_ME = Nothing

  val boolToThree: Equivalent[Boolean => Three, FILL_ME] = ???
  val threeToBool: Equivalent[Three => Boolean, FILL_ME] = ???

  val boolTo_BoolToBool: Equivalent[Boolean => Boolean => Boolean, FILL_ME]   = ???
  val boolToBool_ToBool: Equivalent[(Boolean => Boolean) => Boolean, FILL_ME] = ???

  val threeToUnit: Equivalent[Three => Unit, FILL_ME] = ???
  val unitToThree: Equivalent[Unit => Three, FILL_ME] = ???}
