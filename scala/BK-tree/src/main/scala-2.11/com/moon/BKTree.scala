package com.moon

import java.util

import scala.collection.mutable
import scala.collection.JavaConversions._

/**
  * * BK树，可以用来进行拼写纠错查询
  *
  * 1.度量空间。
  * 距离度量空间满足三个条件：
  * d(x,y) = 0 <-> x = y (假如x与y的距离为0，则x=y)
  * d(x,y) = d(y,x) (x到y的距离等同于y到x的距离)
  * d(x,y) + d(y,z) >= d(x,z)  （三角不等式）
  *
  * 2、编辑距离（ Levenshtein Distance）符合基于以上三条所构造的度量空间
  *
  * 3、重要的一个结论：假设现在我们有两个参数，query表示我们搜索的字符串（以字符串为例），
  * n为待查找的字符串与query最大距离范围，我们可以拿一个字符串A来跟query进行比较，计
  * 算距离为d。根据三角不等式是成立的，则满足与query距离在n范围内的另一个字符转B，
  * 其余与A的距离最大为d+n，最小为d-n。
  *
  * 推论如下：
  * d(query, B) + d(B, A) >= d(query, A), 即 d(query, B) + d(A,B) >= d -->  d(A,B) >= d - d(query, B) >= d - n
  * d(A, B) <= d(A,query) + d(query, B), 即 d(query, B) <= d + d(query, B) <= d + n
  * 其实，还可以得到  d(query, A) + d(A,B) >= d(query, B)
  * -->   d(A,B) >= d(query, B) - d(query, A)
  * -->  d(A,B) >= 1 - d >= 0 (query与B不等)  由于 A与B不是同一个字符串d(A,B)>=1
  * 所以，   min{1, d - n} <= d(A,B) <= d + n
  *
  * 利用这一特点，BK树在实现时，子节点到父节点的权值为子节点到父节点的距离（记为d1）。
  * 若查找一个元素的相似元素，计算元素与父节点的距离，记为d, 则子节点中能满足要求的
  * 相似元素，肯定是权值在d - n <= d1 <= d + n范围内，当然了，在范围内，与查找元素的距离也未必一定符合要求。
  * 这相当于在查找时进行了剪枝，然不需要遍历整个树。试验表明，距离为1范围的查询的搜索距离不会超过树的5-8%，
  * 并且距离为2的查询的搜索距离不会超过树的17-25%。
  *
  * 参见：
  * http://blog.notdot.net/2007/4/Damn-Cool-Algorithms-Part-1-BK-Trees（原文）
  * http://www.cnblogs.com/data2value/p/5707973.html
  * Created by Paul on 2016/12/20.
  */
class BKTree[T](m: MetricSpace[T]) {
  val metricSpace: MetricSpace[T] = m

  var root:Node[T]=_
  /**
    * BK树中添加元素
    * @param term
    */
  def put(term:T): Unit ={
    root match{
      case null => root=new Node[T](term)
      case _ => root.add(metricSpace,term)
    }
  }

  /**
    * 查询相似元素
    * @param term 待查询的元素
    * @param radius 相似的距离范围
    * @return 满足距离范围的所有元素
    */
  def query(term:T,radius:Double):scala.collection.mutable.Set[T]={
    val results:scala.collection.mutable.Set[T]=mutable.Set[T]()
    if(root!=null)
      root.query(metricSpace,term,radius,results)
    results
  }
}

object BKTree {
  /**
    * 根据某个集合元素创建BK树
    * @param ms
    * @param elems
    * @tparam E
    * @return
    */
  def mkBKTree[E](ms: MetricSpace[E], elems: util.Collection[E]): BKTree[E] ={
    var bkTree:BKTree[E]=new BKTree[E](ms)
    for(elem <- elems)
      bkTree.put(elem)
    bkTree
  }

}


