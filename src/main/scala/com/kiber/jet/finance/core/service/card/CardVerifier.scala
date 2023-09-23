package com.kiber.jet.finance.core.service.card

import com.kiber.jet.finance.core.domain.CardData

trait CardVerifier {

  /**
   * It is important to throw
   * @param cardData
   */
  def verify(cardData: CardData): Unit
}
