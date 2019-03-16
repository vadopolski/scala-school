//for {n <- 1 to 10} println(n)
//for (i <- 1 to 3; j <- 1 to 3 if i == j) print(s"($i;$j) ")
//for (i <- 1 to 3; j <- 1 to 3) print(f"${10 * i + j}%3d")
//for (i <- 1 to 3; from = i - 1; j <- from to 3) print(s"($i $j)")
//for (i <- 1 to 10) yield i + i
//for(n <- 1 to 10) yield n%3
//for(c <- "Hello"; i <- 1 to 2) yield (c+i).toChar
//for(i <- 0 to 1; c <- "Hello") yield (c+i).toChar
//
//for { i <- 1 to 3
//      from = 4 - i
//      j <- from to 3 } yield (i,j)

//for {x <- List(1, 2, 3)
//     y <- List(1, 2)} yield x
//
//for {x <- List(1, 2, 3)
//     y <- List.fill(x)(x)} yield s"($x,$y)"
//
//for {x <- List(1, 2, 3)
//     y <- List.fill(x)(x)
//     z <- List(x + y)} yield s"($x, $y, $z)"

for {x <- List(1, 2, 3)
     y <- List.fill(x)(x)
     z = x + y} yield s"($x, $y, $z)"