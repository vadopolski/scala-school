package java2scala.shop

import akka.actor.{ActorRef, ActorSystem}
import cats.effect.IO
import akka.pattern.ask
import akka.util.Timeout
import java2scala.shop.actors.Greeter

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

final class Greeting(actor: ActorRef)(implicit timeout: Timeout, executionContext: ExecutionContext) {
  def greet(name: String): IO[String] =
    IO.fromFuture(IO((actor ? Greeter.Message(name)).collect {
      case s: String => s
    }))
}

object Greeting {
  def greeting(system: ActorSystem): IO[Greeting] =
    for {
      ref <- IO(system.actorOf(Greeter.props))
    } yield {
      implicit val timeout: Timeout = Timeout(1.second)
      import system.dispatcher
      new Greeting(ref)
    }

}
