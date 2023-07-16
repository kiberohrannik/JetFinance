package com.kiber.jet.finance.core.actor

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import com.kiber.jet.finance.core.actor.AcquirerActor.{AcquirerMessage, OnInitializedC2C}
import com.kiber.jet.finance.core.domain.Requisites

object SenderEmitterActor {

  sealed trait SenderEmitterMessage

  sealed trait SenderEmitterCommand extends SenderEmitterMessage
  final case class InitializeC2C(requisites: Requisites, sender: ActorRef[AcquirerMessage]) extends SenderEmitterCommand


  def apply(): Behavior[SenderEmitterMessage] = Behaviors.setup { context =>
    Behaviors.receiveMessage {
      case InitializeC2C(requisites, sender) =>
        println("InitializeC2C")
        sender ! OnInitializedC2C(requisites)
        Behaviors.same
    }
  }
}
