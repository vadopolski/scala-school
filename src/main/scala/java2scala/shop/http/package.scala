package java2scala.shop
import akka.http.scaladsl.marshalling.{Marshaller, ToEntityMarshaller, ToResponseMarshaller}
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import cats.effect.IO
import io.circe.Encoder
import akka.http.scaladsl.model.MediaTypes.{`application/json` => JSON}
import akka.http.scaladsl.server.{Directives, Route}
import akka.stream.scaladsl.Source
import akka.util.ByteString
import cats.Monoid
import io.circe.syntax._
import cats.implicits._
import Directives._

package object http {
  implicit def toResponseMarshaller[A: Encoder]: ToEntityMarshaller[IO[A]] =
    Marshaller.withFixedContentType(JSON) { ia: IO[A] =>
      HttpEntity(JSON, Source.fromFuture(ia.map(a => ByteString(a.asJson.noSpaces)).unsafeToFuture()))
    }

  implicit val ioRouteMonoid: Monoid[IO[Route]] = new Monoid[IO[Route]] {
    def empty: IO[Route]                               = IO.pure(reject)
    def combine(x: IO[Route], y: IO[Route]): IO[Route] = (x, y).mapN(_ ~ _)
  }
}
