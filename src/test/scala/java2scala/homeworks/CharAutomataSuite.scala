package java2scala.homeworks

import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.prop.TableDrivenPropertyChecks._
import CharAutomataSuite.tests

class CharAutomataSuite extends FlatSpec with Matchers {
  import CharAutomata.{balance, find, parseInt}
  "parenthesis balancer" should "identify correct sequences" in {
    forAll(tests) { (string, _, paren, _, _) =>
      balance(string).isRight shouldBe paren
    }
  }

  "substring finder" should "find index of the first string occurence" in {
    forAll(tests) { (string, sub, _, _, res) =>
      find(sub)(string).toOption shouldBe res
    }
  }

  "num parser" should "parse integers" in {
    forAll(tests) { (string, _, _, n, _) =>
      parseInt(string) shouldBe Right(n)
    }
  }

  "and combinator" should "merge results of `balance` and `find`" in {
    forAll(tests) { (string, sub, paren, _, found) =>
      (balance and find(sub))(string).toOption shouldBe
        found.filter(_ => paren).map(((), _))
    }
  }

  it should "merge results of `find` and `parse`" in {
    forAll(tests) { (string, sub, _, n, found) =>
      (find(sub) and CharAutomata.parseInt)(string).toOption shouldBe
        found.map((_, n))

    }
  }

  it should "merge results of `balance` , `find` and `parse`" in {
    forAll(tests) { (string, sub, paren, n, found) =>
      (parseInt and balance and find(sub))(string).toOption shouldBe
        found.filter(_ => paren).map(s => ((n, ()), s))
    }
  }

  "or combinator" should "choose between results of `balance` and `parseInt`" in {
    forAll(tests) { (string, _, paren, n, _) =>
      (balance or parseInt)(string) shouldBe Right(Right(n).filterOrElse(_ => !paren, ()))
    }
  }

  it should "choose between results `find`, `parseInt` and `balance`" in {
    forAll(tests) { (string, sub, paren, n, found) =>
      (find(sub) or parseInt or balance)(string) shouldBe
        Right(Left(found.map(i => Left(i)).getOrElse(Right(n))))
    }
  }

}

object CharAutomataSuite {
  final case class TestCase(string: String, sub: String, paren: Boolean, num: Int, found: Option[Int]) {
    def asTuple = (string, sub, paren, num, found)
  }

  val testCases = Vector(
    TestCase("(())", "", paren = true, num = 0, found = Some(0)),
    TestCase("()()(1)()", "()", paren = true, num = 1, found = Some(0)),
    TestCase("()((12)((24)))", ")(", paren = true, num = 12, found = Some(1)),
    TestCase("(())lol(()lol))", "lol", paren = false, num = 0, found = Some(4)),
    TestCase("(6(5(4)3)2)1)", "lol", paren = false, num = 6, found = None),
    TestCase("()((000)(())234)", "lol", paren = true, num = 234, found = None),
    TestCase("((ke))kek(())", "kek", paren = true, num = 0, found = Some(6))
  )

  val tests = Table(("string", "sub", "paren", "num", "found"), testCases.map(_.asTuple): _*)
}
