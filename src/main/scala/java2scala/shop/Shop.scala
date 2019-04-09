package java2scala.shop
import java.util.concurrent.Executors

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import cats.effect.{ExitCode, IO, IOApp, Resource}
import java2scala.shop.http.productHttp

import scala.concurrent.ExecutionContext
import cats.implicits._

object Shop extends IOApp {

  def runServer(route: Route): IO[Unit] =
    IO.fromFuture(IO {
        implicit val system = ActorSystem()
        implicit val mat    = ActorMaterializer()

        Http().bindAndHandle(route, "0.0.0.0", 8080)
      })
      .flatMap(s => IO(println(s)))
      .void

  val blocking: Resource[IO, ExecutionContext] =
    Resource
      .make(IO(Executors.newCachedThreadPool()))(exec => IO(exec.shutdown()))
      .map(ExecutionContext.fromExecutor)

  def run(args: List[String]): IO[ExitCode] =
    blocking.use { blockingCS =>
      for {
        cartStore    <- IOCartStore.create
        productStore <- ProductStore.fromResource("/shop/products.json", blockingCS)
        route1       = productHttp.route(productStore)
        _            <- runServer(route1)
        _            <- IO.never
      } yield ExitCode.Success
    }
}
