package com.moon.pattern

/**
  * Created by Paul on 2017/2/28.
  */
case class Person(fistName: String, lastName: String) {}

object PersonExample {
  def main(args: Array[String]) {
    val p1 = Person("Micheal", "Bevilaqua")
    val p2 = Person("Pedro", "Vasquez")
    val p3 = Person("Robert", "Aarons")

    val people = Vector(p3, p2, p1)

    people.sortWith((p1, p2) => p1.fistName > p2.fistName)
    print(people)
  }
}
