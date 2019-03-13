import _root_.sbt.Keys._

name := "scala"

version := "1.0"

scalaVersion := "2.12.8"

scalacOptions := Seq(
  "-encoding",
  "utf8",
  "-feature",
  "-unchecked",
  "-deprecation",
  "-target:jvm-1.8",
  "-Ypartial-unification",
  "-language:_",
  "-Xexperimental"
)

val akkaVersion       = "2.5.19"
val akkaHttpVersion   = "10.1.7"
val circeVersion      = "0.11.0"
val catsVersion       = "1.6.0"
val catsEffectVersion = "1.1.0"
val fs2Version        = "1.0.3"

libraryDependencies += "org.scalactic"  %% "scalactic"   % "3.0.5"
libraryDependencies += "org.scalatest"  %% "scalatest"   % "3.0.5" % "test"
libraryDependencies += "org.scalacheck" %% "scalacheck"  % "1.13.4" % "test"
libraryDependencies += "org.mockito"    % "mockito-core" % "1.9.5" % "test"

libraryDependencies += "com.typesafe.akka" %% "akka-actor"       % akkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-stream"      % akkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-remote"      % akkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-slf4j"       % akkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-http"        % akkaHttpVersion

libraryDependencies += "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % "test"
libraryDependencies += "com.typesafe.akka" %% "akka-testkit"      % akkaVersion     % "test"

libraryDependencies += "org.typelevel" %% "cats-core"   % catsVersion
libraryDependencies += "org.typelevel" %% "cats-effect" % catsEffectVersion
libraryDependencies += "co.fs2"        %% "fs2-core"    % fs2Version
libraryDependencies += "co.fs2"        %% "fs2-io"      % fs2Version

libraryDependencies += "io.circe" %% "circe-core"           % circeVersion
libraryDependencies += "io.circe" %% "circe-generic"        % circeVersion
libraryDependencies += "io.circe" %% "circe-parser"         % circeVersion
libraryDependencies += "io.circe" %% "circe-generic-extras" % circeVersion

libraryDependencies += scalaOrganization.value % "scala-reflect" % scalaVersion.value
libraryDependencies += scalaOrganization.value % "scala-compiler" % scalaVersion.value


libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging"  % "3.7.2"
libraryDependencies += "ch.qos.logback"             % "logback-classic" % "1.2.3"

libraryDependencies += "com.chuusai" %% "shapeless" % "2.3.3"

addCompilerPlugin("org.scalameta" % "paradise" % "3.0.0-M11" cross CrossVersion.patch)
