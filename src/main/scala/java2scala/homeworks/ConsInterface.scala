package java2scala.homeworks

import scala.collection.mutable

sealed trait ConsInterface[+A] {
  def value: A
  def next: ConsInterface[A]
}

case class ConsI[+A](val value: A, val next: ConsInterface[A]) extends ConsInterface[A] {
  override def toString = s"head: $value, next: $next"
}

object NilCons extends ConsInterface[Nothing] {
  def value = throw new NoSuchElementException("head of empty list")
  def next = throw new UnsupportedOperationException("tail of empty list")
}


object ConsTutorialDriver extends App {
  val c1 = ConsI(1, NilCons)
  val c2 = ConsI(2, c1)
  val c3 = ConsI(3, c2)
  println(c1)
  println(c2)
  println(c3)


  val scores = new mutable.LinkedHashMap[String, Int]()
  scores("Bob") = 100
  scores.apply("Bob")

}