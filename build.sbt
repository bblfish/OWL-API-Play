val scala213Version = "2.13.2"

//for *.sc scripts
libraryDependencies += "com.lihaoyi" %% "ammonite-ops" % "2.1.4"


lazy val root = project
  .in(file("."))
  .settings(
    name := "OWL API Play",
    version := "0.1.0-SNAPSHOT",

    libraryDependencies ++= Seq(
      // see https://github.com/phenoscape/scowl
      "org.phenoscape" %% "scowl" % "1.3.4",
      // see https://github.com/owlcs/owlapi/wiki/All-Releases
      // latest version is 5.1.9 but scowl does not support 5.x (yet()
      "net.sourceforge.owlapi" % "owlapi-distribution" % "4.5.16",// withSources() withJavaDoc(),
      //     "net.sourceforge.owlapi" % "org.semanticweb.hermit" % "1.4.5.519",
      "com.novocode" % "junit-interface" % "0.11" % "test",
      //     https://github.com/scala/scala-collection-contrib
      "org.scala-lang.modules" %% "scala-collection-contrib" % "0.2.1"
    ),

    // To make the default compiler and REPL use Dotty
    scalaVersion := scala213Version,

  )