package com.kiber.jet.finance.core.service
import com.kiber.jet.finance.core.domain.CardData
import com.kiber.jet.finance.core.service.card.exception.InvalidCardException
import com.kiber.jet.finance.core.service.card.matcher.DefinedCardHolderMatchFunctions

class HolderPatternCardVerifier extends CardVerifier {

  override def verify(cardData: CardData): Unit = {
    val isVerified = DefinedCardHolderMatchFunctions.getAll
      .map(_ apply cardData.number)
      .fold(false)((res1, res2) => res1 || res2)

    if(!isVerified) {
      throw new InvalidCardException(s"Verification has failed for card: " +
        s"${cardData.number} using ${this.getClass.getSimpleName} verifier")
    }
  }

}
