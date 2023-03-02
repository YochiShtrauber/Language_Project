package AsmConverter

import java.io.{File,FileWriter}

object funcHandler 
{
  def callHandle(funcName:String,paramNum:String)
  {
    var funcReturnLebel=funcName+".returnAddress"+getIncreasefuncCounter()
    CmdHandler.pushHandle("","constant",funcReturnLebel)
    pushSegToStack("LCL")
    pushSegToStack("ARG")
    pushSegToStack("THIS")
    pushSegToStack("THAT")
    
    BasicCmdConverter.setMIndexToD("SP")
    var newArg=(paramNum.toInt+5).toString()
    asmFile.append("@"+newArg+"\n")
    asmFile.append("D=D-A\n")
    BasicCmdConverter.setDToM("ARG")
    
    BasicCmdConverter.setMIndexToD("SP")
    BasicCmdConverter.setDToM("LCL")
    
    CmdHandler.gotoHandle(funcName)
    
    CmdHandler.labelHandle(funcReturnLebel)
  }
  
  def pushSegToStack(seg:String)
  {
     BasicCmdConverter.setMIndexToD(seg)
     BasicCmdConverter.PushDToStackTop()
     BasicCmdConverter.increaseSP() 
  }
  def functionHandle(funcName:String,localVarNum:String)
  {
    CmdHandler.labelHandle(funcName)//label func
     for(i<-1 to localVarNum.toInt)//k times
          CmdHandler.pushHandle("", "constant", "0")//push constant 0
  }
  def returnHandle()
  {
    BasicCmdConverter.setMIndexToD("LCL")
    asmFile.append("@5\n")
    asmFile.append("A=D-A\n")
    asmFile.append("D=M\n")
    BasicCmdConverter.setDToM("13")
    CmdHandler.popHandle("","argument","0")
    
    BasicCmdConverter.setMIndexToD("ARG")
    asmFile.append("@SP\n")
    asmFile.append("M=D+1\n")
    
    popSeg("THAT")
    popSeg("THIS")
    popSeg("ARG")
    popSeg("LCL")
    
    asmFile.append("@13\n")
    asmFile.append("A=M\n")
    asmFile.append("0;JMP\n")
  }
 def popSeg(seg:String)
 {
    BasicCmdConverter.PushDToStackOverrideTopByLCL()
    BasicCmdConverter.setDToM(seg)

 }
}