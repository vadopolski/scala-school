package everydaytraining.day250219

object Solution2 extends App{
  println(for { i <- 1 to 3
                from = 4 - i
                j <- from to 3 } yield i)
}