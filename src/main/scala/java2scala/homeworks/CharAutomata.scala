package java2scala.homeworks

trait CharAutomata[+A] {
  def consume(char: Char): CharAutomata[A]

  def error: Option[String]

  def result: Option[A]

  def apply(source: String): Either[String, A] = ???
}

object CharAutomata {
  def find(substring: String): CharAutomata[Int] = new Find(substring)

  class Error(string: String) extends CharAutomata[Nothing] {
    def consume(char: Char): CharAutomata[Nothing] = ???
    def error: Option[String]                      = ???
    def result: Option[Nothing]                    = ???
  }

  class Find private[CharAutomata] (substring: String) extends CharAutomata[Int] {
    def consume(char: Char): CharAutomata[Int] = ???
    def error: Option[String]                  = ???
    def result: Option[Int]                    = ???
  }

  class ParenBalance private[CharAutomata] extends CharAutomata[Unit] {
    def consume(char: Char): CharAutomata[Unit] = ???
    def error: Option[String]                   = ???
    def result: Option[Unit]                    = ???
  }

  class ParseInteger private[CharAutomata] extends CharAutomata[Int] {
    def consume(char: Char): CharAutomata[Int] = ???
    def error: Option[String]                  = ???
    def result: Option[Int]                    = ???
  }

  class And[A, B] private[CharAutomata] (autoA: CharAutomata[A], autoB: CharAutomata[B]) extends CharAutomata[(A, B)] {
    def consume(char: Char): CharAutomata[(A, B)] = ???
    def error: Option[String]                     = ???
    def result: Option[(A, B)]                    = ???
  }

  class Or[A, B] private[CharAutomata] (autoA: CharAutomata[A], autoB: CharAutomata[B]) extends CharAutomata[Either[A, B]] {
    def consume(char: Char): CharAutomata[Either[A, B]] = ???
    def error: Option[String]                           = ???
    def result: Option[Either[A, B]]                    = ???
  }

  class Repeated[A] private[CharAutomata] (auto: CharAutomata[A]) extends CharAutomata[List[A]] {
    def consume(char: Char): CharAutomata[List[A]] = ???
    def error: Option[String]                      = ???
    def result: Option[List[A]]                    = ???
  }

}
