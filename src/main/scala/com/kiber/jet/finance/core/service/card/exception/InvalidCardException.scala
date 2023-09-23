package com.kiber.jet.finance.core.service.card.exception

class InvalidCardException extends RuntimeException {

  def this(message: String) {
    this
    println(s"Card is invalid! $message")
  }
}
