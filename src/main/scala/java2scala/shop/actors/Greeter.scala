package java2scala.shop.actors

import akka.actor.{Actor, Props}
import cats.effect.IO
import cats.effect.concurrent.Ref
import java2scala.shop.actors.Greeter.{Message, Respond}

import scala.concurrent.duration.FiniteDuration

class Greeter extends Actor {
  def receive: Receive = {
    case Message(name, ack, dur, cancel) =>
      implicit val ec = context.system.dispatcher
      val task        = context.system.scheduler.scheduleOnce(dur, self, Respond(name, ack))

      cancel { () =>
        println(s"canceling $name")
        task.cancel()
      }
    case Respond(name, ack) =>
      println(s"responding $name")
      ack(s"Hello, $name!")
  }
}

object Greeter {
  case class Message(name: String, ack: String => Unit, dur: FiniteDuration, cancel: (() => Unit) => Unit)
  case class Respond(name: String, ack: String => Unit)

  def props: Props = Props[Greeter]
}
