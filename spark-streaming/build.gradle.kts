val sparkVersion: String by project.extra

application {
    mainClassName = "com.sumo.experiments.StreamingJobKt"
}

dependencies {
    compile(project(":shared"))

    compile("org.apache.spark:spark-streaming_2.11:$sparkVersion")
}
