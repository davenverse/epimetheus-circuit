import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

ThisBuild / crossScalaVersions := Seq("2.12.14", "2.13.6")

lazy val `epimetheus-circuit` = project.in(file("."))
  .disablePlugins(MimaPlugin)
  .enablePlugins(NoPublishPlugin)
  .aggregate(core)

lazy val core = project.in(file("core"))
  .settings(
    name := "epimetheus-circuit",
    libraryDependencies ++= Seq(
      "org.typelevel"               %% "cats-core"                  % catsV,
      "org.typelevel"               %% "cats-effect"                % catsEffectV,
      "io.chrisdavenport"           %% "epimetheus"                 % epimetheusV,
      "io.chrisdavenport"           %% "circuit"                    % circuitV,

      "org.specs2"                  %% "specs2-core"                % specs2V       % Test,
      "org.specs2"                  %% "specs2-scalacheck"          % specs2V       % Test
    )
  )

lazy val docs = project.in(file("site"))
  .disablePlugins(MimaPlugin)
  .enablePlugins(NoPublishPlugin)
  .enablePlugins(DavenverseMicrositePlugin)
  .dependsOn(core)
  .settings{
    import microsites._
    Seq(
      micrositeName := "epimetheus-circuit",
      micrositeDescription := "Epimetheus Metrics for Circuit",
      micrositeAuthor := "Christopher Davenport",
    )
  }

val catsV = "2.6.1"
val catsEffectV = "2.5.1"
val epimetheusV = "0.4.0"
val circuitV = "0.4.4"

val specs2V = "4.12.3"