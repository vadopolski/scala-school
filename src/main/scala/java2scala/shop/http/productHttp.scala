package java2scala.shop.http

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import cats.effect.IO
import java2scala.shop.ProductStore

object productHttp {
  def route(store: ProductStore): Route =
    pathPrefix("product")(
      (get & path("all"))(complete(store.all))
    )

}
