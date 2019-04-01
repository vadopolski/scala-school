package java2scala.homeworks.funcs

/** simple type with three elements */
sealed trait Three

object Three{
  case object First extends Three
  case object Second extends Three
  case object Third extends Three
}
