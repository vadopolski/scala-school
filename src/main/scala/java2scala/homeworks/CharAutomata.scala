package java2scala.homeworks

trait CharAutomata[+A] {

  /** потребить один символ и перейти в следующее состояние */
  def consume(char: Char): CharAutomata[A]

  /** получить текущий результат, если это конец строки */
  def result: Either[String, A]

  /** потребить строку символ за символом */
  def apply(source: String): Either[String, A] = ???

  /** создать автомат, который запустит оба автомата
    * и если первый вернёт ошибку, вернёт результат второго
    */
  def or[B](auto: CharAutomata[B]): CharAutomata[Either[A, B]] = ???

  /** создать автомат, который запустит оба автомата
    * вернёт результаты обоих, если оба вернут успешный результат
    * и вернёт ошибку, если вернёт ошибку хотя бы один
    */
  def and[B](auto: CharAutomata[B]): CharAutomata[(A, B)] = ???
}

object CharAutomata {

  /** создаёт автомат, всегда результирующий с ошибкой
    * с заданным текстом message
    */
  def error(message: String): CharAutomata[Nothing] = ???

  /** создаёт автомат, всегда успешно результирующий
    * с заданным значением `value`
    */
  def const[A](value: A): CharAutomata[A] = ???

  /** создаёт автомат, возвращающий
    * первое вхождение строчки `substring`
    * или ошибкой
    */
  def find(substring: String): CharAutomata[Int] = ???

  /** создаёт автомат, определяющий является ли строчка,
    * если исключить из неё все символы, кроме `'('` и `')'`
    * корректной скобочной последовательностью */
  def balance: CharAutomata[Unit] = ???

  /** создаёт автомат, ищущий первое число, из цифр подряд
    * и возвращающий результат в качестве BigInt либо 0
    */
  def parseInt: CharAutomata[BigInt] = ???

  /** класс для реализации метода `error` */
  class Error(string: String) extends CharAutomata[Nothing] {
    def consume(char: Char): CharAutomata[Nothing] = ???

    def result: Either[String, Nothing] = ???
  }

  /** класс для реализации метода `const` */
  class Const[A] private[CharAutomata] (value: A) extends CharAutomata[A] {
    def consume(char: Char): CharAutomata[A] = ???

    def result: Either[String, A] = ???
  }

  /** класс для реализации метода `find` */
  class Find private[CharAutomata] (substring: String) extends CharAutomata[Int] {
    def consume(char: Char): CharAutomata[Int] = ???

    def result: Either[String, Int] = ???
  }

  /** класс для реализации метода `balance` */
  class ParenBalance private[CharAutomata] extends CharAutomata[Unit] {
    def consume(char: Char): CharAutomata[Unit] = ???

    def result: Either[String, Unit] = ???
  }

  /** класс для реализации метода `parseInt` */
  class ParseInteger private[CharAutomata] extends CharAutomata[BigInt] {
    def consume(char: Char): CharAutomata[BigInt] = ???

    def result: Either[String, BigInt] = ???
  }

  /** класс для реализации метода `and` */
  class And[A, B] private[CharAutomata] (autoA: CharAutomata[A], autoB: CharAutomata[B]) extends CharAutomata[(A, B)] {
    def consume(char: Char): CharAutomata[(A, B)] = ???

    def result: Either[String, (A, B)] = ???
  }

  /** класс для реализации метода `or` */
  class Or[A, B] private[CharAutomata] (autoA: CharAutomata[A], autoB: CharAutomata[B]) extends CharAutomata[Either[A, B]] {
    def consume(char: Char): CharAutomata[Either[A, B]] = ???

    def result: Either[String, Either[A, B]] = ???
  }
}
