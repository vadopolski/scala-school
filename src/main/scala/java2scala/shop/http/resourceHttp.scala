package java2scala.shop.http

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import cats.effect.IO
import io.circe.Encoder
import java2scala.shop.{ProductStore, SimpleStore}

object resourceHttp {
  def route[A: Encoder](prefix: String, store: SimpleStore[A]): Route =
    pathPrefix(prefix)(
      (get & path("all"))(complete(store.all))
    )
}
