for{
  x <- List(1,2,3)
  y <- List(1,2)
} yield x
// List(1, 1, 2, 2, 3, 3)

for{
  x <- List(1,2,3)
  y <- List(1,2)
} yield s"{$x,$y"
//res1: List[String] = List({1,1, {1,2, {2,1, {2,2, {3,1, {3,2)

for{
  x <- List(1,2,3)
  y <- List.fill(x)(x)
} yield s"{$x, $y)"
//res2: List[String] = List({1, 1), {2, 2), {2, 2), {3, 3), {3, 3), {3, 3))


for{
  x <- List(1,2,3)
  y <- List.fill(x)(x)
  z <- List(x + y)
} yield s"($x, $y, $z)"
//res3: List[String] = List((1, 1, 2), (2, 2, 4), (2, 2, 4), (3, 3, 6), (3, 3, 6), (3, 3, 6))

for{
  x <- List(1,2,3)
  y <- List.fill(x)(x)
  z <- List(x + y)
} yield s"{$x + y=$y + z=$z}"
//res4: List[String] = List({1 + y=1 + z=2}, {2 + y=2 + z=4}, {2 + y=2 + z=4}, {3 + y=3 + z=6}, {3 + y=3 + z=6}, {3 + y=3 + z=6})


for{
  x <- List(1,2,3)
  y <- List.fill(x)(x)
  z <- Nil
} yield s"{$x, $y, $z)"
//res5: List[String] = List()

for{
  x <- List(1,2,3)
  y <- List.fill(x)(x)
  z = x + y
} yield s"{$x, $y, $z}"
//res6: List[String] = List({1, 1, 2}, {2, 2, 4}, {2, 2, 4}, {3, 3, 6}, {3, 3, 6}, {3, 3, 6})


val  res3 = for{
  x <- List(1,2,3)
  list = List.fill(x)(x)
  y <-list
  z = x + y
} yield s"{$x, $y, $z}"
// res3: List[String] = List({1, 1, 2}, {2, 2, 4}, {2, 2, 4}, {3, 3, 6}, {3, 3, 6}, {3, 3, 6})

val  res4 = for{
  x <- List(1,2,3)
  list = List.range(0, x)
  y <-list
  z = x + y
} yield s"{$x, $y, $z}"
//res4: List[String] = List({1, 0, 1}, {2, 0, 2}, {2, 1, 3}, {3, 0, 3}, {3, 1, 4}, {3, 2, 5})

val  res5 = for{
  x <- List(1,2,3)
  list = List.range(0, x)
  y <-list if x + y <= 3
  z = x + y
} yield s"{$x, $y, $z}"
//res5: List[String] = List({1, 0, 1}, {2, 0, 2}, {2, 1, 3}, {3, 0, 3})


val  res6 = for{
  x <- List(1,2,3)
  list = List.range(0, x)
  y <-list if x + y <= 3 && x - y > 1
  z = x + y
} yield s"{$x, $y, $z}"
//res6: List[String] = List({2, 0, 2}, {3, 0, 3})


val res7 = for{
  x <- List(1,2,3, 4)
  list = List.range(0, x)
  y <-list if x + y <= 3 && x - y > 1
  z <- List(y)
} yield s"{$x, $y, $z}"
//res7: List[String] = List({2, 0, 0}, {3, 0, 0})


val res8 = for{
  x <- List(1,2,3, 4)
  list = List.range(0, x)
  y <-list if x + y <= 3 && x - y > 1
  0 <- List(y)
} yield s"{$x, $y}"
//res8: List[String] = List({2, 0}, {3, 0})


val res9 = for{
  x <- List(1,2,3)
  list = List.range(0, x, -1)
  y <-list
} yield s"{$x, $y}"
//res9: List[String] = List()

val res10 = for{
  x <- List(1,2,3)
  list = List.range(0, x, -1)
  y <-list.zipWithIndex
} yield s"{$x, $y}"
//res10: List[String] = List()

val res11 = for{
  x <- List(1,2,3)
  list = List.range(0, x, -1)
  (y, i) <-list.zipWithIndex
} yield s"{$x, $y}"
//res11: List[String] = List()

val res12 = for{
  x <- List(1,2,3)
  list = List.range(0, x, -1)
  (y, i) <-list.zipWithIndex
} yield s"{$x, $y, $i}"
//res12: List[String] = List()

val res13 = for{
  x <- Vector(1,2,3)
  list = List.range(0, x, -1)
  (y, i) <-list.zipWithIndex
  z <- Option(x + y)
} yield s"{$x, $y, $i}"
//res13: scala.collection.immutable.Vector[String] = Vector()


//// Seq is not definde mutable or immutable
val res14 = for{
  x <- Vector(1,2,3) : IndexedSeq[Int]
  list = List.range(0, x, -1)
  (y, i) <-list.zipWithIndex
  z <- Option(x + y)
} yield s"{$x, $y, $i}"
//res14: IndexedSeq[String] = Vector()

import scala.collection.immutable.{Seq, IndexedSeq}
// Seq is not definde mutable or immutable
val res15 = for{
  x <- Vector(1,2,3) : Seq[Int]
  list = List.range(0, x, -1)
  (y, i) <-list.zipWithIndex
  z <- Option(x + y)
} yield s"{$x, $y, $i}"
//res15: scala.collection.immutable.Seq[String] = Vector()


val res16 = for{
  x <- Vector(1,2,3) : Seq[Int]
  list = List.range(0, x, -1)
  (y, i) <-list.zipWithIndex
  z <- Option(x + y)
} yield s"{$x, $y, $i}"
//res16: scala.collection.immutable.Seq[String] = Vector()
