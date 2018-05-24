val sparkVersion: String by project.extra

application {
    mainClassName = "com.sumo.experiments.BatchJobKt"
}

dependencies {
    compile(project(":shared"))

    //compile("org.apache.spark:spark-sql_2.11:$sparkVersion")
}
