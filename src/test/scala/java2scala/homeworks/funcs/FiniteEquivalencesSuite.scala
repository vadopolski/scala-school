package java2scala.homeworks.funcs

import org.scalacheck.Arbitrary
import org.scalactic.Equality
import org.scalatest.prop.PropertyChecks
import org.scalatest.{Assertion, FlatSpec, Matchers, WordSpec}
import FiniteEquivalences._

class FiniteEquivalencesSuite extends WordSpec with Matchers with PropertyChecks {

  def checkEquivalence[A: Arbitrary: Equality, B: Arbitrary: Equality](eq: Equivalent[A, B]): Assertion = {
    forAll { a: A =>
      assert(eq.from(eq.to(a)) === a)
    }

    forAll { b: B =>
      eq.to(eq.from(b)) shouldBe b
    }
  }

  "Finite Equivalences" when {
    "checking boolToThree" should {
      "be a section" in forAll { (f: Boolean => Three, b: Boolean) =>
        boolToThree.from(boolToThree.to(f))(b) shouldEqual f(b)
      }
      "be a retraction" in forAll { p: (Three, Three) =>
        boolToThree.to(boolToThree.from(p)) shouldEqual p
      }
    }

    "checking threeToBool" should {
      "be a section" in forAll { (f: Three => Boolean, t: Three) =>
        threeToBool.from(threeToBool.to(f))(t) shouldEqual f(t)
      }
      "be a retraction" in forAll { t: (Boolean, Boolean, Boolean) =>
        threeToBool.to(threeToBool.from(t)) shouldEqual t
      }
    }

    "checking boolToBoolToBool" should {
      "be a section" in forAll { (f: Boolean => Boolean => Boolean, x: Boolean, y: Boolean) =>
        boolToBoolToBool.from(boolToBoolToBool.to(f))(x)(y) shouldEqual f(x)(y)
      }
      "be a retraction" in forAll { (f: (Boolean => Boolean) => Boolean, g: Boolean => Boolean) =>
        boolToBoolToBool.to(boolToBoolToBool.from(f))(g) shouldEqual f(g)
      }
    }

    "checking threeToUnit" should {
      "be a section" in forAll { (f: Three => Unit, t: Three) =>
        threeToUnit.from(threeToUnit.to(f))(t) shouldEqual f(t)
      }
      "be a retraction" in forAll { u: Unit =>
        threeToUnit.to(threeToUnit.from(u)) shouldEqual u
      }
    }

    "checking unitToThree" should {
      "be a section" in forAll { (f: Unit => Three, u: Unit) =>
        unitToThree.from(unitToThree.to(f))(u) shouldEqual f(u)
      }
      "be a retraction" in forAll { t: Three =>
        unitToThree.to(unitToThree.from(t)) shouldEqual t
      }
    }
  }

}
