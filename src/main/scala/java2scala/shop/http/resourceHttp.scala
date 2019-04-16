package java2scala.shop.http

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import cats.effect.{ContextShift, IO, Timer}
import io.circe.Encoder
import java2scala.shop.{Greeting, ProductStore, SimpleStore}

import scala.concurrent.duration.{Duration, FiniteDuration}

object resourceHttp {
  def route[A: Encoder](prefix: String, store: SimpleStore[A])(implicit timer: Timer[IO], cs: ContextShift[IO]): Route =
    pathPrefix(prefix)(
      (get & path("all"))(complete(store.all))
    )
}

object greeterHttp {
  def route(greeting: Greeting)(implicit timer: Timer[IO], cs: ContextShift[IO]): Route =
    pathPrefix("greet") {
      (get & parameter("name") & parameter("duration")) { (name, durStr) =>
        Duration.apply(durStr) match {
          case Duration.Inf        => complete(StatusCodes.BadRequest)
          case dur: FiniteDuration => complete(greeting.greet(name, dur))
        }

      }
    }
}
