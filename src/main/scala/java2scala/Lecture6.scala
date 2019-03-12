package java2scala
import scala.collection.immutable.{Seq, IndexedSeq}
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

//  val res = for {
//    x      <- Vector(1, 2, 3)
//    list   = List.range(x, 0, -1)
//    (y, i) <- list.zipWithIndex
//  } yield s" {x = $x,  y = $y, i = $i}"
//
//  res.foreach(println)
//  println(res)
}
