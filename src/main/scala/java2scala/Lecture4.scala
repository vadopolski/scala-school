package java2scala

import scala.Function.const

trait ShowInt {
  def name(n: Int): String

  def name2(n: Int, m: Int): String = s"${name(n)} :: ${name(m)}"
}

object Lecture4 extends App {
  type BoolToBool            = Boolean => Boolean
  type BoolToBool2           = Function1[Boolean, Boolean]
  type BoolAndStrinToDouble  = Function2[Boolean, String, Double]
  type BoolAndStrinToDouble2 = (Boolean, String) => Double
  type MakeString            = () => String
  type MakeString2           = Function0[String]

  type Tuple0 = Unit
  val x: Tuple0 = ()
  type IntToString = Int => String

  type BoolAndInt = (Boolean, Int)
  val boolAndInt = (true, 4)

  val (someBool, someInt) = boolAndInt

  final case class Person(infant: Boolean, name: String)

  val person = Person(false, "Eldar")

  val Person(infant, name) = person

  val bid: BoolToBool        = x => x
  val bnot: BoolToBool       = !_
  val constTrue: BoolToBool  = _ => true
  val constFalse: BoolToBool = const(false)

  val plus3: Int => Int = x => x + 3
  val plus4: Int => Int = _ + 4
  val plus5: Int => Int = 5 +

  println(plus3(2))
  println(plus4(2))
  println(plus5(2))

  val namer: ShowInt = x => s"the int $x"

  def name3(x: Int, y: Int, namer: ShowInt) = println(namer.name2(x, y))

  name3( -1, 4, x => s"the int $x")

  println(namer.name2(4, 5))
}
