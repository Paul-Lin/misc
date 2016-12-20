package com.moon

/**
  * 距离度量方法接口
  * 度量空间
  * Created by Paul on 2016/12/20.
  */
trait MetricSpace[T] {
  def distance(a:T,b:T):Double
}
