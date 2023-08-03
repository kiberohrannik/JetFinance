package com.kiber.jet.finance.core.actor

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import com.kiber.jet.finance.core.actor.P2PManagerActor.{P2PMessage, OnCompletedP2P}
import com.kiber.jet.finance.core.domain.Requisites

object ReceiverEmitterActor {

  sealed trait ReceiverEmitterMessage

  sealed trait ReceiverEmitterCommand extends ReceiverEmitterMessage
  final case class CompleteP2P(requisites: Requisites, sender: ActorRef[P2PMessage]) extends ReceiverEmitterCommand

  def apply(): Behavior[ReceiverEmitterMessage] = Behaviors.receive((context, message) =>
    message match {
      case CompleteP2P(requisites, sender) =>
        println(s"${context.self.path} ------- CompleteC2C")
        sender ! OnCompletedP2P(requisites)
        Behaviors.same
    }
  )
}
