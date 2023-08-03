package com.kiber.jet.finance.core.actor

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import com.kiber.jet.finance.core.actor.P2PManagerActor.{P2PEvent, OnResolvedCardData}
import com.kiber.jet.finance.core.domain.Requisites
import com.kiber.jet.finance.core.service.CardDataResolver

object CardDataActor {

  sealed trait CardDataMessage

  sealed trait CardDataCommand extends CardDataMessage
  final case class ResolveCard(requisites: Requisites, sender: ActorRef[P2PEvent]) extends CardDataCommand


  def apply(cardResolver: CardDataResolver): Behavior[CardDataMessage] = Behaviors.receiveMessage {
    case ResolveCard(cardRequisites, sender) =>
      println("ResolveCard")
      cardResolver.resolve(cardRequisites.incoming.cardData)
      sender ! OnResolvedCardData(cardRequisites)
      Behaviors.same
  }
}
