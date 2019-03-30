package java2scala

import cats.effect.{ExitCode, IO, IOApp}
import akka.http.scaladsl.server._
import Directives._
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.ToResponseMarshaller
import akka.http.scaladsl.model.StatusCodes
import akka.stream.{ActorMaterializer, Materializer}
import cats.effect.concurrent.Ref
import cats.implicits._

import scala.collection.immutable
object Lecture8 extends IOApp {

//  implicit val ioListStringMarshaller: ToResponseMarshaller[IO[List[String]]] = ???

  def route(tags: AppTags): Route =
    handleRejections(RejH) {
      path("") {
        complete("Hello World")
      } ~
        pathPrefix("tags") {
          get { complete(tags.allTags.map(_.toString()).unsafeToFuture()) } ~
            (put & parameter("tag")) { tag =>
              complete(tags.addTag(tag).as("ok added").unsafeToFuture())
            }
        } ~
        pathPrefix("users") {
          parameters(("id".as[Int].?, "version".?)) { (id, version) =>
            complete(s"id = $id version = $version")
          }
        } ~
        pathPrefix("products" / IntNumber / LongNumber / Segment) { (num, num2, str) =>
          complete(s"product[$num, $num2] : $str") // http://localhost:8080/products/3/5/etert
        } ~
        pathPrefix("foo") {
          parameterMultiMap { pm =>
            complete(pm.get("bar").toString)
          }
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
    for {
      tags     <- AppTags(Nil)
      appRoute = route(tags)
      _        <- IO.fromFuture(IO(Http().bindAndHandle(appRoute, "0.0.0.0", 8080)))
    } yield ExitCode.Success

}

final class AppTags(ref: Ref[IO, List[String]]) {
  def allTags: IO[List[String]]     = ref.get
  def addTag(tag: String): IO[Unit] = ref.update(tag :: _)
}

object AppTags {
  def apply(initial: List[String]): IO[AppTags] =
    for (ref <- Ref[IO].of(initial)) yield new AppTags(ref)
}
