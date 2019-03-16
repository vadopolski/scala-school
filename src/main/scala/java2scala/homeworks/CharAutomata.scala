package java2scala.homeworks

import java2scala.homeworks.CharAutomata.{And, Or}

trait CharAutomata[+A] {

  /** потребить один символ и перейти в следующее состояние */
  def consume(char: Char): CharAutomata[A]

  /** получить текущий результат, если это конец строки */
  def result: Either[String, A]

  /** потребить строку символ за символом */
  def apply(source: String): Either[String, A] = {
    source.size > 0 match {
      case true => consume(source.charAt(0)).apply(source.substring(1))
      case false => result
    }
  }

  /** создать автомат, который запустит оба автомата
    * и если первый вернёт ошибку, вернёт результат второго
    */
  def or[B](auto: CharAutomata[B]): CharAutomata[Either[A, B]] = new Or[A, B](this, auto)

  /** создать автомат, который запустит оба автомата
    * вернёт результаты обоих, если оба вернут успешный результат
    * и вернёт ошибку, если вернёт ошибку хотя бы один
    */
  def and[B](auto: CharAutomata[B]): CharAutomata[(A, B)] = new And[A, B](this, auto)
}

object CharAutomata {

  /** создаёт автомат, всегда результирующий с ошибкой
    * с заданным текстом message
    */
  def error(message: String): CharAutomata[Nothing] = new Error(message)

  /** создаёт автомат, всегда успешно результирующий
    * с заданным значением `value`
    */
  def const[A](value: A): CharAutomata[A] = new Const[A](value)

  /** создаёт автомат, возвращающий
    * первое вхождение строчки `substring`
    * или ошибкой
    */
  def find(substring: String): CharAutomata[Int] = new Find(substring)

  /** создаёт автомат, определяющий является ли строчка,
    * если исключить из неё все символы, кроме `'('` и `')'`
    * корректной скобочной последовательностью */
  def balance: CharAutomata[Unit] = new ParenBalance()

  /** создаёт автомат, ищущий первое число, из цифр подряд
    * и возвращающий результат в качестве BigInt либо 0
    */
  def parseInt: CharAutomata[BigInt] = new ParseInteger

  /** класс для реализации метода `error` */
  class Error(string: String) extends CharAutomata[Nothing] {
    def consume(char: Char): CharAutomata[Nothing] = this

    def result: Either[String, Nothing] = Left(string)
  }

  /** класс для реализации метода `const` */
  class Const[A] private[CharAutomata](value: A) extends CharAutomata[A] {
    def consume(char: Char): CharAutomata[A] = this

    def result: Either[String, A] = Right(value)
  }

  /** класс для реализации метода `find` */
  class Find private[CharAutomata](substring: String) extends CharAutomata[Int] {
    var buffer: String = ""

    def consume(char: Char): CharAutomata[Int] = {
      buffer += char
      this
    }

    def result: Either[String, Int] = {
      val index = buffer.indexOf(substring)
      index >= 0 match {
        case true => Right(index)
        case false => Left("substring not found")
      }
    }
  }

  /** класс для реализации метода `balance` */
  class ParenBalance private[CharAutomata] extends CharAutomata[Unit] {
    var parenthesisCount: Int = 0

    def consume(char: Char): CharAutomata[Unit] = {
      char match {
        case '(' => parenthesisCount += 1
        case ')' => parenthesisCount -= 1
        case _ =>
      }
      this
    }

    def result: Either[String, Unit] = {
      parenthesisCount match {
        case 0 => Right()
        case _ => Left("parenthesis are not balanced")
      }
    }
  }

  /** класс для реализации метода `parseInt` */
  class ParseInteger private[CharAutomata] extends CharAutomata[BigInt] {
    var bufferFull = false
    var intBuffer: String = ""

    def consume(char: Char): CharAutomata[BigInt] = {
      char.isDigit match {
        case true => if (!bufferFull) intBuffer += char
        case false => {
          intBuffer = intBuffer.replaceAll("^0+", "")
          if (intBuffer.size > 0) bufferFull = true
        }
      }
      this
    }

    def result: Either[String, BigInt] = {
      intBuffer match {
        case "" => Right(BigInt(0))
        case value => Right(BigInt(value))
      }
    }
  }

  /** класс для реализации метода `and` */
  class And[A, B] private[CharAutomata](autoA: CharAutomata[A], autoB: CharAutomata[B]) extends CharAutomata[(A, B)] {
    def consume(char: Char): CharAutomata[(A, B)] = {
      autoA.consume(char)
      autoB.consume(char)
      this
    }

    def result: Either[String, (A, B)] = {
      autoA.result match {
        case Left(str) => Left(str)
        case Right(a) => {
          autoB.result match {
            case Left(str) => Left(str)
            case Right(b) => Right(a, b)
          }
        }
      }
    }
  }

  /** класс для реализации метода `or` */
  class Or[A, B] private[CharAutomata](autoA: CharAutomata[A], autoB: CharAutomata[B]) extends CharAutomata[Either[A, B]] {
    def consume(char: Char): CharAutomata[Either[A, B]] = {
      autoA.consume(char)
      autoB.consume(char)
      this
    }

    def result: Either[String, Either[A, B]] = {
      autoA.result match {
        case Right(a) => Right(Left(a))
        case Left(_) => {
          autoB.result match {
            case Left(str) => Left(str)
            case Right(b) => Right(Right(b))
          }
        }
      }
    }
  }
}