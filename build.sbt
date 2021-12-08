ThisBuild / scalaVersion := "2.13.7"
ThisBuild / organization := "com.innerproduct"
ThisBuild / version := "0.0.1-SNAPSHOT"
ThisBuild / fork := true
ThisBuild / turbo := true                  // default: false
ThisBuild / includePluginResolvers := true // default: false
Global / onChangedBuildSource := ReloadOnSourceChanges

val CatsEffectVersion = "2.5.4"
val CatsTaglessVersion = "0.14.0"
val CirceVersion = "0.14.1"
val Http4sVersion = "0.22.8"
val LogbackVersion = "1.2.7"
val MunitVersion = "0.7.29"

val commonSettings =
  Seq(
    addCompilerPlugin(
      "org.typelevel" %% "kind-projector" % "0.13.2" cross CrossVersion.full
    ),
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % MunitVersion % Test
    ),
    // scalacOptions provided by sbt-tpolecat plugin
  )

lazy val util = (project in file("util"))
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-effect" % CatsEffectVersion,
      "org.typelevel" %% "cats-effect-laws" % CatsEffectVersion % Test
    )
  )

lazy val exercises = (project in file("exercises"))
  .dependsOn(util)
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-effect" % CatsEffectVersion,
      "org.typelevel" %% "cats-effect-laws" % CatsEffectVersion % Test
    ),
    // remove fatal warnings since exercises have unused and dead code blocks
    scalacOptions ++= Seq( "-Wdead-code:false" ),
    scalacOptions --= Seq( "-Xfatal-warnings" )
  )

lazy val solutions = (project in file("solutions"))
  .dependsOn(util)
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-effect" % CatsEffectVersion,
      "org.typelevel" %% "cats-effect-laws" % CatsEffectVersion % Test
    )
  )

lazy val petstore = (project in file("case-studies") / "petstore")
  .dependsOn(solutions % "test->test;compile->compile")
  .settings(commonSettings)
  .settings(
    // -Ymacro-annotations in 2.13.2 breaks -Wunused-imports, so downgrade for petstore (https://github.com/scala/bug/issues/11978)
    scalaVersion := "2.13.7",
    scalacOptions += "-Ymacro-annotations", // required by cats-tagless-macros
    libraryDependencies ++= Seq(
      "ch.qos.logback" % "logback-classic" % LogbackVersion,
      "io.circe" %% "circe-generic" % CirceVersion,
      "org.http4s" %% "http4s-blaze-server" % Http4sVersion,
      "org.http4s" %% "http4s-blaze-client" % Http4sVersion,
      "org.http4s" %% "http4s-circe" % Http4sVersion,
      "org.http4s" %% "http4s-dsl" % Http4sVersion,
      "org.typelevel" %% "cats-tagless-macros" % CatsTaglessVersion,
      "org.scalameta" %% "munit-scalacheck" % MunitVersion % Test
    )
  )
