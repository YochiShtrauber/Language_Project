package AsmConverter

import java.io.{File,FileWriter}

object Parser {
  
  def getVmFilesInPath(path: String):List[File]=
  {
    val dir=new File(path)
    if(dir.exists() && dir.isDirectory())
      dir.listFiles.filter(_.isFile()).filter(f=>f.getName.endsWith("vm")).toList
    else
      List[File]()
  }
  
  def CreateAsmFile(dir_path:String):FileWriter={
     var splitPath=dir_path.split("/").toArray
     //var file_name="/StackTest.asm"//get name from path
     var file_name="/"+splitPath(splitPath.length-1)+".asm"
     var file=new File(dir_path+file_name)
     file.delete()
     file.createNewFile()
     new FileWriter(file,true)   
    
  }
  
  def ConvertVMtoAsm(dir_path:String):Unit={
    asmFile=CreateAsmFile(dir_path)
    var vmFiles=getVmFilesInPath(dir_path)
    if(vmFiles.length>1)
      BasicCmdConverter.bootStrapping()
    for(currentFile<-vmFiles)
    {
      
           var fileName=currentFile.getName
           //println(fileName)
           val lines=scala.io.Source.fromFile(currentFile.getPath).getLines().toList
           for(line<-lines)
           {
                if(!(line.startsWith("//") || line.equals("")))
                {
                    asmFile.append("//"+line.toString()+"\n")
                    line.trim()
                    var splitLine=line.split(" ").toArray
                    
                     splitLine(0) match {                    
                      case "push"=>CmdHandler.pushHandle(fileName+"Static",splitLine(1), splitLine(2))
                      case "pop"=>CmdHandler.popHandle(fileName+"Static",splitLine(1), splitLine(2))
                      case "add"=>CmdHandler.addHandle()
                      case "sub"=>CmdHandler.subHandle()
                      case "neg"=>CmdHandler.negHandle()
                      case "eq"=>CmdHandler.eqHandle(fileName+"lbl")
                      case "gt"=> CmdHandler.gtHandle(fileName+"lbl")
                      case "lt"=>CmdHandler.ltHandle(fileName+"lbl")
                      case "and"=>CmdHandler.andHandle(fileName+"lbl")
                      case "or"=> CmdHandler.orHandle(fileName+"lbl")
                      case "not"=>CmdHandler.notHandle(fileName+"lbl")
                      case "label"=>CmdHandler.labelHandle(fileName+splitLine(1))
                      case "goto"=>CmdHandler.gotoHandle(fileName+splitLine(1))
                      case "if-goto"=>CmdHandler.ifgotoHandle(fileName+splitLine(1))
                      case "function"=>funcHandler.functionHandle(splitLine(1), splitLine(2))
                      case "call"=>funcHandler.callHandle(splitLine(1), splitLine(2))
                      case "return"=>funcHandler.returnHandle()
                      case _=>println("error: command not found ")
                              return
                      }
                }
              }
    } 
    asmFile.close()
    println("compilation ended successfuly")
  }

  def main(args:Array[String]):Unit={
    var dir_path="C:/000learning/principles/nand2tetris/nand2tetris/projects/08/FunctionCalls/FibonacciElement"
    //dir_path=scala.io.StdIn.readLine()
    ConvertVMtoAsm(dir_path)
  }
}