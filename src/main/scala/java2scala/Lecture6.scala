package java2scala
import scala.collection.immutable.{Seq, IndexedSeq}

import scala.reflect.runtime.universe._
//
object Lecture6 extends App {
//  println(List("foo", "bar", "baz").zipWithIndex)
  val res0          = for (x <- List(1, 2, 3)) yield x + 1
  val res0Desugared = List(1, 2, 3).map(x => x + 1)
  assert(res0 == res0Desugared)

  val res1 = for {
    x <- List(1, 2, 3)
    y <- List.range(0, x)
  } yield x + y
  val res1Desugared = List(1, 2, 3).flatMap(x => List.range(0, x).map(y => x + y))

  val res2 = for {
    x <- List(1, 2, 3, 4)
    y <- List.range(0, x)
    z <- List.range(0, y)
  } yield x + y + z

  val res2Desugared =
    List(1, 2, 3, 4).flatMap { x =>
      List.range(0, x).flatMap { y =>
        List.range(0, y).map { z =>
          x + y + z
        }
      }
    }

  val res3 = for {
    x <- List(1, 2, 3, 4)
    y <- List(1, 2, 3, 4)
    z <- List(1, 2, 3, 4)
  } yield 1

  val res3Desugared1 =
    List(1, 2, 3, 4).flatMap { x =>
      List(1, 2, 3, 4).flatMap { y =>
        List(1, 2, 3, 4).map { z =>
          1
        }
      }
    }

  val res3Desugared2 =
    List(1, 2, 3, 4).flatMap { x =>
      List(1, 2, 3, 4)
    }.flatMap { y =>
      List(1, 2, 3, 4)
    }.map { z =>
      1
    }

  val res4 = for {
    x <- List(1, 2, 3, 4)
    y <- List.range(0, x) if x + y < 5
    z <- List.range(0, y)
  } yield x + y + z

  val res4Desugared =
    List(1, 2, 3, 4).flatMap { x =>
      List.range(0, x).withFilter(y => x + y < 5).flatMap { y =>
        List.range(0, y).map { z =>
          x + y + z
        }
      }
    }

  val res5 = for {
    x      <- List(1, 2, 3, 4)
    y      <- List.range(0, x)
    (z, i) <- List.range(0, y).zipWithIndex
  } yield x + y + z + i

  val res5Desugared =
    List(1, 2, 3, 4).flatMap { x =>
      List.range(0, x).withFilter(y => x + y < 5).flatMap { y =>
        List.range(0, y).zipWithIndex.map { p =>
          val z = p._1
          val i = p._2
          x + y + z + i
        }
      }
    }

  def res6 =
    for {
      x      <- List(1, 2, 3, 4)
      y      <- List.range(0, x)
      (z, i) <- List.range(0, y).zipWithIndex
    } println(x + y + z + i)

  def res6Desugared =
    List(1, 2, 3, 4).foreach { x =>
      List.range(0, x).foreach { y =>
        if (x + y < 5)
          List.range(0, y).zipWithIndex.foreach { p =>
            val z = p._1
            val i = p._2
            x + y + z + i
          }
      }
    }

  List(1, 2, 3).flatMap(x => Some(x).filter(_ > 1))
//  val res = for {
//    x      <- Vector(1, 2, 3)
//    list   = List.range(x, 0, -1)
//    (y, i) <- list.zipWithIndex
//  } yield s" {x = $x,  y = $y, i = $i}"
//
//  res.foreach(println)
//  println(res)

  final class Fruit(val name: String, val color: Long)

  object Fruit {
    def apply(name: String, color: Long): Fruit   = new Fruit(name, color)
    def unapply(x: Fruit): Option[(String, Long)] = Some((x.name, x.color)).filter(_._2 != 2)
  }

  object pear {
    def unapply(x: Fruit): Boolean = x.name == "pear"
  }

  object fruirs {
    def unapplySeq(x: Fruit): Option[List[Int]] = Some(List.range(1, x.color.toInt))
  }

  val fruit = Fruit("pear", 2)

//  fruit match {
//    case fruirs(c)          => println(s"fruirs : $c")
//    case pear()             => println("a pear")
//    case Fruit(name, color) => println((name, color))
//    case _                  => println("something else")
//  }

  val xs = List(1, 2, 3, 4, 5, 6, 7)

  xs match {
    case Seq()                   => "empty"
    case Seq(a, 3, c, rest @ _*) => println(a, c, rest)
    case _                       => "???"
  }

  object Lol {
    def apply(xs: Int*) = xs ++ xs
  }

//  println(Lol(List.range(1, 10): _*))

  val digits = ".*?(\\d+).*?(\\d+).*".r

//  println(digits.findFirstMatchIn("asdfsd123123fd232sf").map(_.group(1)))

  "asdfsd123123fd232sf" match {
    case digits(x, y) => println(x, y)
  }

}
