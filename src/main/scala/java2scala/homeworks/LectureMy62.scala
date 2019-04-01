package java2scala.homeworks

object Lecture6My extends App {

  val res0 = for(x <- List(1,2,3)) yield x + 1
  val res0Desugared = List(1,2,3).map(x => x +1)
  assert(res0 == res0Desugared)

  val res16 = for{
    x <- List(1,2,3)
    y <- List.range(0, x)
  } yield x + y
  res16.foreach(println)

  val res1Desugared = List(1,2,3).flatMap(x => List.range(0, x).map(y => x + y))

  val res2 = for{
    x <- List(1,2,3,4)
    y <- List.range(0, x)
    z <- List.range(0, y)
  } yield x + y + z
  res16.foreach(println)
  val res2Desugered = List(1, 2, 3, 4).flatMap { x => List.range(0, x).flatMap { y => List.range(0, y).map { z => x + y + z } } }

  val res3 = for{
    x <- List(1,2,3,4)
    y <- List(1,2,3,4)
    z <- List(1,2,3,4)
  } yield x + y + z
  res16.foreach(println)

  val res3Desugered = List(1, 2, 3, 4).flatMap { x =>
                        List(1,2,3,4).flatMap { y =>
                          List.range(0, y).map { z =>
                            x + y + z
                          }
                        }
  }

  val res4Desugered = List(1, 2, 3, 4).flatMap { x =>
                        List.range(0, x).flatMap { y =>
                          List.range(0, y).map { z =>
                            x + y + z
                          }
                        }
  }



}
