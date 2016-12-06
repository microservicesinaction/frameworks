val http4sVersion = "0.13.0"

val circeVersion = "0.1.2"

organization := "underscore"

name := "http4s-example"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.5"

resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"

libraryDependencies ++= Seq(
    "org.http4s" %% "http4s-blaze-server" % http4sVersion,
    "org.http4s" %% "http4s-core" % http4sVersion,
    "org.http4s" %% "http4s-server" % http4sVersion,
    "org.http4s" %% "http4s-dsl" % http4sVersion,
    "org.http4s" %% "http4s-argonaut" % http4sVersion,
    "io.circe" %% "circe-core" % circeVersion,
  	"io.circe" %% "circe-generic" % circeVersion,
  	"io.circe" %% "circe-jawn" % circeVersion,
  	"com.chuusai" %% "shapeless" % "2.2.1",
    "io.argonaut" %% "argonaut" % "6.0.4",
    "org.scalaz.stream" %% "scalaz-stream" % "0.6a"
  )
