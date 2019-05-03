package java2scala.shop
import java.util.concurrent.Executors

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import cats.effect.{ExitCode, IO, IOApp, Resource}
import java2scala.shop.http.{greeterHttp, resourceHttp}

import scala.concurrent.ExecutionContext
import cats.implicits._
import akka.http.scaladsl.server.Directives._

object Shop extends IOApp {

  def system: IO[ActorSystem] = IO(ActorSystem())

  def runServerAndSystem(route: Route)(implicit system: ActorSystem): IO[Unit] =
    for {
      binding <- IO.fromFuture(IO {
                  implicit val mat = ActorMaterializer()
                  Http().bindAndHandle(route, "0.0.0.0", 8080)
                })
      res <- IO(println(binding))
    } yield res

  val blocking: Resource[IO, ExecutionContext] =
    Resource
      .make(IO(Executors.newCachedThreadPool()))(exec => IO(exec.shutdown()))
      .map(ExecutionContext.fromExecutor)

  def run(args: List[String]): IO[ExitCode] =
    blocking.use { blockingCS =>
      for {
        cartStore                   <- IOCartStore.create
        productStore                <- SimpleStore.fromResource[Product]("/shop/products.json", blockingCS)
        userStore                   <- SimpleStore.fromResource[User]("/shop/users.json", blockingCS)
        productRoute                = resourceHttp.route("product", productStore)
        userRoute                   = resourceHttp.route("user", userStore)
        implicit0(sys: ActorSystem) <- system
        greeting                    <- Greeting.greeting(sys)
        helloRoute                  = greeterHttp.route(greeting)
        _                           <- runServerAndSystem(productRoute ~ userRoute ~ helloRoute)
        _                           <- IO.never
      } yield ExitCode.Success
    }
}
