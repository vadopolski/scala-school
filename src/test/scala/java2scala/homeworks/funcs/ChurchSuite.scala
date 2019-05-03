package java2scala.homeworks.funcs

import org.scalactic.Equality
import org.scalatest.prop.PropertyChecks
import org.scalatest.{Matchers, PropSpec, WordSpec}

import scala.Function.uncurried

class ChurchSuite extends WordSpec with Matchers with PropertyChecks {
  "ChurchBool" when {
//    "converting to and from boolean" should {
//      "be isomorphic" in forAll { b: Boolean =>
//        ChurchBool(b).toBool shouldEqual b
//      }
//
//      "convert True to true" in {
//        ChurchBool.True.toBool shouldEqual true
//      }
//
//      "convert False to false" in {
//        ChurchBool.False.toBool shouldEqual false
//      }
//    }

    "checking conditions" should {
      "use first expr for true" in forAll { (x: Int, y: Int) =>
        ChurchBool.True.cif(x)(y) shouldEqual x
      }

      "use second expr for false" in forAll { (x: Int, y: Int) =>
        ChurchBool.False.cif(x)(y) shouldEqual y
      }
    }

//    "negating" should {
//      "negate converted to the negated" in forAll { b: Boolean =>
//        (!ChurchBool(b)).toBool shouldEqual !b
//      }
//
//      "double negate to the same" in forAll { b: Boolean =>
//        (!(!ChurchBool(b))).toBool shouldEqual b
//      }
//    }

//    "calc AND" should {
//      "keep and" in forAll { (b: Boolean, c: Boolean) =>
//        (ChurchBool(b) && ChurchBool(c)).toBool shouldEqual b && c
//      }
//
//      "true neutral" in forAll { b: Boolean =>
//        (ChurchBool(b) && ChurchBool.True).toBool shouldEqual b
//        (ChurchBool.True && ChurchBool(b)).toBool shouldEqual b
//      }
//
//      "false absorb" in forAll { b: Boolean =>
//        (ChurchBool(b) && ChurchBool.False).toBool shouldEqual false
//        (ChurchBool.False && ChurchBool(b)).toBool shouldEqual false
//      }
//    }

//    "calc OR" should {
//      "keep OR" in forAll { (b: Boolean, c: Boolean) =>
//        (ChurchBool(b) || ChurchBool(c)).toBool shouldEqual b || c
//      }
//
//      "true absorb" in forAll { b: Boolean =>
//        (ChurchBool(b) || ChurchBool.True).toBool shouldEqual true
//        (ChurchBool.True || ChurchBool(b)).toBool shouldEqual true
//      }
//
//      "false neutral" in forAll { b: Boolean =>
//        (ChurchBool(b) || ChurchBool.False).toBool shouldEqual b
//        (ChurchBool.False || ChurchBool(b)).toBool shouldEqual b
//      }
//    }
  }

}
