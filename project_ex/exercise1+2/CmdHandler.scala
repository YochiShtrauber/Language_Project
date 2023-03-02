package AsmConverter

import java.io.{File,FileWriter}

object CmdHandler {
    def pushHandle(staticSymbol:String,seg:String,index:String):Unit={
      seg match {
          case "constant"=>SegHandler.pushConHandle(index)
          case "local"|"argument"|"this"|"that"=>SegHandler.pushArgLCLThisThatHandle(seg, index)
          case "pointer"=>
                      SegHandler.pushPointerHandle(index)
          case "static"=>
                      SegHandler.pushStaticHandle(staticSymbol,index)
          case "temp"=>
                      SegHandler.pushTempHandle(index)
                           
      }
    }
    def popHandle(staticSymbol:String,seg:String,index:String):Unit={
      seg match {
          case "local"|"argument"|"this"|"that"=>SegHandler.popArgLCLThisThatHandle(seg, index)
          case "pointer"=>
                      SegHandler.popPointerHandle(index)
          case "static"=>
                      SegHandler.popStaticHandle(staticSymbol,index)
          case "temp"=>
                      SegHandler.popTempHandle(index)
                           
      }
    }

    def addHandle():Unit={
       BasicCmdConverter.GetTopStackToD()
       BasicCmdConverter.decreaseSP()
       asmFile.append("A=M-1\n")
       asmFile.append("M=M+D\n")
    }
    
    def subHandle():Unit={
       BasicCmdConverter.GetTopStackToD()
       BasicCmdConverter.decreaseSP()
       asmFile.append("A=M-1\n")
       asmFile.append("M=M-D\n")
    }
    def negHandle():Unit={
       BasicCmdConverter.GetTopStackToD()
       asmFile.append("M=M-D\n")
       asmFile.append("M=M-D\n")
    }
    def eqHandle(lblName:String):Unit={
       BasicCmdConverter.GetTopStackToD()
       BasicCmdConverter.decreaseSP()
       asmFile.append("A=M-1\n")
       asmFile.append("D=M-D\n")//x-y
       BasicCmdConverter.trueOrFalse(lblName, "jeq")            
    }
     def gtHandle(lblName:String):Unit={
       BasicCmdConverter.GetTopStackToD()
       BasicCmdConverter.decreaseSP()
       asmFile.append("A=M-1\n")
       asmFile.append("D=M-D\n")//x-y
       BasicCmdConverter.trueOrFalse(lblName, "jgt")            
    }
     def ltHandle(lblName:String):Unit={
       BasicCmdConverter.GetTopStackToD()
       BasicCmdConverter.decreaseSP()
       asmFile.append("A=M-1\n")
       asmFile.append("D=M-D\n")//x-y
       BasicCmdConverter.trueOrFalse(lblName, "jlt")            
    }
     def andHandle(lblName:String):Unit={
       BasicCmdConverter.GetTopStackToD()
       BasicCmdConverter.decreaseSP()
       asmFile.append("A=M-1\n")
       var lbl=lblName+getIncreaselblCounter()
       asmFile.append("D=M&D\n")
       BasicCmdConverter.PushDToStackOverrideTop()           
     }
     def orHandle(lblName:String):Unit={
       BasicCmdConverter.GetTopStackToD()
       BasicCmdConverter.decreaseSP()
       asmFile.append("A=M-1\n")
       var lbl=lblName+getIncreaselblCounter()
       asmFile.append("D=M|D\n")
       BasicCmdConverter.PushDToStackOverrideTop()           
     }     
     def notHandle(lblName:String):Unit={
       BasicCmdConverter.GetTopStackToD()
       var lbl=lblName+getIncreaselblCounter()
      // asmFile.append("@"+lbl+"\n")
       asmFile.append("D=!D\n")
      // asmFile.append("("+lbl+")\n")
       BasicCmdConverter.PushDToStackOverrideTop()           
     }
     def labelHandle(lblName:String):Unit={
       asmFile.append("("+lblName+")\n")        
     }
     def gotoHandle(lblName:String):Unit={
       asmFile.append("@"+lblName+"\n") 
       asmFile.append("0;JMP\n")
     }
     def ifgotoHandle(lblName:String):Unit={
       BasicCmdConverter.GetTopStackToD()
       BasicCmdConverter.decreaseSP()
       asmFile.append("@"+lblName+"\n") 
       asmFile.append("D;JNE\n")
       
     }
     
}