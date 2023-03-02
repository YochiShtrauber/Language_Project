package AsmConverter

import java.io.{File,FileWriter}

object Renderer {
  
  /*def getVmFilesInPath(path: String):List[File]=
  {
    val dir=new File(path)
    if(dir.exists() && dir.isDirectory())
      dir.listFiles.filter(_.isFile()).filter(f=>f.getName.endsWith("vm")).toList
    else
      List[File]()
  }
  
  def CreateAsmFile(dir_path:String):FileWriter={
     var s=dir_path.split("/").toArray
     //var file_name="/StackTest.asm"//get name from path
     var file_name="/"+s(s.length-1)+".asm"
     var file=new File(dir_path+file_name)
     file.delete()
     file.createNewFile()
     new FileWriter(file,true)   
    
  }
  
  def ConvertVMtoAsm(dir_path:String):Unit={
    var asmFile=CreateAsmFile(dir_path)
    var vmFiles=getVmFilesInPath(dir_path)
    if(vmFiles.length>1)
      BasicCmdConverter.bootStrapping(asmFile)
    for(currentFile<-vmFiles)
    {
      
           var fileName=currentFile.getName
           //println(fileName)
           val lines=scala.io.Source.fromFile(currentFile.getPath).getLines().toList
           for(line<-lines)
           {
                 //println(line)
                if(!(line.startsWith("//") || line.equals("")))
                {
                    asmFile.append("//"+line.toString()+"\n")
                    var splitLine=line.split(" ").toArray
                    
                    val cmd=splitLine(0) match {                    
                      case "push"=>CmdHandler.pushHandle(fileName+"Static",splitLine(1), splitLine(2),asmFile)
                      case "pop"=>CmdHandler.popHandle(fileName+"Static",splitLine(1), splitLine(2), asmFile)
                      case "add"=>CmdHandler.addHandle(asmFile)
                      case "sub"=>CmdHandler.subHandle(asmFile)
                      case "neg"=>CmdHandler.negHandle(asmFile)
                      case "eq"=>CmdHandler.eqHandle(fileName+"lbl", asmFile)
                      case "gt"=> CmdHandler.gtHandle(fileName+"lbl", asmFile)
                      case "lt"=>CmdHandler.ltHandle(fileName+"lbl", asmFile)
                      case "and"=>CmdHandler.andHandle(fileName+"lbl",asmFile)
                      case "or"=> CmdHandler.orHandle(fileName+"lbl",asmFile)
                      case "not"=>CmdHandler.notHandle(fileName+"lbl",asmFile)
                      case "label"=>CmdHandler.labelHandle(fileName+splitLine(1), asmFile)
                      case "goto"=>CmdHandler.gotoHandle(fileName+splitLine(1), asmFile)
                      case "if-goto"=>CmdHandler.ifgotoHandle(fileName+splitLine(1), asmFile)
                      case "function"=>funcHandler.functionHandle(splitLine(1), splitLine(2), asmFile)
                      case "call"=>funcHandler.callHandle(splitLine(1), splitLine(2), asmFile)
                      case "return"=>funcHandler.returnHandle(asmFile)
                      case _=>println("error: command not found")
                      
                      }
                }
              }
    } 
    asmFile.close()
    
  }
  
  
  def mainio(args:Array[String]):Unit={
    var dir_path="C:/000learning/principles/nand2tetris/nand2tetris/projects/08/FunctionCalls/FibonacciElement"
    //dir_path=scala.io.StdIn.readLine()
    ConvertVMtoAsm(dir_path)
  }*/
}