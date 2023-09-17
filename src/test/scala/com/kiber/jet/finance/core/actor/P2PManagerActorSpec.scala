package com.kiber.jet.finance.core.actor

import akka.actor.testkit.typed.scaladsl.ActorTestKit
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

class P2PManagerActorSpec extends AnyWordSpec with BeforeAndAfterAll with Matchers {

  val testKit = ActorTestKit()

  override protected def afterAll(): Unit = testKit.shutdownTestKit()
}
