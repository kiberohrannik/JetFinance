package com.kiber.jet.finance.core.actor

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import com.kiber.jet.finance.core.actor.AcquirerActor.{AcquirerMessage, OnCompletedC2C}
import com.kiber.jet.finance.core.domain.Requisites

object ReceiverEmitterActor {

  sealed trait ReceiverEmitterMessage

  sealed trait ReceiverEmitterCommand extends ReceiverEmitterMessage
  final case class CompleteC2C(requisites: Requisites, sender: ActorRef[AcquirerMessage]) extends ReceiverEmitterCommand

  def apply(): Behavior[ReceiverEmitterMessage] = Behaviors.receive((context, message) =>
    message match {
      case CompleteC2C(requisites, sender) =>
        println("CompleteC2C")
        sender ! OnCompletedC2C(requisites)
        Behaviors.same
    }
  )
}
