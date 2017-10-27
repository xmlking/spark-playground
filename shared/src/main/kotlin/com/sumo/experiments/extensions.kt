package com.sumo.experiments

import org.apache.spark.api.java.JavaPairRDD
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Encoders.*
import org.apache.spark.sql.Row
import scala.Tuple1
import scala.Tuple2

fun <K, V> JavaPairRDD<K, V>.sortByValue(ascending: Boolean = true): JavaPairRDD<K, V> {
    return this.mapToPair { it.swap() }.sortByKey(ascending).mapToPair { it.swap() }
}

fun <K, V> JavaPairRDD<K, V>.collectAsImmutableMap() = this.collectAsMap().toMap()


fun <K> K.tuple() = Tuple1(this)
infix fun <K, V> K.tuple(value: V) = Tuple2(this, value)

inline fun <reified T : Any>  Dataset<Row>.cast(): Dataset<T> = this.`as`(bean(T::class.java))

//inline fun <reified T : Any> JavaRDD<T>.saveToMarkLogic(collection: String) {
//    markLogicUtil.save(T::class.java)
//}
