package java2scala.shop.actors

import akka.actor.{Actor, Props}
import java2scala.shop.actors.Greeter.{Message, Respond}

import scala.concurrent.duration.FiniteDuration

class Greeter extends Actor {
  def receive: Receive = {
    case Message(name, ack, dur) =>
      implicit val ec = context.system.dispatcher
      context.system.scheduler.scheduleOnce(dur, self, Respond(name, ack))
    case Respond(name, ack) => ack(s"Hello, $name!")
  }
}

object Greeter {
  case class Message(name: String, ack: String => Unit, dur: FiniteDuration)
  case class Respond(name: String, ack: String => Unit)


  def props: Props = Props[Greeter]
}
