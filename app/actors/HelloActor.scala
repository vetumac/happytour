package actors

import akka.actor.{Actor, Props}

object HelloActor {
  def props = Props[HelloActor]

  case class SayHello(name: String)

}

class HelloActor extends Actor {

  import HelloActor._

  def receive = {
    case SayHello(name: String) =>
      sender() ! "Hello, " + name
  }
}