package java2scala.homeworks

import org.scalatest.{Matchers, PropSpec}
import org.scalatest.prop._

class LinkedMapSpec extends PropSpec with Matchers with PropertyChecks {
  property("empty map contains nothing") {
    forAll { key: Int =>
      val empty = LinkedMap[Int, String]()
      empty.contains(key) shouldBe false
      empty(key) shouldBe None
    }
  }

  property("singleton map contains it's key") {
    forAll { (key1: Int, value: String, key2: Int) =>
      val singleton = LinkedMap[Int, String](key1 -> value)
      singleton.contains(key1) shouldBe true
      singleton(key1) shouldBe Some(value)
      singleton.contains(key2) shouldBe (key1 == key2)
    }
  }

  property("deleted data should not be found") {
    forAll { (elems: List[(String, Int)], key: String) =>
      val removed = LinkedMap(elems: _*).delete(key)
      removed.contains(key) shouldBe false
      removed(key) shouldBe None
    }
  }

  property("updated element should always be in map") {
    forAll { (elems: List[(Int, String)], key: Int, value: String) =>
      val updated = LinkedMap(elems: _*)(key) = value
      updated.contains(key) shouldBe true
      updated(key) shouldBe Some(value)
    }
  }

  property("maps are equivalent when non repeating lists are quivalent") {
    forAll { (m1: Map[String, String], m2: Map[String, String]) =>
      val l1 = m1.toList
      val l2 = m2.toList
      (LinkedMap(l1: _*) == LinkedMap(l2: _*)) shouldBe (l1 == l2)
    }
  }

  property("mapped map should be correct") {
    forAll { (l: List[(String, Int)], f: Int => String) =>
      LinkedMap(l: _*).mapValues(f) shouldBe LinkedMap(l.map { case (k, v) => k -> f(v) }: _*)
    }
  }

  property("key-value mapped map should be correct") {
    forAll { (l: List[(Int, String)], f: (Int, String) => Double) =>
      LinkedMap(l: _*).mapWithKey(f) shouldBe LinkedMap(l.map { case (k, v) => k -> f(k, v) }: _*)
    }
  }

}
