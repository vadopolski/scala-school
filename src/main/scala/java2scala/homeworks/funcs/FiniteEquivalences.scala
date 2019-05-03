package java2scala.homeworks.funcs

import java2scala.homeworks.funcs.Three.{First, Second, Third}

object FiniteEquivalences {
  val boolToThree: Equivalent[Boolean => Three, (Three, Three)] =
    Equivalent { a: (Boolean => Three) => (a(false), a(true))}
               { b: (Three, Three) => x =>
                 { val (b1, b2) = b; if (!x) b1 else b2 }
               }

  val threeToBool: Equivalent[Three => Boolean, (Boolean, Boolean, Boolean)] =
    Equivalent { a: (Three => Boolean) => (a(First), a(Second), a(Third))}
               { b: (Boolean, Boolean, Boolean) => a =>
                 {
                   val (b1, b2, b3) = b
                   a match {
                     case First  => b1
                     case Second => b2
                     case Third  => b3
                   }
                 }
               }

  val boolToBoolToBool: Equivalent[Boolean => Boolean => Boolean, (Boolean => Boolean) => Boolean] =
    Equivalent { a: (Boolean => Boolean => Boolean) => ??? }
               { b: ((Boolean => Boolean) => Boolean) => ???}

  val threeToUnit: Equivalent[Three => Unit, Unit] =
    Equivalent { a: (Three => Unit) => () }
               { b: Unit => a => ()}

  val unitToThree: Equivalent[Unit => Three, Three] =
    Equivalent { a: (Unit => Three) => a(()) }
               { b: Three => a => b }
}
