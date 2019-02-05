package inclasss

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.scaladsl.AskPattern._
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.util.Timeout

import scala.concurrent.duration._

object AkkaTypedHello {

  sealed trait Command

  object Command {

    final case class GetGreeter(word: String, respond: ActorRef[ActorRef[GreeterCommand]]) extends Command

    final case class GetGreeting(word: String, personName: String, respond: ActorRef[String]) extends Command

    final case class Delayed(command: Command) extends Command

  }

  sealed trait GreeterCommand

  object GreeterCommand {

    final case class GetGreeting(personName: String, respond: ActorRef[String]) extends GreeterCommand

  }

  def greeter(word: String): Behavior[GreeterCommand] = {
    import GreeterCommand._
    Behaviors.receiveMessage {
      case GetGreeting(personName, respond) =>
        respond ! s"$word, $personName !"
        Behaviors.same
    }
  }

  val system = ActorSystem[Command](
    Behaviors.withTimers[Command] { timers =>
      import Command._
      Behaviors.receive[Command] {
        (ctx, message) =>
          def getGreeter(word: String): ActorRef[GreeterCommand] =
            ctx
              .child(word)
              .map(_.asInstanceOf[ActorRef[GreeterCommand]])
              .getOrElse(ctx.spawn(greeter(word), word))

          message match {
            case GetGreeter(word, respond) =>
              val actor = getGreeter(word)
              respond ! actor

            case msg @ GetGreeting(word, personName, respond) =>
              timers.startSingleTimer("delay", Delayed(msg), 100.millis)

            case Delayed(GetGreeting(word, personName, respond)) =>
              getGreeter(word) ! GreeterCommand.GetGreeting(personName, respond)

          }
          Behaviors.same
      }
    },
    "hello"
  )

  def main(args: Array[String]): Unit = {

    import system.executionContext
    implicit val sched   = system.scheduler
    implicit val timeout = Timeout(10.second)

    (system ? ((respond: ActorRef[String]) => Command.GetGreeting("aloha", "Anton", respond))).onComplete {
      case util.Success(message) =>
        println(s"received message: $message")
      case util.Failure(ex) =>
        ex.printStackTrace()
    }

    val greeterRef =
      system ? ((respond: ActorRef[ActorRef[GreeterCommand]]) => Command.GetGreeter("hello", respond))

    greeterRef.foreach(_ ? ((respond: ActorRef[String]) => GreeterCommand.GetGreeting("Oleg", respond)) onComplete {
      case util.Success(message) =>
        println(s"received message: $message")
      case util.Failure(ex) =>
        ex.printStackTrace()
    })
    //    val greeter = system ? (GetGreeter("hello", _: ActorRef[ActorRef[String]]))

    //    system  ("hello", "Oleg")
    //    system ! ("aloha", "Anton")
    //    system ! ("hello", "Anton")

    //    system.terminate()
  }

}
