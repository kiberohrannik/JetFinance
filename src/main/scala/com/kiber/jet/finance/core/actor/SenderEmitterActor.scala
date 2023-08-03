package com.kiber.jet.finance.core.actor

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import com.kiber.jet.finance.core.actor.P2PManagerActor.{P2PMessage, OnInitializedP2P}
import com.kiber.jet.finance.core.domain.Requisites

object SenderEmitterActor {

  sealed trait SenderEmitterMessage

  sealed trait SenderEmitterCommand extends SenderEmitterMessage
  final case class InitializeP2P(requisites: Requisites, sender: ActorRef[P2PMessage]) extends SenderEmitterCommand


  def apply(): Behavior[SenderEmitterMessage] = Behaviors.setup { context =>
    Behaviors.receiveMessage {
      case InitializeP2P(requisites, sender) =>
        println(s"${context.self.path} ------- InitializeC2C")
        sender ! OnInitializedP2P(requisites)
        Behaviors.same
    }
  }
}
