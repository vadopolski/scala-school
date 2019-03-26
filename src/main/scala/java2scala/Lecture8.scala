package java2scala

import cats.effect.{ExitCode, IO, IOApp}
import akka.http.scaladsl.server._
import Directives._
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.{ActorMaterializer, Materializer}
import cats.effect

import cats.implicits._
object Lecture8 extends IOApp {
  val route: Route = complete("Hello World")

  implicit val system: ActorSystem        = ActorSystem("our-server")
  implicit val materializer: Materializer = ActorMaterializer()

  def run(args: List[String]): IO[ExitCode] =
    IO.fromFuture(IO(Http().bindAndHandle(route, "0.0.0.0", 8080))) as ExitCode.Success
}
