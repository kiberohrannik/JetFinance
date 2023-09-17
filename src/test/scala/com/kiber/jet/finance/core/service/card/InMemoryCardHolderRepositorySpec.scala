package com.kiber.jet.finance.core.service.card

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.tagobjects.Retryable

class InMemoryCardHolderRepositorySpec extends AnyFlatSpec {

  val cardHolderDataRepository = new InMemoryCardHolderDataRepository()


  "getMatchFunctions" must "contain functions for main card holders" taggedAs (TrainingTest) in {
    val result = cardHolderDataRepository.getMatchFunctions()
    assert(result.contains("American Express"))
    assert(result.contains("Diners Club Carte Blanche"))
    assert(result.contains("Diners Club International"))
    assert(result.contains("Diners Club US and Canada"))
    assert(result.contains("InstaPayment"))
    assert(result.contains("JCB"))
    assert(result.contains("Mastercard"))
    assert(result.contains("Visa"))
  }
}
