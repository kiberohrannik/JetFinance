lazy val root = (project in file("."))
  .settings(
    name := "JetFinance"
  )

val AkkaVersion = "2.8.2"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream-typed" % AkkaVersion,

  "com.typesafe.akka" %% "akka-actor-testkit-typed" % AkkaVersion % Test,
  //  "ch.qos.logback" % "logback-classic" % "1.4.5" % Runtime
)