package java2scala

import cats.effect.{ExitCode, IO, IOApp}
import akka.http.scaladsl.server._
import Directives._
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.stream.{ActorMaterializer, Materializer}
import cats.implicits._

import scala.collection.immutable
object Lecture8 extends IOApp {
  val route: Route =
    handleRejections(RejH) {
      path("") {
        complete("Hello World")
      } ~
        pathPrefix("users") {
          parameter("id".as[Int]) { id =>
            complete(s"hello, $id")
          }
        } ~
        pathPrefix("products" / IntNumber / LongNumber / Segment) { (num, num2, str) =>
          complete(s"product[$num, $num2] : $str") // http://localhost:8080/products/3/5/etert
        }
    }

  case object MyRejection extends Rejection

  object RejH extends RejectionHandler {
    def apply(rejs: immutable.Seq[Rejection]): Option[Route] =
      if (rejs.contains(MyRejection))
        Some(complete(StatusCodes.NotImplemented, "lol no"))
      else None

  }

  implicit val system: ActorSystem        = ActorSystem("our-server")
  implicit val materializer: Materializer = ActorMaterializer()

  def run(args: List[String]): IO[ExitCode] =
    IO.fromFuture(IO(Http().bindAndHandle(route, "0.0.0.0", 8080))) as ExitCode.Success
}
