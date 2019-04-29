package java2scala

import cats.{Contravariant, Functor}
import cats.effect.{ExitCode, IO, IOApp, Timer}

import scala.annotation.unchecked.uncheckedVariance
import scala.collection.mutable
import cats.implicits._

import scala.concurrent.duration._

object Lecture11 extends IOApp {

  trait X1 {
    def thisAndThat(y: Y): List[X1] = List(this, y)
  }

  abstract class X extends X1 {
    def x: String

    def singleList: List[X] = List(this)
  }

  abstract class Y extends X {
    def y: Int

    override def singleList: List[Y] = List(this)
  }

  abstract class Z extends X {
    def z: Double
  }

  trait Y1 extends X1

  def foo(x: X): String = x.x

  def bar(y: Y): String = foo(y)

  def fooL(xs: List[X]): String = xs.map(_.x).mkString(",")

  def barL(ys: List[Y]): String = fooL(ys)

  case class Pair[+A](first: A, second: A) {
    def _1: A = first

    def toList: List[A] = List(first, second)

    def withFirst[B >: A](newFirst: B): Pair[B] = Pair(newFirst, second)
  }

  object Pair {
    implicit val functor: Functor[Pair] = new Functor[Pair] {
      def map[A, B](fa: Pair[A])(f: A => B): Pair[B] = Pair(f(fa.first), f(fa.second))
    }
  }

  val y1                = new Y { def x = "a"; def y = 1 }
  val y2                = new Y { def x = "b"; def y = 2 }
  val pairOfYs: Pair[Y] = Pair(y1, y2)

  y1.thisAndThat(y2)

  trait Consumer[-A] {
    def consume(a: A): IO[Unit]

    def consumeAll(a: List[A]): IO[Unit] = a.traverse_(consume)
  }

  object Consumer {
    implicit val contravariant: Contravariant[Consumer] = new Contravariant[Consumer] {
      def contramap[A, B](fa: Consumer[A])(f: B => A): Consumer[B] = b => fa.consume(f(b))
    }
  }

  trait Producer[+A] {
    def produce(x: Consumer[A]): IO[Unit]
  }

  object Producer {
    def of[A](xs: A*): Producer[A] =
      consumer => consumer.consumeAll(xs.toList)

    def tick[A](duration: FiniteDuration)(init: A)(f: A => A): Producer[A] =
      consumer => {
        def go(x: A): IO[Unit] =
          for {
            _   <- consumer.consume(x)
            _   <- Timer[IO].sleep(duration)
            res <- go(f(x))
          } yield res
        go(init)
      }

    implicit val functor: Functor[Producer] = ???
  }

  val printer: Consumer[String] = s => IO(println(s))

  val xPrinter: Consumer[X] = x => IO(println(s"x: ${x.x}"))

  def consumerManyYs(consumer: Consumer[Y]): IO[Unit] =
    consumer.consume(new Y   { def x = "a"; def y = 1 }) *>
      consumer.consume(new Y { def x = "b"; def y = 2 })

  consumerManyYs(xPrinter.contramap(y => y))
  consumerManyYs(xPrinter)

//  same as
//  def consumerManyYs[T >: Y](consumer: Consumer[T]): IO[Unit] =
//    consumer.consume(new Y   { def x = "a"; def y = 1 }) *>
//      consumer.consume(new Y { def x = "b"; def y = 2 })

  val ys: Producer[Y] = Producer.of(y1, y2)

  val zs = Producer.tick[Z](1 second)(new Z { def x = "start"; def z = 0 })(
    prev => new Z { def x = s"next ${prev.z}"; def z = prev.z + 1 }
  )

  def printAll(producer: Producer[X]): IO[Unit] =
    producer.produce(xPrinter)

  def run(args: List[String]): IO[ExitCode] =
    printAll(ys) *> printAll(zs) as ExitCode.Success
}
