package com.sumo.experiments

import org.apache.spark.sql.SaveMode.*
import org.apache.spark.sql.functions.*
import org.apache.spark.sql.SparkSession

fun main(args: Array<String>) {


    val spark = with(SparkSession.builder()) {
        appName("Java Spark SQL basic example")
        config("mapreduce.fileoutputcommitter.marksuccessfuljobs", "false")
        config("parquet.enable.summary-metadata", "false")
        getOrCreate()
    }

    spark.sparkContext().setLogLevel("ERROR")

    val input = spark.sparkContext().conf["spark.myapp.input"]
    val output = spark.sparkContext().conf["spark.myapp.output"]

    val people = with(spark.read()) {
        option("header", "true")
        // option("inferSchema", "true")
        schema(Schema.peopleSchema)
        option("nullValue", "null")
        option("mode", "DROPMALFORMED")
        csv("${input}/people.csv")
    }.cast<Person>()

    people.createOrReplaceTempView("people")

    val skills = with(spark.read()) {
        option("header", "true")
        // option("inferSchema", "true")
        schema(Schema.skillSchema)
        option("nullValue", "null")
        option("mode", "DROPMALFORMED")
        csv("${input}/skills.csv")
    }.cast<Skill>()
    skills.createOrReplaceTempView("skills")

//    val result = spark.sql("SELECT age, count(*) as cnt  FROM people WHERE age >= 13 AND age <= 19 group by age")
//    val result = spark.sql("SELECT id, name, numFriends as friends, age  FROM people WHERE age >= 13 AND age <= 19")
    val result = spark.sql("""SELECT people.*, collect_list(struct(skills.*)) AS skills
                                                    FROM people LEFT JOIN skills ON  people.id =  skills.pid
                                                    GROUP BY id, name, age, numFriends
                                                    ORDER BY id""")

    println("--------------")
    println(result.printSchema())
    result.show(5, false)

    result.coalesce(1)
        .write()
        .mode(Overwrite)
        .json("${output}/people.json")
    println("--------------")
}
