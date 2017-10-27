package com.sumo.experiments

import org.apache.spark.sql.types.*

object Schema {
    val peopleSchema = StructType().add("id", "integer", false).add("name", "string").add("age", "integer").add("numFriends", "integer")
    val skillSchema = StructType().add("pid", "integer", false).add("skill", "string").add("level", "integer")
}
