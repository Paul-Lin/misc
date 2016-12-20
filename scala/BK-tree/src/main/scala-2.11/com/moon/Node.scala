package com.moon
/**
  * Created by Paul on 2016/12/20.
  */
class Node[T](term:T) {
  val value:T=term
  // 用一个map存储子节点
  var children:Map[Double,Node[T]]=Map()

  def add(metricSpace: MetricSpace[T],value:T): Unit ={
    // value与父节点的距离
    val distance:Double=metricSpace.distance(this.value,value)

    // 距离为0，表示元素相同，返回
    if(distance == 0)
      return

    // 从父节点的子节点查找child，满足距离为distance
    val child:Node[T]=children.get(distance).get

    child match{
      case null =>{
        // 若距离父节点为distance的子节点不存在，则直接添加一个新的子节点
        children+=(distance->new Node[T](value))
      }
      case _ =>{
        // 若距离父节点为distance子节点存在，则递归的将value添加到该子节点下
        child.add(metricSpace,value)
      }
    }
  }

  def query(ms:MetricSpace[T],term:T,radius:Double,results:scala.collection.mutable.Set[T] ):Unit={
    val distance:Double=ms.distance(this.value,term)

    // 与父节点的距离小于阀值，则添加到结果集中，并继续向下寻找
    if(distance <= radius)
      results+(this.value)

    // 子节点的距离在最小距离和最大距离之间的
    // 由度量空间的d(x,y)+d(y,z) >= d(x,z)这一定理，有查找的value与子节点的距离范围如下:
    // min={1,distance-radius}, max=distance+radius
    var max:Double=Math.max(distance-radius,1)
    while(max < (distance+radius)){
      val child:Option[Node[T]]=children.get(max)
      // 递归调用
      if(child.nonEmpty)
        child.get.query(ms,term,radius,results)
      max=max+1
    }
  }
}
