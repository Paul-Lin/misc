package com.moon.pattern

/**
  * Created by Paul on 2017/3/6.
  */
object Pattern5 {
  val isVowel=Set('a','e','i','o','u')

  def test(): Unit ={
    println(vowelIsInWord("onomotopeia"))
    println(vowelIsInWord("yak"))

    println(prependHello(Vector("Mike","John","Joe")))
  }

  def main(args: Array[String]) {
    print(sumSequence(Vector(1,2,3,4,5)))
  }

  def vowelIsInWord(word:String)=word.filter(isVowel).toSet

  def prependHello(names:Seq[String])=names.map((name)=>"Hello "+name)

  def sumSequence(sequence:Seq[Int])=if(sequence.isEmpty)0 else sequence.reduce((acc,curr)=> acc+curr)
}
