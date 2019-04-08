import scala.annotation.tailrec
import scala.util.Random

def isEven(n: Long): Boolean = n % 2 == 0

val s = Stream.from(2).filter(isEven(_))

s.take(5).force

def genEvenStream(n: Long): Stream[Long] =
  if (isEven(n)) {
    n #:: genEvenStream(n + 1)
  }
  else {
    genEvenStream(n + 1)
  }

val primeStream = genEvenStream(2)

primeStream.take(5).force

def genStream(n: Long): Stream[Long] = n #:: genStream(n + 1)

val tenOrMore = genStream(10)
tenOrMore.tail.tail.tail

val tenOrMoreSquare = genStream(10).map(x => x * x)
tenOrMoreSquare.tail
tenOrMoreSquare.take(5).force

