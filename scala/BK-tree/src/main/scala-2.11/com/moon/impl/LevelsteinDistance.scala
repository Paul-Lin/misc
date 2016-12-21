package com.moon.impl

import com.moon.MetricSpace

/**
  * 编辑距离， 又称Levenshtein距离，是指两个字串之间，由一个转成另一个所需的最少编辑操作次数。
  * 该类中许可的编辑操作包括将一个字符替换成另一个字符，插入一个字符，删除一个字符。
  *
  * 使用动态规划算法。算法复杂度：m*n。
  * Created by Paul on 2016/12/20.
  */
class LevelsteinDistance extends MetricSpace[String] {
  var insertCost: Double = 1
  var deleteCost: Double = 1
  var substituCost: Double = 1.5

  def computeDistance(target: String, source: String): Double = {
    val n: Int = target.trim.length
    val m: Int = source.trim.length

    val distance: Array[Array[Double]] = Array.ofDim[Double](n + 1, m + 1)
    distance(0)(0) = 0

    for (i <- 1 to m)
      distance(0)(i) = i

    for (j <- 1 to n)
      distance(j)(0) = j

    for (i <- 1 to n) {
      for (j <- 1 to m) {
        var min: Double = distance(i - 1)(j - 1) + insertCost
        if (target.charAt(i - 1) == source.charAt(j - 1)) {
          if (min > distance(i - 1)(j - 1))
            min = distance(i - 1)(j - 1)
        } else {
          if (min > distance(i - 1)(j - 1) + substituCost)
            min = distance(i - 1)(j - 1) + substituCost
        }
        if (min > distance(i)(j - 1) + deleteCost)
          min = distance(i)(j - 1) + deleteCost
        distance(i)(j) = min
      }
    }
    distance(n)(m)
  }

  override def distance(a: String, b: String): Double = computeDistance(a, b)


}

object LevelsteinDistance {

}
