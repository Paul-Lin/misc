package com.moon.impl

import org.junit.Test

/**
  * Created by Paul on 2016/12/20.
  */
class LevelsteinDistanceTest {
  @Test
  def testComputerDistance(): Unit ={
    val distance:LevelsteinDistance=new LevelsteinDistance
    println(distance.computeDistance("你好","好你"))
  }
}
