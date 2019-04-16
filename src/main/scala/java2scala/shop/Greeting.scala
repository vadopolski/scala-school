package java2scala.shop

import akka.actor.{ActorRef, ActorSystem}
import cats.effect.concurrent.Ref
import cats.effect.{ContextShift, IO}
import java2scala.shop.actors.Greeter
import cats.syntax.apply._

import scala.concurrent.duration.FiniteDuration

final class Greeting(actor: ActorRef) {
  def greet(name: String, duration: FiniteDuration)(implicit cs: ContextShift[IO]): IO[String] =
    for {
      ref <- Ref[IO].of(() => ())
      res <- IO.cancelable[String] { k =>
              actor ! Greeter.Message(
                name,
                s => k(Right(s)),
                duration,
                f => ref.set(f).unsafeRunSync()
              )

              ref.get.flatMap(g =>
                IO {
                  println("cancelation executed")
                  g()
              })
            }
    } yield res

}

object Greeting {
  def greeting(system: ActorSystem): IO[Greeting] =
    for (ref <- IO(system.actorOf(Greeter.props))) yield new Greeting(ref)

}
