package com.kiber.jet.finance.core.service

import com.kiber.jet.finance.core.domain.{CardData, Requisites}

trait CardVerifier {

  /**
   * It is important to throw
   * @param cardData
   */
  def verify(cardData: CardData): Unit
}
