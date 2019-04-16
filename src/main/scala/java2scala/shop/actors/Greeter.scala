package java2scala.shop.actors

import akka.actor.{Actor, Props}
import java2scala.shop.actors.Greeter.Message

class Greeter extends Actor {
  def receive: Receive = {
    case Message(name, ack) => ack(s"Hello, $name!")
  }
}

object Greeter {
  case class Message(name: String, ack: String => Unit)

  def props: Props = Props[Greeter]
}
