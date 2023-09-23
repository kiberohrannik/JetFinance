package com.kiber.jet.finance.core.service.card

import com.kiber.jet.finance.core.domain.CardData

class NoneCardVerifier extends CardVerifier {

  override def verify(cardData: CardData): Unit = {
    println(s"Mocked sender's card data verification... CardData: ${cardData}")
  }
}
