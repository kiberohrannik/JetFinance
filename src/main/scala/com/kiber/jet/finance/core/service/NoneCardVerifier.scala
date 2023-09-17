package com.kiber.jet.finance.core.service
import com.kiber.jet.finance.core.domain.CardData

class NoneCardVerifier extends CardVerifier {

  override def verify(cardData: CardData): Boolean = {
    println(s"Mocked sender's card data resolution... CardData: ${cardData}")
    true
  }
}
