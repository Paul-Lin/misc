package com.moon.impl

import com.moon.{BKTree, MetricSpace}
import org.junit.Test

/**
  * Created by Paul on 2016/12/20.
  */
class SpellChecker {
  @Test
  def testSpellChecker(): Unit ={
    // 编辑距离阀值
    val radius:Double=1.5
    // 待纠错的词
    val term:String="helli"

    // 创建BK树
    val ms:MetricSpace[String]=new LevelsteinDistance
    val bk:BKTree[String]=new BKTree[String](ms)

    bk.put("hello")
    bk.put("shell")
    bk.put("holl")

    val set:scala.collection.mutable.Set[String]=bk.query(term,radius)
    println(set.toString)
  }
}
