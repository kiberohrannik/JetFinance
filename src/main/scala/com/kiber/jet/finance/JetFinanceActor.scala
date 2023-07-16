package com.kiber.jet.finance

import akka.NotUsed
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorSystem, Behavior, Terminated}
import com.kiber.jet.finance.core.actor.AcquirerActor.C2CRequest
import com.kiber.jet.finance.core.actor.{AcquirerActor, CardDataActor, ReceiverEmitterActor, SenderEmitterActor}
import com.kiber.jet.finance.core.domain.{IncomingRequisites, Requisites, ResolvedRequisites}

import scala.util.Random

object JetFinanceActor {

  sealed trait JetFinanceMessage

  def apply(): Behavior[NotUsed] = Behaviors.setup { context =>
    val cardDataActor = context.spawn(CardDataActor.apply(), "CardDataActor")
    val senderEmitterActor = context.spawn(SenderEmitterActor.apply(), "SenderEmitterActor")
    val receiverEmitterActor = context.spawn(ReceiverEmitterActor.apply(), "ReceiverEmitterActor")

    val acquirerActor = context.spawn(AcquirerActor.apply(cardDataActor, senderEmitterActor, receiverEmitterActor),
      "AcquirerActor")

    val requisites = Requisites(IncomingRequisites(Random.nextString(10), Random.nextString(10)), ResolvedRequisites("", ""))
    acquirerActor ! C2CRequest(requisites)

    Behaviors.receiveSignal {
      case (_, Terminated(_)) => Behaviors.stopped
    }
  }

  def main(args: Array[String]): Unit = {
    val actorSystem = ActorSystem(JetFinanceActor.apply(), "JetFinance")

    Thread.sleep(3000)
    actorSystem.terminate()
  }
}
