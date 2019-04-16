package java2scala.shop.http

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import cats.effect.IO
import io.circe.Encoder
import java2scala.shop.{Greeting, ProductStore, SimpleStore}

object resourceHttp {
  def route[A: Encoder](prefix: String, store: SimpleStore[A]): Route =
    pathPrefix(prefix)(
      (get & path("all"))(complete(store.all))
    )
}

object greeterHttp {
  def route(greeting: Greeting): Route =
    pathPrefix("greet") {
      (get & parameter("name")) { name =>
        complete(greeting.greet(name))
      }
    }
}
