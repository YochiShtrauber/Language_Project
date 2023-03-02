

package AsmConverter

import java.io.{File,FileWriter}

object SegHandler {
     def pushConHandle(index:String)
     {
       BasicCmdConverter.setIndexToD(index)
       BasicCmdConverter.PushDToStackTop()
       BasicCmdConverter.increaseSP()
     }     
      def pushArgLCLThisThatHandle(seg:String,index:String)
     {
       BasicCmdConverter.setIndexToD(index)
       seg match {
          case "local"=>asmFile.append("@LCL\n")
          case "argument"=>asmFile.append("@ARG\n")
          case "this"=>asmFile.append("@THIS\n")
          case "that"=>asmFile.append("@THAT\n")
          }
       BasicCmdConverter.PushRamToD()
       BasicCmdConverter.PushDToStackTop()
       BasicCmdConverter.increaseSP()
     }
       def pushPointerHandle(index:String)
     {
         if(index.equals("0"))
             asmFile.append("@THIS\n")
         else
             asmFile.append("@THAT\n")
         asmFile.append("D=M\n")
         BasicCmdConverter.PushDToStackTop()
         BasicCmdConverter.increaseSP()
     }
      def pushStaticHandle(staticSymbol:String,index:String)
     {
       asmFile.append("@"+staticSymbol+index+"\n")
       asmFile.append("D=M\n")
       BasicCmdConverter.PushDToStackTop()
       BasicCmdConverter.increaseSP()
     }
      def pushTempHandle(index:String)
      {
         var i=(index.toInt+5).toString()
         asmFile.append("@"+i+"\n")
         asmFile.append("D=M\n")
         BasicCmdConverter.PushDToStackTop()
         BasicCmdConverter.increaseSP()
      }
      def popArgLCLThisThatHandle(seg:String,index:String)
     {
       BasicCmdConverter.GetTopStackToD()
       seg match {
          case "local"=>asmFile.append("@LCL\n")
          case "argument"=>asmFile.append("@ARG\n")
          case "this"=>asmFile.append("@THIS\n")
          case "that"=>asmFile.append("@THAT\n")
          }
       BasicCmdConverter.PopStackToRam(index)
       BasicCmdConverter.decreaseSP()
     }
       def popPointerHandle(index:String)
     {
         BasicCmdConverter.GetTopStackToD()
         if(index.equals("0"))
             asmFile.append("@THIS\n")
         else
             asmFile.append("@THAT\n")
         asmFile.append("M=D\n")
         BasicCmdConverter.decreaseSP()
     }
      def popTempHandle(index:String)
     {
       BasicCmdConverter.GetTopStackToD()
       var i=(index.toInt+5).toString()
       asmFile.append("@"+i+"\n")
       asmFile.append("M=D\n")
       BasicCmdConverter.decreaseSP()
     }
     def popStaticHandle(staticSymbol:String,index:String)
     {
       BasicCmdConverter.GetTopStackToD()
       asmFile.append("@"+staticSymbol+index+"\n")
       asmFile.append("M=D\n")
       BasicCmdConverter.decreaseSP()
     }
}