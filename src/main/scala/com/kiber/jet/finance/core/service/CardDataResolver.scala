package com.kiber.jet.finance.core.service

import com.kiber.jet.finance.core.domain.CardData

trait CardDataResolver {

  def resolve(cardData: CardData): Unit
}
