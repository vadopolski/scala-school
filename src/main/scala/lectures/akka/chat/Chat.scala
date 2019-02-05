package lectures.akka.chat

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.scaladsl.AbstractBehavior
import akka.actor.typed.{ActorRef, Behavior}

object Chat {
  sealed trait Message

  final case class SendText(login: String, text: String)                      extends Message
  final case class Connect(login: String, session: ActorRef[Session.Message]) extends Message
  final case class Disconnect(session: ActorRef[Session.Message])             extends Message

  def behavior(name: String): Behavior[Message] = new Behave(name)

  private class Behave(name: String) extends AbstractBehavior[Message] {
    var sessions = Set.empty[ActorRef[Session.Message]]

    def onMessage(msg: Message): Behavior[Message] = {
      msg match {
        case SendText(login, text) =>
          sessions.foreach(_ ! Session.NewMessage(login, text))
        case Connect(login, session) =>
          (sessions - session).foreach(_ ! Session.UserEnter(login))
          sessions += session
        case Disconnect(session) =>
          sessions -= session
      }
      Behaviors.same
    }

  }
}
