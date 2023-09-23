package com.kiber.jet.finance.core.actor

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import com.kiber.jet.finance.core.actor.P2PManagerActor.{CardDataEvent, OnResolvedRecipientCardData, OnResolvedSenderCardData}
import com.kiber.jet.finance.core.domain.Requisites
import com.kiber.jet.finance.core.service.card.CardVerifier

object CardDataActor {

  sealed trait CardDataMessage

  sealed trait CardDataCommand extends CardDataMessage
  final case class ResolveSenderCard(requisites: Requisites, sender: ActorRef[CardDataEvent]) extends CardDataCommand
  final case class ResolveRecipientCard(requisites: Requisites, sender: ActorRef[CardDataEvent]) extends CardDataCommand


  def apply(cardVerifiers: Seq[CardVerifier]): Behavior[CardDataMessage] = Behaviors.receiveMessage {
    case ResolveSenderCard(cardRequisites, sender) =>
      println("ResolveSenderCard")
      cardVerifiers.foreach(_ verify cardRequisites.incoming.cardData)

      sender ! OnResolvedSenderCardData(cardRequisites)
      Behaviors.same

    case ResolveRecipientCard(cardRequisites, sender) =>
      println("ResolveRecipientCard")
      cardVerifiers.foreach(_ verify cardRequisites.incoming.cardData)

      sender ! OnResolvedRecipientCardData(cardRequisites)
      Behaviors.same
  }
}
