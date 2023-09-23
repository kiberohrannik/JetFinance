package com.kiber.jet.finance.core.service.card.matcher

object DefinedCardHolderMatchFunctions {

  /*
  TODO it is inefficient to apply disjunction
       to all these functions cause some of them are exclusive.
       Advice: to combine functions in one simplified equation.
   */

  def getAll: Seq[CardHolderMatchFunction] = {
    List(
      americanExpress,
      dinersClubCarteBlanche, dinersClubCarteBlanche, dinersClubInternational, dinersClubUSAndCanada,
      discoverCard,
      instaPayment,
      jcb,
      laser,
      maestro,
      mastercard,
      visa, visaElectron
    )
  }

  val americanExpress: CardHolderMatchFunction = (cardNumber) =>
    cardNumber.matches(s"(34\\d{13})|(37\\d{13})")

  val dinersClubCarteBlanche: CardHolderMatchFunction = (cardNumber) =>
    cardNumber.matches("\\d{14}") && prefixInRange(cardNumber, 3, 300, 305)

  val dinersClubInternational: CardHolderMatchFunction = (cardNumber) => cardNumber.matches("36\\d{12}")

  val dinersClubUSAndCanada: CardHolderMatchFunction = (cardNumber) =>
    cardNumber.matches("(54\\d{14})|(55\\d{14})")

  val discoverCard: CardHolderMatchFunction = (cardNumber) => {
    def startMatch: Boolean = cardNumber.startsWith("6011") || cardNumber.startsWith("65")
    def rangeMatch: Boolean = prefixInRange(cardNumber, 6, 622126, 622925) || prefixInRange(cardNumber, 3, 644, 649)
    cardNumber.matches("\\d{16}") && (startMatch || rangeMatch)
  }

  val instaPayment: CardHolderMatchFunction = (cardNumber) =>
    cardNumber.matches("\\d{16}") && prefixInRange(cardNumber, 3, 637, 639)

  val jcb: CardHolderMatchFunction = (cardNumber) =>
    cardNumber.matches("\\d{16}") && prefixInRange(cardNumber, 4, 3528, 3589)

  val laser: CardHolderMatchFunction = (cardNumber) => {
    val prefixes = Set("6304", "6706", "6771", "6709")
    (cardNumber.matches("\\d*")
      && inRange(cardNumber.length, 16, 19)
      && prefixes.exists(prefix => cardNumber.startsWith(prefix)))
  }

  val maestro: CardHolderMatchFunction = (cardNumber) => {
    val prefixes = Set("5018", "5020", "5038", "6304", "6759", "6761", "6762", "6763")
    (cardNumber.matches("\\d*")
      && inRange(cardNumber.length, 12, 19)
      && prefixes.exists(prefix => cardNumber.startsWith(prefix)))
  }

  val mastercard: CardHolderMatchFunction = (cardNumber) =>
    cardNumber.matches("\\d{16}") && prefixInRange(cardNumber, 2, 51, 55)

  val visa: CardHolderMatchFunction = (cardNumber) =>
    cardNumber.matches("\\d*") && cardNumber.startsWith("4") && inRange(cardNumber.length, 13, 16)

  val visaElectron: CardHolderMatchFunction = (cardNumber) => {
    val prefixes = Set("4026", "417500", "4508", "4844", "4913", "4917");
    cardNumber.matches("\\d{16}") && prefixes.exists(prefix => cardNumber.startsWith(prefix))
  }


  private def prefixInRange(value: String, prefixLength: Int, min: Int, max: Int): Boolean = {
    inRange(value.substring(0, prefixLength - 1).toInt, min, max)
  }

  private def inRange(value: Int, min: Int, max: Int): Boolean = {
    value >= min && value <= max
  }
}
