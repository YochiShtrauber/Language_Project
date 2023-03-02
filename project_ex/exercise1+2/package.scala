import java.io.{File,FileWriter}

package object AsmConverter {
  var asmFile=new FileWriter("temp.txt",true)
  var funcCounter=0
  var lblCounter=0
  var staticVarCounter=0
  def getIncreasefuncCounter():Int=
  {
    funcCounter+=1
    funcCounter
  }
  def getIncreaselblCounter():Int=
  {
    lblCounter+=1
    lblCounter
  }
   def getIncreasestaticVarCounter():Int=
  {
    staticVarCounter+=1
    staticVarCounter
  }
}