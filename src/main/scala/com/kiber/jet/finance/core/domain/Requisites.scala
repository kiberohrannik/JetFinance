package com.kiber.jet.finance.core.domain

case class Requisites(incoming: IncomingRequisites, var resolved: ResolvedRequisites)

case class IncomingRequisites(cardData: CardData, paymentDetails: String)
case class ResolvedRequisites(var cardData: CardData, var paymentDetails: String)

case class CardData(bic: String, iban: String, number: String)