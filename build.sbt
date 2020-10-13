name := "urlshortener"

version := "0.1"

scalaVersion := "2.12.12"

crossScalaVersions := Seq("2.12.6", "2.11.12")

lazy val root = (project in file(".")).enablePlugins(PlayScala, BuildInfoPlugin)
  .settings(
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "com.crp"
  )

resolvers := Seq(
  Resolver.mavenLocal,
  Resolver.typesafeRepo("releases"),
  Resolver.url("Local Ivy Repository", url("file://" + Path.userHome.absolutePath+"/.ivy2/local"))(Resolver.ivyStylePatterns),
  "jaadec" at "http://repo.spring.io/libs-release/",
  "confluent" at "https://packages.confluent.io/maven/"
)

libraryDependencies += guice
libraryDependencies += ws
libraryDependencies += ehcache
libraryDependencies ++= Seq(
  "org.jsoup" % "jsoup" % "1.11.3" ,
  "org.mindrot" % "jbcrypt" % "0.4",
  "org.reactivemongo" %% "reactivemongo" % "0.17.1",
  "commons-codec" % "commons-codec" % "1.12"
)

libraryDependencies ++= testDependencies

lazy val testDependencies = Seq(
  "org.scalatest"         %% "scalatest"           % "3.0.5"     % Test,
  "org.scalacheck"        %% "scalacheck"          % "1.14.0"    % Test,
  "org.mockito"            % "mockito-core"        % "2.23.4"    % Test,
  "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.1" % Test
)

packageName in Docker := packageName.value

version in Docker := version.value

dockerBaseImage := "adoptopenjdk/openjdk8:jdk8u212-b04"

dockerExposedPorts := Seq(9000)

javaOptions in Universal ++= Seq(
  "-Dpidfile.path=/dev/null"
)