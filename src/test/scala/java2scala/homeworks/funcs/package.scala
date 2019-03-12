package java2scala.homeworks
import java2scala.homeworks.funcs.Three.{First, Second, Third}
import org.scalacheck.{Arbitrary, Cogen, Gen}
import org.scalactic.Equality

package object funcs {
  private val threeCogenF: Three => Long = {
    case First  => 1L
    case Second => 2L
    case Third  => 3L
  }

  implicit val arbitraryThree: Arbitrary[Three] = Arbitrary(Gen.oneOf(First, Second, Third))
  implicit val cogenThree: Cogen[Three]         = Cogen(threeCogenF)

  implicit def arbitraryChurchList[A: Arbitrary]: Arbitrary[ChurchList[A]] =
    Arbitrary(Arbitrary.arbitrary[List[A]].map(ChurchList(_: _*)))

  implicit def equalityChurchList[A]: Equality[ChurchList[A]] = {
    case (a, b: ChurchList[A @unchecked]) => a.toList == b.toList
    case _                                => false
  }
}
