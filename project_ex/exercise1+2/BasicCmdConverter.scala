package AsmConverter

import java.io.{File,FileWriter}


object BasicCmdConverter {
  def bootStrapping()
  {
    asmFile.append("@256\n")
    asmFile.append("D=A\n")
    asmFile.append("@SP\n")
    asmFile.append("M=D\n")
    
    funcHandler.callHandle("Sys.init", "0")
  }
  def increaseSP():Unit={
        asmFile.append("@SP\n")
        asmFile.append("M=M+1\n")
  }
  
  def decreaseSP():Unit={
        asmFile.append("@SP\n")
        asmFile.append("M=M-1\n")
  }

  def PushDToStackTop():Unit={
        asmFile.append("@SP\n")
        asmFile.append("A=M\n")
        asmFile.append("M=D\n")
  }
   def PushDToStackOverrideTop():Unit={
        asmFile.append("@SP\n")
        asmFile.append("A=M-1\n")
        asmFile.append("M=D\n")
  }
    def PushDToStackOverrideTopByLCL():Unit={
        asmFile.append("@LCL\n")
        asmFile.append("M=M-1\n")
        asmFile.append("A=M\n")
        asmFile.append("D=M\n")
  }
   def PushDToLCLOverrideTop():Unit={
        asmFile.append("@LCL\n")
        asmFile.append("A=M-1\n")
        asmFile.append("M=D\n")
  }
  def setIndexToD(index:String):Unit={
        asmFile.append("@"+index+"\n")
        asmFile.append("D=A\n")
  }
   def setMIndexToD(index:String):Unit={
        asmFile.append("@"+index+"\n")
        asmFile.append("D=M\n")
  }
  def setDToM(index:String):Unit={
        asmFile.append("@"+index+"\n")
        asmFile.append("M=D\n")
  }
  def PushRamToD():Unit={     
      asmFile.append("A=M+D\n")
      asmFile.append("D=M\n")
  }
   def PopStackToRam(index:String):Unit={     
      asmFile.append("A=M\n")
      for(i<-1 to index.toInt)
          asmFile.append("A=A+1\n")
      asmFile.append("M=D\n")
  }
  def GetTopStackToD():Unit={
        asmFile.append("@SP\n")
        asmFile.append("A=M-1\n")
        asmFile.append("D=M\n")
  }
  def trueOrFalse(lblName:String,jumpType:String):Unit={
        var lblTrue=lblName+getIncreaselblCounter()
        var lblFalse=lblName+getIncreaselblCounter()
        asmFile.append("@"+lblTrue+"\n")
        asmFile.append("D;"+jumpType.toUpperCase()+"\n")
        asmFile.append("D=0\n")
        PushDToStackOverrideTop()
        asmFile.append("@"+lblFalse+"\n")
        asmFile.append("0;JMP\n")
        asmFile.append("("+lblTrue+")\n")
        asmFile.append("D=-1\n")
        PushDToStackOverrideTop()
        asmFile.append("("+lblFalse+")\n")
  }
}