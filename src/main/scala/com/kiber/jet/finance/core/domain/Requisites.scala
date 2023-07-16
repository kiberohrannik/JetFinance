package com.kiber.jet.finance.core.domain

case class Requisites(incoming: IncomingRequisites, var resolved: ResolvedRequisites)

case class IncomingRequisites(cardData: String, paymentDetails: String)
case class ResolvedRequisites(var cardData: String, var paymentDetails: String)