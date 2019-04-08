package java2scala.homeworks

import scala.math.sqrt

object StreamThings extends App{

  /** простые числа - имеющие ровно два делителя, себя и 1. Числа 2,3,5,7,11...
    * необходимо реализовать с помощью `isPrime`*/
  val primes: Stream[Long] = 1 #:: genEvenStream(2).filter(isPrime)

  def genEvenStream(n: Long): Stream[Long] =
    if (isPrime(n)) n #:: genEvenStream(n + 1)
    else genEvenStream(n + 1)

  /** проверка на простоту числа - нужно сделать с помощью `primes` */
  def isPrime(n: Long): Boolean = n > 1 && (2L to sqrt(n).toLong).forall(x => n % x != 0)

  /** последовательность Коллатца для выбранного числа
    * C(0) = n,
    * C(i + 1) = С(i) / 2 если С(i_ - чётное
    * С(i + 1) = 3 * С(i) + 1 если С(i) - нечётное*/
  def collatz(n: Long): Stream[Long] = ???

  /** числа фибоначчи F(0) = F(1) = 1, F(n + 2) = F(n + 1) + F(n) */
  val fibonacci: Stream[Long] = 1L #:: 1L #:: (??? : Stream[Long])

  /** построить бесконечный цикл из конечного числа элементов, используя O(`as.length`) памяти */
  def cycle[A](as: A*): Stream[A] = as.toStream
}
