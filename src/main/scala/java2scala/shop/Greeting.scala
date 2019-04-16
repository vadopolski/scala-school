package java2scala.shop

import akka.actor.{ActorRef, ActorSystem}
import cats.effect.{ContextShift, IO}
import java2scala.shop.actors.Greeter

import scala.concurrent.duration.FiniteDuration

final class Greeting(actor: ActorRef) {
  def greet(name: String, duration: FiniteDuration)(implicit cs: ContextShift[IO]): IO[String] =
    for {
      cont <- IO.async[CancelCont[String]] { cont =>
               actor ! Greeter.Message(
                 name,
                 s => cont(Right(s)),
                 duration,
               )
             }
      res <- IO.cancelable[String] { k =>
              val f = cont(s => k(Right(s)))
              IO(f())
            }
    } yield res

}

object Greeting {
  def greeting(system: ActorSystem): IO[Greeting] =
    for (ref <- IO(system.actorOf(Greeter.props))) yield new Greeting(ref)

}
