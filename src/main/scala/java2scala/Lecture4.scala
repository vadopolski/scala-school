package java2scala

import java2scala.Lecture3.{Base, Color, GenColor}
import java2scala.Lecture3.Color.{Blue, Green, Red}

import scala.Function.const

trait ShowInt {
  def name(n: Int): String

  def name2(n: Int, m: Int): String = s"${name(n)} :: ${name(m)}"
}

case class Lol() {
  def sdfsdf_+++++(lol: Lol): String = "lol"
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

  val plus3: Int => Int  = x => x + 3
  val plus4: Int => Int  = _ + 4
  val plus5: Int => Int  = 5 +
  val plus5a: Int => Int = x => 5.+(x)
  val plus5b: Int => Int = 5.+

  val xx  = 5.+(1)
  val xx2 = 5 + 1

  val u = (i: Long) => 6 + i

  def foo(int: Int, long: Long, str: String, double: Double): Boolean = int == long

  val y  = namer.name2 _
  val y1 = namer.name2(3, _)

//  println(plus3(2))
//  println(plus4(2))
//  println(plus5(2))

  def foo1 = foo _
  def foo2 = foo(1, _, "sss", _)

  val namer: ShowInt = x => s"the int $x"

  def name3(x: Int, y: Int, namer: ShowInt) = println(namer.name2(x, y))

  name3(-1, 4, x => s"the int $x")

  val colorToBool: Color => Boolean = {
    case Red          => true
    case Blue | Green => false
  }

  val genColorToString: GenColor => String = {
    case Base(color) => s"base $color"
    case _           => "not base"
  }

  val plus: Int => Int => Int  = x => y => x + y
  val plus2: (Int, Int) => Int = (x, y) => x + y
  def plus2a                   = plus2.curried

  def plusM(x: Int)(y: Int) = x + y
  def plusS(x: Int, y: Int) = x + y

  def transformInt(x: Int, f: Int => Int) = f(x)
  println(transformInt(6, plusS(4, _)))
  println(transformInt(6, plusM(4)))

    


//  println(plus(2)(3))
//  println(plus2a(2)(3))

//  println(namer.name2(4, 5))
}
