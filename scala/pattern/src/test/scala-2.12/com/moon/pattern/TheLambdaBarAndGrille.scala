package com.moon.pattern

/**
  * Created by Paul on 2017/3/6.
  */
case class Person(name: String, address: Address)

case class Address(zip: Int)

object TheLambdaBarAndGrille {
  def isCloseZip(zipCode: Int) = zipCode == 19123 || zipCode == 192103

  def generateGreeting(people: Seq[Person]) =
    for (Person(name, address) <- people if isCloseZip(address.zip))
      yield "hello,%s and welcome to the Lambda Bar And G".format(name)

  def printGreeting(people:Seq[Person])=
    for(Person(name,address)<-people if isCloseZip(address.zip))
      println("hello,%s".format(name))

}

