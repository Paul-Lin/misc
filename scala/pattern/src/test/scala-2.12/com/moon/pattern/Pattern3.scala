package com.moon.pattern

/**
  * Created by Paul on 2017/3/6.
  */
object Pattern3 {
  def makePurchase(register:CashRegister,amount:Int): ()=>Unit ={
    ()=>{
      println("Purchase in amount: "+amount)
      register.addCash(amount)
    }
  }

  var purchases:Vector[()=>Unit]=Vector()

  def executePurchase(purchase:()=>Unit): Unit ={
    purchases=purchases :+ purchase
    purchase()
  }

  def main(args: Array[String]) {
    val register=new CashRegister(0)
    val purchaseOne=makePurchase(register,100)
    val purchaseTwo=makePurchase(register,50)
    executePurchase(purchaseOne)
    executePurchase(purchaseTwo)
    println(register.total)
    register.total=0
    for(purchase<-purchases)
      purchase.apply()
    println(register.total)

  }
}

/**
  * 使用scala的混合特性
  * @param total
  */
class CashRegister(var total:Int){
  def addCash(toAdd:Int): Unit ={
    total+=toAdd
  }
}



