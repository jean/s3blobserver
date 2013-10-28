import sbt._
import sbt.Keys._
import xerial.sbt.Pack._

object Build extends sbt.Build {

  lazy val root = Project(
    id = "s3blobserver",
    base = file("."),
    settings = Defaults.defaultSettings ++
      Seq(
        name := "s3blobserver",
        resolvers += "spray repo" at "http://repo.spray.io",
        libraryDependencies ++= Seq(
          "io.spray" % "spray-caching" % "1.2-M8",
          "io.spray" % "spray-can" % "1.2-M8",
          "io.spray" % "spray-routing" % "1.2-M8",
          "io.spray" % "spray-testkit" % "1.2-M8" % "test",
          "io.spray" % "spray-client" % "1.2-M8" % "test",
          "com.typesafe.akka" %% "akka-actor" % "2.2.0-RC1",
          "com.typesafe.akka" %% "akka-slf4j" % "2.2.0-RC1",
          "com.typesafe.akka" %% "akka-testkit" % "2.2.0-RC1" % "test",
          "org.clapper" % "grizzled-scala_2.10" % "1.1.4",
          "com.amazonaws" % "aws-java-sdk" % "1.4.7",
          "org.scalatest" %% "scalatest" % "1.9.1" % "test",
          "org.mockito" % "mockito-core" % "1.9.5" % "test",
          "org.apache.zookeeper" % "zookeeper" % "3.4.5"
            exclude("com.sun.jdmk", "jmxtools")
            exclude("com.sun.jmx", "jmxri")
            exclude("javax.jms", "jms"),
          "com.escalatesoft.subcut" %% "subcut" % "2.0"          ),
        scalacOptions ++= Seq("-deprecation", "-feature"),
        parallelExecution in Test := false,
        scalaVersion := "2.10.2"
      ) ++
      packSettings ++
      Seq(
        // Specify mappings from program name -> Main class (full package path)
        packMain := Map("server" -> "com.zope.s3blobserver.Main"),
        // Add custom settings here
        // [Optional] JVM options of scripts (program name -> Seq(JVM option, ...))
        packJvmOpts := Map("server" -> Seq("-server", "-Xincgc"))
        // [Optional] Extra class paths to look when launching a program
        //packExtraClasspath := Map("hello" -> Seq("${PROG_HOME}/etc"))
        )
  )
}
