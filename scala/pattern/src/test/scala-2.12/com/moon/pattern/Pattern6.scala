package com.moon.pattern

/**
  * Created by Paul on 2017/3/2.
  */
object Pattern6 {

}

class TemplateExample{
  def makeOperation(
                     subOperationOne:()=>Unit,
                     subOperationTwo:()=>Unit)=
    ()=>{
      subOperationOne
      subOperationTwo
    }
}

/**
  * 模板方法模式
  */
class GradeReportTemplate{
  def reportGrades(
                    numToLetter:()=>Unit,
                    printGradeReport:()=>Unit)=
    ()=>{

    }
}