package java2scala

import java.time.Instant

import cats.Eval
import cats.effect.{ExitCode, IO, IOApp, Timer}
import cats.effect.concurrent.MVar

import scala.concurrent.duration._
import cats.implicits._

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

  def ping(name: String, mvar: MVar[IO, String], idx: Int = 0): IO[Nothing] =
    for {
      _    <- IO(println(s"pinging $name $idx"))
      _    <- mvar.put(name)
      time <- currentTime
      _    <- IO(println(s"pinged $name $idx $time"))
      _    <- Timer[IO].sleep(1.1 second)
      res  <- ping(name, mvar, idx + 1)
    } yield res

  def pong(name: String, mvar: MVar[IO, String], idx: Int = 0): IO[Nothing] =
    for {
      _      <- IO(println(s"ponging $name $idx"))
      pinger <- mvar.take
      time   <- currentTime
      _      <- IO(println(s" $name received ping from $pinger $time"))
      _      <- Timer[IO].sleep(1.4 second)
      res    <- pong(name, mvar, idx + 1)
    } yield res

  val process: IO[Unit] = for {
    List(x, y) <- MVar[IO].empty[String].replicateA(2)
    bob        <- pong("Bob", x).start
    oleg       <- pong("Oleg", y).start
    charlie    <- ping("Charlie", x).start
    alice      <- ping("Alice", x).start
    stas       <- ping("Stas", y).start
    _          <- Timer[IO].sleep(5 minutes)
  } yield ()

  def run(args: List[String]): IO[ExitCode] = process as ExitCode.Success
}
