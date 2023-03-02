import java.io.FileWriter

import SyntexAnalyzer.index

import scala.collection.mutable.ListBuffer

package object VmGenerator {
  val tableClass = ListBuffer[Symbol]()
  var tableFunc=ListBuffer[Symbol]()

  var lineIndex=0
  def increaseLineIndex():Int=
  {
    var temp=lineIndex
    lineIndex+=1
    temp
  }

  def getToken(line:String):String=
  {
    var ind=line.indexOf(">")
    var line1=line.slice(ind+1,line.length)
    ind=line1.indexOf("<")
    line1=line1.slice(0,ind)
    //line1=line1.trim()
    line1=line1.slice(1,line1.length-1)
    line1
  }
  def listContains(list:ListBuffer[Symbol],name:String):Symbol=
  {
    for(sym<-list)
      {
        if(sym.name==name)
          return sym
      }
    return null
  }

  var vmFile=new FileWriter("temp.txt",true)
  var lines:List[String]=List()

  var argIndex=0
  var staticIndex=0
  var localIndex=0
  var varIndex=0
  var fieldIndex=0

  var labelCounter:Int=0
  var ifTrueCounter:Int=0
  var ifFalseCounter:Int=0
  var ifEndCounter:Int=0
  var whileBegCounter:Int=0
  var whileEndCounter:Int=0
  /*def increaseLabelCounter(label:Int): Int ={
    label+=1
    label
  }*/

  var className=""
}
