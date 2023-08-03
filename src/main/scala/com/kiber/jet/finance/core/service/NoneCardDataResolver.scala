package com.kiber.jet.finance.core.service
import com.kiber.jet.finance.core.domain.CardData

class NoneCardDataResolver extends CardDataResolver {

  override def resolve(cardData: CardData): Unit = {
    println(s"Mocked card data resolution... CardData: ${cardData}")
  }
}
