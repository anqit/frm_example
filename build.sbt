ThisBuild / organization := "com.ankit"
ThisBuild / version := "0.1"
ThisBuild / scalaVersion := "2.13.3"

lazy val frm = (project in file("."))
    .settings(
        name := "frm",
        libraryDependencies += "com.typesafe" % "config" % "1.4.1",
        libraryDependencies += "org.postgresql" % "postgresql" % "42.2.13",
        // slick
        libraryDependencies ++= Seq(
            "com.typesafe.slick" %% "slick" % "3.3.3",
            "org.slf4j" % "slf4j-nop" % "1.6.4",
            "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3"
        )
    )