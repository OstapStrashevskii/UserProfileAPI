name := "Vtb"

version := "0.1"

scalaVersion := "2.13.2"


lazy val akkaHttpVersion = "10.2.0-M1"
lazy val akkaVersion = "2.6.4"
lazy val redisVersion = "3.20"
lazy val playJsonVersion = "2.7.4"
lazy val akkaHttpJsonVersion = "1.29.1"

val akkaHttp = "com.typesafe.akka" %% "akka-http" % akkaHttpVersion
val akkaActorTyped = "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion
val akkaStream = "com.typesafe.akka" %% "akka-stream" % akkaVersion
val playJson = "com.typesafe.play" %% "play-json" % playJsonVersion
val akkaHttpJson = "de.heikoseeberger" %% "akka-http-play-json" % akkaHttpJsonVersion
val logBack = "ch.qos.logback" % "logback-classic" % "1.1.3" % Runtime


val scalatest = "org.scalatest" %% "scalatest" % "3.1.1" % Test
val streamTestkit = "com.typesafe.akka" %% "akka-stream-testkit" % "2.6.5" % Test
val httpTestkit = "com.typesafe.akka" %% "akka-http-testkit" % "10.2.0-M1" % Test
val mockito = "org.mockito" % "mockito-all" % "1.10.19" % Test

lazy val redisDependencies = Seq("net.debasishg" %% "redisclient" % redisVersion)

lazy val akkaDependencies = Seq(
  akkaHttp,
  akkaStream,
  akkaHttpJson
)

lazy val testDependencies = Seq(
  scalatest,
  streamTestkit,
  httpTestkit,
  mockito
)

lazy val userM =
  (project in file("userM")).settings(libraryDependencies ++= redisDependencies ++ akkaDependencies ++ testDependencies :+ playJson)


lazy val iterator =
  (project in file("iterator"))