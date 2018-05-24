package com.sumo.experiments.scala

import org.apache.spark.sql.SparkSession

object LoadJob extends App {
    //  println("Hello, " + args(0))

    val spark = SparkSession.builder()
          .appName("People-Skills Join Job")
          .config("mapreduce.fileoutputcommitter.marksuccessfuljobs", "false")
          .config("parquet.enable.summary-metadata", "false")
          .getOrCreate()

    import spark.implicits._

    val sc = spark.sparkContext

    sc.setLogLevel("ERROR")

    val input = sc.getConf.get("spark.myapp.input")
    val output = sc.getConf.get("spark.myapp.output")

    val people = spark.read
        .option("header","true")
        // .option("inferSchema", "true")
        .schema(Schema.peopleSchema)
        .option("nullValue", "null")
        .option("mode", "DROPMALFORMED")
        .csv(s"$input/people.csv").as[Person]

    people.createOrReplaceTempView("people")

    val skills = spark.read
        .option("header","true")
        // .option("inferSchema", "true")
        .schema(Schema.skillSchema)
        .option("nullValue", "null")
        .option("mode", "DROPMALFORMED")
        .csv(s"$input/skills.csv").as[Skill]

     skills.createOrReplaceTempView("skills")

    val result = people.as("L").joinWith(skills.as("R"), $"L.id" === $"R.pid",  "left_outer" )

    val scalaGuys = result
        .filter( p => p._1.age > 26 && p._2!=null && p._2.skill.contentEquals("scala") )
        .map(p => (p._1.name, p._1.age, p._1.numFriends))


    println("--------------")
    result.explain
    println(s"partitions: ${result.rdd.partitions.length}")
    println(result.printSchema)
    result.show(20, truncate = false)
    println(result.count)
    scalaGuys.show(20, truncate = false)
    println("--------------")
}
