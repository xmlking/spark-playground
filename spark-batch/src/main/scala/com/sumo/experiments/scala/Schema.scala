package com.sumo.experiments.scala

import org.apache.spark.sql.types._

object Schema {
    val peopleSchema: StructType = new StructType().add("id", "integer", nullable = false).add("name", "string").add("age", "integer").add("numFriends", "integer")
    val skillSchema: StructType = new StructType().add("pid", "integer", nullable = false).add("skill", "string").add("level", "integer")
}
