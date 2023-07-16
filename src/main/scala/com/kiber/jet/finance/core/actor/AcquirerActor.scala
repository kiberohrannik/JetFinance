package com.kiber.jet.finance.core.actor

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import com.kiber.jet.finance.core.actor.CardDataActor.{CardDataMessage, ResolveCard}
import com.kiber.jet.finance.core.actor.ReceiverEmitterActor.{CompleteC2C, ReceiverEmitterMessage}
import com.kiber.jet.finance.core.actor.SenderEmitterActor.{InitializeC2C, SenderEmitterMessage}
import com.kiber.jet.finance.core.domain.Requisites

object AcquirerActor {

  sealed trait AcquirerMessage

  sealed trait AcquirerCommand extends AcquirerMessage
  final case class C2CRequest(requisites: Requisites) extends AcquirerCommand

  sealed trait AcquirerEvent extends AcquirerMessage
  final case class OnResolvedCardData(resolvedRequisites: Requisites) extends AcquirerEvent
  final case class OnInitializedC2C(requisites: Requisites) extends AcquirerEvent
  final case class OnCompletedC2C(requisites: Requisites) extends AcquirerEvent


  def apply(cardDataActor: ActorRef[CardDataMessage],
            senderEmitterActor: ActorRef[SenderEmitterMessage],
            receiverEmitterActor: ActorRef[ReceiverEmitterMessage]): Behavior[AcquirerMessage] =
    Behaviors.setup { context =>
      Behaviors.receiveMessage {
        case C2CRequest(requisites) =>
          println("C2CRequest")
          cardDataActor ! ResolveCard(requisites, context.self)
          Behaviors.same

        case OnResolvedCardData(requisites) =>
          println("OnResolvedCardData")
          senderEmitterActor ! InitializeC2C(requisites, context.self)
          Behaviors.same

        case OnInitializedC2C(requisites) =>
          println("OnInitializedC2C")
          receiverEmitterActor ! CompleteC2C(requisites, context.self)
          Behaviors.same

        case OnCompletedC2C(requisites) =>
          println("OnCompletedC2C")
          println("Start clearing...")
          Behaviors.same
      }
    }
}
