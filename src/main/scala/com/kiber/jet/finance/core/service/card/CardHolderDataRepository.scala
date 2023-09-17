package com.kiber.jet.finance.core.service.card

trait CardHolderDataRepository {

  def getAllData() : Seq[(String, String, String)]

  def getMatchFunctions() : Map[String, (String) => Boolean]
}
