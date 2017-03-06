package com.moon.pattern

/**
  * Created by Paul on 2017/3/6.
  */
object ScalaPattern3 {
  var purchases: Vector[() => Unit] = Vector()

  def makePurchase(register: ScalaCashRegister, amount: Int): () => Unit = {
    () => {
      println("purchase in amount: " + amount)
      register.total + amount
    }
  }

  def executePurchase(purchase: () => Unit): Unit = {
    purchases = purchases :+ purchase
//    purchase()
  }

  def main(args: Array[String]) {
    val register: ScalaCashRegister = new ScalaCashRegister(0)
    val purchaseOne = makePurchase(register, 100)
    val purchaseTwo = makePurchase(register, 50)
    executePurchase(purchaseOne)
    executePurchase(purchaseTwo)
    for(purchase<-purchases)
      purchase.apply()
  }
}

class ScalaCashRegister(var total: Int) {
  def addCash(toAdd: Int): Unit = {
    total += toAdd
  }
}

