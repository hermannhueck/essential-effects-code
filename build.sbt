ThisBuild / scalaVersion           := "2.13.8"
ThisBuild / organization           := "com.innerproduct"
ThisBuild / version                := "0.0.1-SNAPSHOT"
ThisBuild / fork                   := true
ThisBuild / turbo                  := true // default: false
ThisBuild / includePluginResolvers := true // default: false
Global / onChangedBuildSource      := ReloadOnSourceChanges

val CE2Version         = "2.5.4"
val CE3Version         = "3.3.4"
val CatsTaglessVersion = "0.14.0"
val CirceVersion       = "0.14.1"
val Http4sVersion      = "0.22.8"
val LogbackVersion     = "1.2.10"
val MunitVersion       = "0.7.29"
val MunitCe3Version    = "1.0.7"

val commonSettings =
  Seq(
    addCompilerPlugin(
      "org.typelevel" %% "kind-projector" % "0.13.2" cross CrossVersion.full
    ),
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % MunitVersion % Test
    )
    // scalacOptions provided by sbt-tpolecat plugin
  )

lazy val ce3examples = (project in file("ce3/examples"))
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-effect"         % CE3Version,
      "org.typelevel" %% "cats-effect-testkit" % CE3Version      % Test,
      "org.typelevel" %% "munit-cats-effect-3" % MunitCe3Version % Test
    )
  )

lazy val ce2examples = (project in file("ce2/examples"))
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-effect"      % CE2Version,
      "org.typelevel" %% "cats-effect-laws" % CE2Version % Test
    )
  )

lazy val jobscheduler = (project in file("ce2/case-studies") / "jobscheduler")
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-effect"      % CE2Version,
      "org.typelevel" %% "cats-effect-laws" % CE2Version % Test
    )
  )

lazy val ce2petstore = (project in file("ce2/case-studies") / "petstore")
  .dependsOn(ce2examples % "test->test;compile->compile")
  .settings(commonSettings)
  .settings(
    // -Ymacro-annotations in 2.13.2 breaks -Wunused-imports, so downgrade for petstore (https://github.com/scala/bug/issues/11978)
    scalacOptions += "-Ymacro-annotations", // required by cats-tagless-macros
    libraryDependencies ++= Seq(
      "ch.qos.logback" % "logback-classic"     % LogbackVersion,
      "io.circe"      %% "circe-generic"       % CirceVersion,
      "org.http4s"    %% "http4s-blaze-server" % Http4sVersion,
      "org.http4s"    %% "http4s-blaze-client" % Http4sVersion,
      "org.http4s"    %% "http4s-circe"        % Http4sVersion,
      "org.http4s"    %% "http4s-dsl"          % Http4sVersion,
      "org.typelevel" %% "cats-tagless-macros" % CatsTaglessVersion,
      "org.scalameta" %% "munit-scalacheck"    % MunitVersion % Test
    )
  )
