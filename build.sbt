import com.typesafe.tools.mima.core.Problem
import laika.helium.Helium

ThisBuild / tlBaseVersion := "0.6" // your current series x.y

ThisBuild / organization := "io.chrisdavenport"
ThisBuild / organizationName := "Christopher Davenport"
ThisBuild / licenses := Seq(License.MIT)
ThisBuild / developers := List(
  // your GitHub handle and name
  tlGitHubDev("christopherdavenport", "Christopher Davenport")
)

ThisBuild / tlCiReleaseBranches := Seq("main")

// true by default, set to false to publish to s01.oss.sonatype.org
ThisBuild / tlSonatypeUseLegacyHost := true
ThisBuild / githubWorkflowSbtCommand := "./sbt"

ThisBuild / crossScalaVersions := Seq("2.12.18", "2.13.12", "3.3.0")

val catsV = "2.7.0"
val catsEffectV = "3.5.0"
val epimetheusV = "0.6.0"
val circuitV = "0.5.1"

val specs2V = "4.15.0"

lazy val `epimetheus-circuit` = tlCrossRootProject
  .aggregate(core)

lazy val core = project.in(file("core"))
  .settings(
    mimaBinaryIssueFilters := List({(_: Problem) => false}), // TODO: remove this once switched to next major version
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
  .enablePlugins(TypelevelSitePlugin)
  .dependsOn(core)
  .settings(
    laikaTheme := Helium.defaults.site.landingPage().build
  )
