package com.kiber.jet.finance.core.service.card

class InMemoryCardHolderDataRepository extends CardHolderDataRepository {

  //TODO create parser of this data
  override def getAllData(): Seq[(String, String, String)] = {
    List(
      ("American Express", "34|37", "15"),
      ("Diners Club Carte Blanche", "300-305", "14"),
      ("Diners Club International", "36", "14"),
      ("Diners Club US and Canada", "54|55", "16"),
//      ("Discover Card", "6011|622126-622925|644-649|65", "16"),
      ("InstaPayment", "637-639", "16"),
      ("JCB", "3528-3589", "16"),
//      ("Laser", "6304|6706|6771|6709", "16-19"),
//      ("Maestro", "5018|5020|5038|6304|6759|6761|6762|6763", "12-19"),
      ("Mastercard", "51-55", "16"),
      ("Visa", "4", "13-16"),
//      ("Visa Electron", "4026|417500|4508|4844|4913|4917", "16")
    )
  }

  override def getMatchFunctions(): Map[String, (String) => Boolean] = {
    Map(
      ("American Express", cardNumber => cardNumber.matches("(34\\d{13})|(37\\d{13})")),
      ("Diners Club Carte Blanche", n => n.matches("\\d{14}") && prefixInRange(n, 3, 300, 305)),
      ("Diners Club International", n => n.matches("36\\d{12}")),
      ("Diners Club US and Canada", n => n.matches("(54\\d{14})|(55\\d{14})")),

      ("InstaPayment", n => n.matches("\\d{16}") && prefixInRange(n, 3, 637, 639)),
      ("JCB", n => n.matches("\\d{16}") && prefixInRange(n, 4, 3528, 3589)),

      ("Mastercard", n => n.matches("\\d{16}") && prefixInRange(n, 2, 51, 55)),
      ("Visa", n => n.matches("\\d*") && n.startsWith("4") && inRange(n.length, 13, 16)),

    )
  }

  private def prefixInRange(value: String, prefixLength: Int, min: Int, max: Int) : Boolean = {
    inRange(value.substring(0, prefixLength - 1).toInt, min, max)
  }

  private def inRange(value: Int, min: Int, max: Int): Boolean = {
    value >= min && value <= max
  }
}