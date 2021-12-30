import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

ThisBuild / crossScalaVersions := Seq("2.12.14", "2.13.6", "3.1.0")

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

      ("org.specs2"                  %% "specs2-core"                % specs2V       % Test).cross(CrossVersion.for3Use2_13),
      ("org.specs2"                  %% "specs2-scalacheck"          % specs2V       % Test).cross(CrossVersion.for3Use2_13)
    )
  )

lazy val site = project.in(file("site"))
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
val catsEffectV = "3.3.2"
val epimetheusV = "0.5.0-M2"
val circuitV = "0.5.0-M2"

val specs2V = "4.12.12"