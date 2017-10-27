package com.sumo.experiments

import java.io.Serializable

// This annotation used to indicate *Gradle noArg plugin* to generate zero-argument constructor.
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class NoArgy

@NoArgy
data class Rating(val userId: String, val movieId: String, val rating: String): Serializable {
    companion object Factory {
        fun create(row: List<String>): Rating {
            return Rating(row[0], row[1], row[2])
        }
    }
}

@NoArgy
data class Person(var id: Int, var name: String, var age: Int, var numFriends: Int)
@NoArgy
data class Skill(var pid: Int, var skill: String, var level: Int)
