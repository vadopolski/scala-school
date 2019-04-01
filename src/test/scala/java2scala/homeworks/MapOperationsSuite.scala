package java2scala.homeworks

import org.scalatest.prop.PropertyChecks
import org.scalatest.{FlatSpec, Matchers}

import MapOperations._

class MapOperationsSuite extends FlatSpec with Matchers with PropertyChecks {
  "map union" should "produce map containing all the keys" in
    forAll { (m1: Map[Int, Int], m2: Map[Int, String]) =>
      (m1 union m2).keySet shouldBe (m1.keySet | m2.keySet)
    }

  it should "have elements of left parameter as left" in
    forAll { (m1: Map[Int, Int], m2: Map[Int, String]) =>
      (m1 union m2).flatMap { case (k, v) => v.left.map(k -> _) } shouldBe m1
    }

  it should "have elements of right parameter as right" in
    forAll { (m1: Map[Int, Int], m2: Map[Int, String]) =>
      (m1 union m2).flatMap { case (k, v) => v.right.map(k -> _) } shouldBe m2
    }

  "map intersect" should "produce map containing common keys" in
    forAll { (m1: Map[Int, Int], m2: Map[Int, String]) =>
      (m1 intersect m2).keySet shouldBe (m1.keySet & m2.keySet)
    }

  it should "elements of first parameters as first" in
    forAll { (m1: Map[Int, Int], m2: Map[Int, String]) =>
      (m1 intersect m2).mapValues(_._1) shouldBe m1.filterKeys(m2.contains)
    }

  it should "elements of second parameters as second" in
    forAll { (m1: Map[Int, Int], m2: Map[Int, String]) =>
      (m1 intersect m2).mapValues(_._2) shouldBe m2.filterKeys(m1.contains)
    }

  "map difference" should "produce map containing exclusive keys" in
    forAll { (m1: Map[Int, Int], m2: Map[Int, String]) =>
      (m1 difference m2).keySet shouldBe ((m1.keySet diff m2.keySet) ++ (m2.keySet diff m1.keySet))
    }

  it should "contain elements of left parameter as left" in
    forAll { (m1: Map[Int, Int], m2: Map[Int, String]) =>
      (m1 difference m2).flatMap { case (k, v) => v.left.toOption.map(k -> _) } shouldBe m1.filterKeys(!m2.contains(_))
    }

  it should "contain elements of right parameter as left" in
    forAll { (m1: Map[Int, Int], m2: Map[Int, String]) =>
      (m1 difference m2).flatMap { case (k, v) => v.right.toOption.map(k -> _) } shouldBe m2.filterKeys(!m1.contains(_))
    }
}
