package java2scala

import cats.effect.{ExitCode, IO, IOApp, Timer}

import scala.concurrent.duration._
import scala.io.StdIn
import scala.language.postfixOps
import cats.implicits._

object Lecture7 extends IOApp {
  val helloWorld = IO(println("Hello world"))

  def printlnIO(str: String): IO[Unit] = IO(println(str))
  val readLn: IO[String]               = IO(StdIn.readLine()) //TODO : make async !!!

  def elapsed(seconds: Long = 0): IO[Nothing] =
    for {
      _   <- printlnIO(s"$seconds seconds elapsed")
      _   <- Timer[IO].sleep(1 second)
      res <- elapsed(seconds + 1)
    } yield res

  def elapsed2(seconds: Long = 0): IO[Nothing] =
    printlnIO(s"$seconds seconds elapsed") *>
      Timer[IO].sleep(1 second) *>
      elapsed(seconds + 1)

  val waitForEnter = printlnIO("press Enter to exit") <* readLn

  val app = for {
    _    <- printlnIO("write your name: ")
    name <- readLn
    _    <- printlnIO(s"Hello, $name")
  } yield ExitCode.Success

  def run(args: List[String]): IO[ExitCode] =
    for {
      res <- (Timer[IO].sleep(1 millisecond) *> elapsed()).start
      _ <- waitForEnter
    } yield ExitCode.Success
}
