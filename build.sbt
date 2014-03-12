name := "agendatech"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "mysql" % "mysql-connector-java" % "5.1.29",
  "commons-io" % "commons-io" % "2.2",
  "javax.mail" % "mail" % "1.4.1",
  "com.typesafe"  %% "play-plugins-mailer"  % "2.1-RC2"
)     

play.Project.playJavaSettings
