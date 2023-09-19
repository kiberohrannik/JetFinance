package com.kiber.jet.finance.core.service.card.matcher

import com.kiber.jet.finance.core.service.card.matcher.DefinedCardHolderMatchFunctions.{americanExpress, dinersClubCarteBlanche, visa}
import org.scalatest.flatspec.AnyFlatSpec

class DefinedCardHolderMatchFunctionsSpec extends AnyFlatSpec {

  "americanExpress" must "match 'American Express,34|37,15' pattern" in {
    val data = new CardHolderData("American Express", "34|37", "15")
    val card0 = "378734493671000"
    val card1 = "371449635398431"
    val card2 = "341449635398431"
    val wrongCard0 = "3414496353984341"
    val wrongCard1 = "361449635398434"

    assert(americanExpress.apply(data, card0))
    assert(americanExpress.apply(data, card1))
    assert(americanExpress.apply(data, card2))

    assert(!americanExpress.apply(data, wrongCard0))
    assert(!americanExpress.apply(data, wrongCard1))
  }

  "Visa" must "match 'Visa,4,13-16' pattern" in {
    val data = new CardHolderData("Visa", "34|37", "13-16")
    val card0 = "4012888888881881"
    val card1 = "4222222222222"
    val card2 = "4111111111111111"
    val wrongCard0 = "401288888888"
    val wrongCard1 = "3012888888881881"

    assert(visa.apply(data, card0))
    assert(visa.apply(data, card1))
    assert(visa.apply(data, card2))

    assert(!visa.apply(data, wrongCard0))
    assert(!visa.apply(data, wrongCard1))
  }

}
