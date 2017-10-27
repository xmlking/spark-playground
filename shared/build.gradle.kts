import org.gradle.api.tasks.bundling.Jar
val sparkVersion by project

application {
    mainClassName = "dummy"
}

val jar: Jar by tasks
jar.enabled = true

dependencies {
    compile("org.apache.spark:spark-sql_2.11:$sparkVersion")
}
