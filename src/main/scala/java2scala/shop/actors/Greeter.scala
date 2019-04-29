package java2scala.shop
package actors

import akka.actor.{Actor, Props}
import java2scala.shop.actors.Greeter.{Message, Respond}

import scala.concurrent.duration.FiniteDuration

class Greeter extends Actor {
  def receive: Receive = {
    case Message(name, continue, dur) =>
      implicit val ec = context.system.dispatcher

      continue { ack =>
        val task = context.system.scheduler.scheduleOnce(dur, self, Respond(name, ack))
        () => {
          println(s"canceling $name")
          task.cancel()
        }
      }
    case Respond(name, ack) =>
      println(s"responding $name")
      ack(s"Hello, $name!")
  }
}

object Greeter{

  case class Message(name: String, continue: CancelCont[String] => Unit, dur: FiniteDuration)
  case class Respond(name: String, ack: String => Unit)

  def props: Props = Props[Greeter]
}
