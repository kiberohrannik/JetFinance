package com.kiber.jet.finance.core.service

import com.kiber.jet.finance.core.domain.{CardData, Requisites}

trait CardVerifier {

  def verify(cardData: CardData): Boolean
}
