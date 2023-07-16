package com.kiber.jet.finance.core.actor

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import com.kiber.jet.finance.core.actor.AcquirerActor.{AcquirerEvent, OnResolvedCardData}
import com.kiber.jet.finance.core.domain.Requisites

object CardDataActor {

  sealed trait CardDataMessage

  sealed trait CardDataCommand extends CardDataMessage
  final case class ResolveCard(requisites: Requisites, sender: ActorRef[AcquirerEvent]) extends CardDataCommand


  def apply(): Behavior[CardDataMessage] = Behaviors.receiveMessage {
    case ResolveCard(cardRequisites, sender) =>
      println("ResolveCard")
      cardRequisites.resolved.cardData = resolve(cardRequisites.incoming.cardData)
      sender ! OnResolvedCardData(cardRequisites)
      Behaviors.same
  }

  private def resolve(data: String): String = {
    println("Mocked card data resolution...")
    "resolved: " + data
  }
}
