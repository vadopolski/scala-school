package java2scala.homeworks

trait CharAutomata[+A] {
  def consume(char: Char): CharAutomata[A]

  def result: Either[String, A]

  def apply(source: String): Either[String, A] = ???

  def or[B](auto: CharAutomata[B]): CharAutomata[Either[A, B]] = ???

  def and[B](auto: CharAutomata[B]): CharAutomata[(A, B)] = ???
}

object CharAutomata {
  def error(message: String): CharAutomata[Nothing] = ???
  def const[A](value: A): CharAutomata[A]           = ???
  def find(substring: String): CharAutomata[Int]    = ???
  def balance: CharAutomata[Unit]                   = ???
  def parseInt: CharAutomata[BigInt]                = ???

  class Error(string: String) extends CharAutomata[Nothing] {
    def consume(char: Char): CharAutomata[Nothing] = ???

    def result: Either[String, Nothing] = ???
  }

  class Const[A] private[CharAutomata] (value: A) extends CharAutomata[A] {
    def consume(char: Char): CharAutomata[A] = ???

    def result: Either[String, A] = ???
  }

  class Find private[CharAutomata] (substring: String) extends CharAutomata[Int] {
    def consume(char: Char): CharAutomata[Int] = ???

    def result: Either[String, Int] = ???
  }

  class ParenBalance private[CharAutomata] extends CharAutomata[Unit] {
    def consume(char: Char): CharAutomata[Unit] = ???

    def result: Either[String, Unit] = ???
  }

  class ParseInteger private[CharAutomata] extends CharAutomata[BigInt] {
    def consume(char: Char): CharAutomata[BigInt] = ???

    def result: Either[String, BigInt] = ???
  }

  class And[A, B] private[CharAutomata] (autoA: CharAutomata[A], autoB: CharAutomata[B]) extends CharAutomata[(A, B)] {
    def consume(char: Char): CharAutomata[(A, B)] = ???

    def result: Either[String, (A, B)] = ???
  }

  class Or[A, B] private[CharAutomata] (autoA: CharAutomata[A], autoB: CharAutomata[B]) extends CharAutomata[Either[A, B]] {
    def consume(char: Char): CharAutomata[Either[A, B]] = ???

    def result: Either[String, Either[A, B]] = ???
  }

  class Repeated[A] private[CharAutomata] (auto: CharAutomata[A]) extends CharAutomata[List[A]] {
    def consume(char: Char): CharAutomata[List[A]] = ???

    def result: Either[String, List[A]] = ???
  }
}
