name := "keytype"
version := "0.0.2"
scalaVersion := "2.12.1"
scalaOrganization in ThisBuild := "org.typelevel"

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.3")
addCompilerPlugin("org.scalamacros" %% "paradise" % "2.1.0" cross CrossVersion.full)

resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats" % "0.9.0",
  "org.atnos" %% "eff" % "4.3.1",
  "io.github.adelbertc" %% "aiyou-core-cats" % "0.1.0-SNAPSHOT",
  "io.github.adelbertc" %% "aiyou-laws-cats" % "0.1.0-SNAPSHOT",
  "com.googlecode.lanterna" % "lanterna" % "3.0.0-rc1"
)

scalacOptions ++= Seq("-feature", "-Ypartial-unification")
updateOptions := updateOptions.value.withLatestSnapshots(false)