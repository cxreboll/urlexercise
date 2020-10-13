addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.7.0")

addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "1.0.0")

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.1")

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.3.23")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.9.2")

addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.9.0")

resolvers += "sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
