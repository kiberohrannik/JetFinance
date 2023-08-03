package com.kiber.jet.finance.core.actor

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import com.kiber.jet.finance.core.actor.CardDataActor.{CardDataMessage, ResolveCard}
import com.kiber.jet.finance.core.actor.ReceiverEmitterActor.{CompleteP2P, ReceiverEmitterMessage}
import com.kiber.jet.finance.core.actor.SenderEmitterActor.{InitializeP2P, SenderEmitterMessage}
import com.kiber.jet.finance.core.domain.Requisites

object P2PManagerActor {

  sealed trait P2PMessage

  sealed trait P2PCommand extends P2PMessage
  final case class StartP2P(requisites: Requisites) extends P2PCommand

  sealed trait P2PEvent extends P2PMessage
  final case class OnResolvedCardData(resolvedRequisites: Requisites) extends P2PEvent
  final case class OnInitializedP2P(requisites: Requisites) extends P2PEvent
  final case class OnCompletedP2P(requisites: Requisites) extends P2PEvent


  def apply(cardDataActor: ActorRef[CardDataMessage],
            senderEmitterActor: ActorRef[SenderEmitterMessage],
            receiverEmitterActor: ActorRef[ReceiverEmitterMessage]): Behavior[P2PMessage] =
    Behaviors.setup { context =>
      Behaviors.receiveMessage {
        case StartP2P(requisites) =>
          println("C2CRequest")
          cardDataActor ! ResolveCard(requisites, context.self)
          Behaviors.same

        case OnResolvedCardData(requisites) =>
          println("OnResolvedCardData")
          senderEmitterActor ! InitializeP2P(requisites, context.self)
          Behaviors.same

        case OnInitializedP2P(requisites) =>
          println("OnInitializedC2C")
          receiverEmitterActor ! CompleteP2P(requisites, context.self)
          Behaviors.same

        case OnCompletedP2P(requisites) =>
          println("OnCompletedC2C")
          println("Start clearing...")
          Behaviors.same
      }
    }
}
