name := "EC3Evaluator"

version := "1.0"

scalaVersion := "2.10.3"

mainClass in Compile := Some("EC3EvaluatorPartII")

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "com.typesafe.akka" % "akka-actor_2.10" % "2.2-M1"

libraryDependencies += "com.typesafe.akka" % "akka-actor" % "2.0"
 
libraryDependencies += "com.typesafe.akka" % "akka-remote" % "2.0"
