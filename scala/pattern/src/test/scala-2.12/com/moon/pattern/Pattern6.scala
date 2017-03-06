package com.moon.pattern

/**
  * Created by Paul on 2017/3/2.
  */
object Pattern6 {



}

class TemplateExample {
  def makeOperation(
                     subOperationOne: () => Unit,
                     subOperationTwo: () => Unit) =
    () => {
      subOperationOne
      subOperationTwo
    }
}

/**
  * 模板方法模式
  */
class GradeReportTemplate {
  def makeGradeReport(
                       numToLetter: (Double) => String,
                       printGradeReport: (Seq[String] => Unit)) = (grades: Seq[Double]) => {
    printGradeReport(grades.map(numToLetter))
  }


  def fullGradeConverter(grade: Double) =
    if (grade <= 5.0 && grade > 4.0) "A"
    else if (grade <= 4.0 && grade > 3.0) "B"
    else if (grade <= 3.0 && grade > 2.0) "C"
    else if (grade <= 2.0 && grade > 0.0) "D"
    else if (grade == 0.0) "F"
    else "N/A"

  def printHistogram(grades:Seq[String]): Unit ={
    val grouped=grades.groupBy(identity)
    val counts=grouped.map((kv)=>(kv._1,kv._2.size)).toSeq.sorted
    for(count <- counts){
      val stars="*"*count._2
      println("%s: %s".format(count._1,stars))
    }
  }
}