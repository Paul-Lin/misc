package com.moon.pattern

import org.junit.Test

/**
  * Created by Paul on 2017/3/1.
  */
class Pattern14 {
  @Test
  def test(){
    val vs = Vector(20.0, 4.5, 50.0, 15.75, 30.0, 3.5)
    println(vs filter (price => price >= 20))
    println(vs map (price => price * 0.10))
    println(vs reduce ((total, price) => total + price))
  }

  def calculateDiscount(prices:Seq[Double]):Double={
    prices filter(price=>price>=20) map(
      price=>price*0.10) reduce(
      (total,price)=>total+price)
  }

  @Test
  def testCalculate(): Unit ={
    print(calculateDiscount(Vector(20.0,4.5,50.0,15.75,30.0,3.5)))
  }

  def calculateDiscountNamedFn(prices:Seq[Double]):Double={
    def isGreaterThan20(price:Double)=price>20
    def tenPercent(price:Double)=price*0.10
    def sumPrices(total:Double,price:Double)=total+price
    prices
  }
}
