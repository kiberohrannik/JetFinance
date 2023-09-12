package com.kiber.jet.finance.core.service

import com.kiber.jet.finance.core.domain.{CardData, Requisites}

trait CardResolver {

  def resolve(cardData: CardData): Boolean
}
