name := "agendatech"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "mysql" % "mysql-connector-java" % "5.1.29",
  "commons-io" % "commons-io" % "2.2"
)     

play.Project.playJavaSettings
