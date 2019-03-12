package java2scala

object Lecture6My extends App {
//  println(for{
//    x <- List(1,2,3)
//    y <- List(1,2)
//  } yield x)
//
//  println(for{
//    x <- List(1,2,3)
//    y <- List(1,2)
//  } yield s"{x=$x + y=$y")
//
//  println(for{
//    x <- List(1,2,3)
//    y <- List.fill(x)(x)
//  } yield s"{x=$x + y=$y")
//
//  println(for{
//    x <- List(1,2,3)
//    y <- List.fill(x)(x)
//    z <- List(x + y)
//  } yield s"{x=$x + y=$y + z=$z")
//
//  val  res = for{
//    x <- List(1,2,3)
//    y <- List.fill(x)(x)
//    z <- List(x + y)
//  } yield s"{x=$x + y=$y + z=$z"
//  res.foreach(println)
//
//  val  res1 = for{
//    x <- List(1,2,3)
//    y <- List.fill(x)(x)
//    z <- Nil
//  } yield s"{x=$x + y=$y + z=$z"
//  res1.foreach(println)
//
//  val  res2 = for{
//    x <- List(1,2,3)
//    y <- List.fill(x)(x)
//    z = x + y
//  } yield s"{x=$x + y=$y + z=$z"
//  res2.foreach(println)
//
//  val  res3 = for{
//    x <- List(1,2,3)
//    list = List.fill(x)(x)
//    y <-list
//    z = x + y
//  } yield s"{x=$x + y=$y + z=$z"
//  res3.foreach(println)
//
//  val  res4 = for{
//    x <- List(1,2,3)
//    list = List.range(0, x)
//    y <-list
//    z = x + y
//  } yield s"{x=$x + y=$y + z=$z"
//  res4.foreach(println)
//
//  val  res5 = for{
//    x <- List(1,2,3)
//    list = List.range(0, x)
//    y <-list if x + y <= 3
//    z = x + y
//  } yield s"{x=$x + y=$y + z=$z"
//  res5.foreach(println)
//
//  val  res6 = for{
//    x <- List(1,2,3)
//    list = List.range(0, x)
//    y <-list if x + y <= 3 && x - y > 1
//    z = x + y
//  } yield s"{x=$x + y=$y + z=$z"
//  res6.foreach(println)
//
//  val res7 = for{
//    x <- List(1,2,3, 4)
//    list = List.range(0, x)
//    y <-list if x + y <= 3 && x - y > 1
//    z <- List(y)
//  } yield s"{x=$x + y=$y + z=$z"
//  res7.foreach(println)
//
//  val res8 = for{
//    x <- List(1,2,3, 4)
//    list = List.range(0, x)
//    y <-list if x + y <= 3 && x - y > 1
//    0 <- List(y)
//  } yield s"{x=$x + y=$y"
//  res8.foreach(println)
//
//  val res9 = for{
//    x <- List(1,2,3)
//    list = List.range(0, x, -1)
//    y <-list
//  } yield s"{x=$x + y=$y"
//  res9.foreach(println)
//
//  println(List("foo", "bar", "bus").zipWithIndex)
//
//  val res10 = for{
//    x <- List(1,2,3)
//    list = List.range(0, x, -1)
//    y <-list.zipWithIndex
//  } yield s"{x=$x + y=$y"
//  res10.foreach(println)
//
//  val res11 = for{
//    x <- List(1,2,3)
//    list = List.range(0, x, -1)
//    (y, i) <-list.zipWithIndex
//  } yield s"{x=$x + y=$y"
//  res11.foreach(println)
//
//  val res12 = for{
//    x <- List(1,2,3)
//    list = List.range(0, x, -1)
//    (y, i) <-list.zipWithIndex
//  } yield s"{x=$x + y=$y + i = $i}"
//  res12.foreach(println)
//
//  val res13 = for{
//    x <- Vector(1,2,3)
//    list = List.range(0, x, -1)
//    (y, i) <-list.zipWithIndex
//    z <- Option(x + y)
//  } yield s"{x=$x + y=$y + i = $i}"
//  res13.foreach(println)
//
//
//  // Seq is not definde mutable or immutable
//  val res14 = for{
//    x <- Vector(1,2,3) : IndexedSeq[Int]
//    list = List.range(0, x, -1)
//    (y, i) <-list.zipWithIndex
//    z <- Option(x + y)
//  } yield s"{x=$x + y=$y + i = $i}"
//  res14.foreach(println)
//  // list of string res13
//
//
//  import scala.collection.immutable.{Seq, IndexedSeq}
//  // Seq is not definde mutable or immutable
//  val res15 = for{
//    x <- Vector(1,2,3) : Seq[Int]
//    list = List.range(0, x, -1)
//    (y, i) <-list.zipWithIndex
//    z <- Option(x + y)
//  } yield s"{x=$x + y=$y + i = $i}"
//  res15.foreach(println)

  val res16 = for{
    x <- Vector(1,2,3) : Seq[Int]
    list = List.range(0, x, -1)
    (y, i) <-list.zipWithIndex
    z <- Option(x + y)
  } yield s"{x=$x + y=$y + i = $i}"
  res16.foreach(println)



}
