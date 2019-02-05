package lectures.akka.chat

import akka.actor.typed.scaladsl.AbstractBehavior
import akka.actor.typed.{ActorRef, ActorSystem, Behavior, scaladsl}
import akka.{actor => untyped}
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import untyped.BootstrapSetup
import untyped.typed.scaladsl.ActorContext
import untyped.typed.scaladsl.Behaviors

object Hub extends LazyLogging {
  sealed trait Message

  final case class GetChats(session: ActorRef[Session.Message]) extends Message
  final case class Connect(name: String, login: String, session: ActorRef[Session.Message]) extends Message
  final case class NewSession(respond: ActorRef[ActorRef[Session.Message]]) extends Message
  final case class NewChat(name: String) extends Message


  def apply(name: String = "chat-hub", config: Option[Config] = None) =
    config.fold(ActorSystem(behaviour, name))(ActorSystem(behaviour, name, _))

  def behaviour: Behavior[Message] = Behaviors.setup(new Behave(_))


  class Behave(ctx: ActorContext[Message]) extends AbstractBehavior[Message] {
    var chats = Map.empty[String, ActorRef[Chat.Message]]
    def onMessage(msg: Message): Behavior[Message] = {
      msg match {
        case GetChats(session)             => session ! Session.ChatList(chats.keys.toList)
        case Connect(name, login, session) =>
          chats.get(name).foreach { chat =>
            chat ! Chat.Connect(login, session)
            session ! Session.Connected(name, login, chat)
          }
        case NewSession(respond)           =>
          logger.info("new client connected")
          respond ! ctx.spawnAnonymous(Session.initial)
        case NewChat(name)                 =>
          chats += name -> ctx.spawn(Chat.behavior(name), s"chat-$name")
      }
      Behaviors.same
    }
  }

}
