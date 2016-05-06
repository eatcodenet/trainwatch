name := """tw-webapp"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  /*jdbc,*/
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "net.eatcode.trainwatch" % "tw-search-api" % "1.0-SNAPSHOT"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
resolvers += "confluent-io" at "http://packages.confluent.io/maven/"
resolvers += Resolver.mavenLocal

routesGenerator := InjectedRoutesGenerator
