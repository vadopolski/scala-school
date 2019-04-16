package java2scala.shop

import akka.actor.{ActorRef, ActorSystem}
import cats.effect.IO
import java2scala.shop.actors.Greeter

import scala.concurrent.duration.FiniteDuration

final class Greeting(actor: ActorRef) {
  def greet(name: String, duration: FiniteDuration): IO[String] =
    IO.async { k =>
      actor ! Greeter.Message(name, s => k(Right(s)), duration)
    }
}

object Greeting {
  def greeting(system: ActorSystem): IO[Greeting] =
    for (ref <- IO(system.actorOf(Greeter.props))) yield new Greeting(ref)

}
