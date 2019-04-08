package java2scala

import java.time.Instant

import cats.Eval
import cats.effect.{ExitCode, IO, IOApp, Timer}
import cats.effect.concurrent.{Deferred, MVar}

import scala.concurrent.duration._
import cats.implicits._
import cats.effect.syntax.all._

trait ChurchList[A] { self =>
  def fold[R](z: Eval[R])(ag: A => Eval[R] => Eval[R]): Eval[R]

  def ::(x: A): ChurchList[A] = new ChurchList[A] {
    def fold[R](z: Eval[R])(ag: A => Eval[R] => Eval[R]): Eval[R] =
      Eval.defer(ag(x)(self.fold(z)(ag)))
  }

  def map[B](f: A => B): ChurchList[B] =
    new ChurchList[B] {
      def fold[R](z: Eval[R])(ag: B => Eval[R] => Eval[R]): Eval[R] =
        self.fold(z)(a => ag(f(a)))
    }

  def filter(p: A => Boolean): ChurchList[A] = new ChurchList[A] {
    def fold[R](z: Eval[R])(ag: A => Eval[R] => Eval[R]): Eval[R] =
      self.fold(z)(a => if (p(a)) ag(a) else identity)
  }

  def flatMap[B](f: A => ChurchList[B]): ChurchList[B] = new ChurchList[B] {
    def fold[R](z: Eval[R])(ag: B => Eval[R] => Eval[R]): Eval[R] =
      self.fold(z)(a => r => f(a).fold(r)(ag))
  }

  def ++(that: ChurchList[A]): ChurchList[A] = new ChurchList[A] {
    def fold[R](z: Eval[R])(ag: A => Eval[R] => Eval[R]): Eval[R] = self.fold(that.fold(z)(ag))(ag)
  }

  def headOption: Option[A] = fold[Option[A]](Eval.now(None))(a => b => Eval.now(Some(a))).value

  def toList: List[A] = fold[List[A]](Eval.now(Nil))(e => ll => ll.map(e :: _)).value
}

object ChurchList {
  def apply[A](xs: A*): ChurchList[A] = new ChurchList[A] {
    def fold[R](z: Eval[R])(ag: A => Eval[R] => Eval[R]): Eval[R] = xs.foldRight(z)((a, lb) => ag(a)(lb))
  }

  def empty[A]: ChurchList[A] = new ChurchList[A] {
    def fold[R](z: Eval[R])(ag: A => Eval[R] => Eval[R]): Eval[R] = z
  }
}

object Lecture9 extends App {
  val list = List.range(1, 10000).foldRight(ChurchList.empty[Int])(_ :: _)

  println(list.fold(Eval.now(0))(x => y => y.map(_ + x)).value)
}

object Lecture9IO extends IOApp {
  def currentTime: IO[String] =
    for (millis <- Timer[IO].clock.realTime(MILLISECONDS))
      yield Instant.ofEpochMilli(millis).toString

  def produce(name: String, mvar: MVar[IO, (String, Double)], idx: Int = 0): IO[Nothing] =
    for {
      _    <- IO(println(s"producing $name $idx"))
      _    <- mvar.put(name -> (idx * 0.5))
      time <- currentTime
      _    <- IO(println(s"produced $name $idx $time"))
      _    <- Timer[IO].sleep(1.1 second)
      res  <- produce(name, mvar, idx + 1)
    } yield res

  def consumer(name: String, mvar: MVar[IO, (String, Double)], limit: Double = 10): IO[String] =
    for {
      _                  <- IO(println(s"consuming $name left $limit"))
      (pinger, quantity) <- mvar.take
      time               <- currentTime
      _                  <- IO(println(s"$name received $quantity from $pinger $time"))
      _                  <- Timer[IO].sleep(1.4 second)
      res                <- if (limit > quantity) consumer(name, mvar, limit - quantity) else IO(name)
    } yield res

  val process: IO[Unit] = for {
    x       <- MVar[IO].empty[(String, Double)]
    y       <- MVar[IO].empty[(String, Double)]
    charlie <- produce("Charlie", x).start
    alice   <- produce("Alice", x).start
    stas    <- produce("Stas", y).start
    name    <- consumer("Bob", x).race(consumer("Oleg", y, 1000))
    _       <- IO(println(s"completed $name"))
  } yield ()

  def run(args: List[String]): IO[ExitCode] = process as ExitCode.Success
}
