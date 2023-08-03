package com.kiber.jet.finance.core.actor

import akka.actor.typed.ActorRef
import com.kiber.jet.finance.core.actor.CardDataActor.CardDataMessage

class ActorHolder {
  private var cardDataActor: ActorRef[CardDataMessage] = null
}
