package com.kiber.jet.finance

import akka.NotUsed
import akka.actor.typed.scaladsl.{Behaviors, PoolRouter, Routers}
import akka.actor.typed.{ActorSystem, Behavior, SupervisorStrategy, Terminated}
import com.kiber.jet.finance.core.actor.CardDataActor.CardDataMessage
import com.kiber.jet.finance.core.actor.P2PManagerActor.StartP2P
import com.kiber.jet.finance.core.actor.ReceiverEmitterActor.ReceiverEmitterMessage
import com.kiber.jet.finance.core.actor.SenderEmitterActor.SenderEmitterMessage
import com.kiber.jet.finance.core.actor.{CardDataActor, P2PManagerActor, ReceiverEmitterActor, SenderEmitterActor}
import com.kiber.jet.finance.core.domain.{CardData, IncomingRequisites, Requisites, ResolvedRequisites}
import com.kiber.jet.finance.core.service.card.{HolderPatternCardVerifier, LuhnAlgCardVerifier, NoneCardVerifier}

import scala.util.Random

object JetFinanceActor {

  sealed trait JetFinanceMessage

  def apply(): Behavior[NotUsed] = Behaviors.setup { context =>
//    val cardDataActor = context.spawn(CardDataActor.apply(new NoneCardDataResolver()), "CardDataActor")
    val cardDataActor = context.spawn(createCardDataActorPool(2), "CardDataActor")
    val senderEmitterActor = context.spawn(createSenderEmitterActorPool(2), "SenderEmitterActor")
    val receiverEmitterActor = context.spawn(createReceiverEmitterActorPool(2), "ReceiverEmitterActor")

    val acquirerActor = context.spawn(P2PManagerActor.apply(cardDataActor, senderEmitterActor, receiverEmitterActor),
      "AcquirerActor")

    val cardData = CardData(Random.nextString(10), Random.nextString(10), Random.nextString(10))
    val requisites = Requisites(IncomingRequisites(cardData, Random.nextString(10)), ResolvedRequisites(cardData, ""))

    acquirerActor ! StartP2P(requisites)
//    acquirerActor ! StartP2P(requisites)
//    acquirerActor ! StartP2P(requisites)
//    acquirerActor ! StartP2P(requisites)
//    acquirerActor ! StartP2P(requisites)


    Behaviors.receiveSignal {
      case (_, Terminated(_)) => Behaviors.stopped
    }
  }

  def main(args: Array[String]): Unit = {
    val actorSystem = ActorSystem(JetFinanceActor.apply(), "JetFinance")

    Thread.sleep(3000)
    actorSystem.terminate()
  }

  private def createCardDataActorPool(poolSize: Int): PoolRouter[CardDataMessage] = {
    val pool = Routers.pool(poolSize) {
      // make sure the workers are restarted if they fail

      val cardVerifiers = new LuhnAlgCardVerifier::new HolderPatternCardVerifier::new NoneCardVerifier::Nil
      val cardDataActor = CardDataActor(cardVerifiers)

      Behaviors.supervise(cardDataActor)
        .onFailure[Exception](SupervisorStrategy.restart)
    }

    pool.withRandomRouting()
//    pool.withRoundRobinRouting()
  }

  private def createSenderEmitterActorPool(poolSize: Int): PoolRouter[SenderEmitterMessage] = {
    Routers.pool(poolSize) (SenderEmitterActor())
  }

  private def createReceiverEmitterActorPool(poolSize: Int): PoolRouter[ReceiverEmitterMessage] = {
    Routers.pool(poolSize) {
      Behaviors.supervise(ReceiverEmitterActor()).onFailure[Exception](SupervisorStrategy.restart)
    }
  }
}
