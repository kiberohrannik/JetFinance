package com.kiber.jet.finance.core.service.card.matcher

case class CardHolderData(holder: String, prefixPattern: String, length: String)

trait CardHolderMatchFunction extends (String => Boolean) {

}
