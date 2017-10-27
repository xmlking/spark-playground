package com.sumo.experiments.scala

case class Person(var id: Int, var name: String, var age: Int, var numFriends: Int)

case class Skill(var pid: Option[Int], var skill: String, var level: Option[Int])
