package controllers

import javax.inject.Inject

import actors.HelloActor
import actors.HelloActor.SayHello
import akka.actor.ActorSystem
import akka.pattern.ask
import akka.util.Timeout
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Action, Controller}

import scala.concurrent.duration._

class Application @Inject()(system: ActorSystem) extends Controller {

  val helloActor = system.actorOf(HelloActor.props, "hello-actor")

  implicit val timeout: Timeout = 5.seconds

  def sayHello(name: String) = Action.async {
    (helloActor ? SayHello(name)).mapTo[String].map { message =>
      Ok(message)
    }
  }


}
