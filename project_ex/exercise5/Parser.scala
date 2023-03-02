package VmGenerator

import java.io.{File, FileWriter}

import SyntexAnalyzer.Tokenizer

object Parser {
  def CreateVmFile(dir_path:String,fileName:String):FileWriter={
    var file_name="/"+fileName+".vm"
    var file=new File(dir_path+file_name)
    file.delete()
    file.createNewFile()
    new FileWriter(file,true)
  }

  def getTxmlFilesInPath(path: String):List[File]=
  {
    val dir=new File(path)
    if(dir.exists() && dir.isDirectory())
      dir.listFiles.filter(_.isFile()).filter(f=>f.getName.endsWith("T.xml")).toList
    else
      List[File]()
  }
  def main(args:Array[String]):Unit={
    var dir_path="C:/000learning/principles/nand2tetris/nand2tetris/projects/11/Square"
    Tokenizer.tokenize(dir_path)
    val tokenFiles=getTxmlFilesInPath(dir_path)

    for(currentFile<-tokenFiles)
    {
      println(currentFile.getName)
      vmFile=CreateVmFile(dir_path,currentFile.getName.replace("T.xml",""))
      lines=scala.io.Source.fromFile(currentFile.getPath).getLines().toList
      lineIndex=1
      ProgramStructure.classParse()
      vmFile.close()
      println("finish file")

    }
  }
}
