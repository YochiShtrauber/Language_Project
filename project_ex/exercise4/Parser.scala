package SyntexAnalyzer

import java.io.{File,FileWriter}

object Parser {
  
   def CreateXmlFile(dir_path:String,fileName:String):FileWriter={
     var file_name="/"+fileName
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
  
   
  def classParse()
  {
    parseFile.append("<class>\n")
    parseFile.append(lines(increaseIndex())+"\n")//<keyword> class </keyword>
    parseFile.append(lines(increaseIndex())+"\n")//<identifier> class_name </identifier>
    parseFile.append(lines(increaseIndex())+"\n")//<symbol> { </symbol>
    
    classVarDecParse()
    subDecParse()

    parseFile.append(lines(increaseIndex())+"\n")//<symbol> } </symbol>
    parseFile.append("</class>\n")
  }
  
  def  classVarDecParse()
  {
    while (lines(index).contains("static") ||  lines(index).contains("field"))
	  {
      parseFile.append("<classVarDec>\n")
      parseFile.append(lines(increaseIndex())+"\n")//<keyword> field or static </keyword>
      parseFile.append(lines(increaseIndex())+"\n")//<keyword> type </keyword>
      parseFile.append(lines(increaseIndex())+"\n")//<identifier> x </identifier>
		
		while (lines(index).contains(","))
		{
      parseFile.append(lines(increaseIndex())+"\n")// <symbol> , </symbol>
      parseFile.append(lines(increaseIndex())+"\n")// <identifier> y </identifier>
	  }
      parseFile.append(lines(increaseIndex())+"\n")// <symbol> ; </symbol>
      parseFile.append("</classVarDec>\n")
	
	  }
  }
  
  def subDecParse()
  {
     while (lines(index).contains("constructor") ||  lines(index).contains("function")||  lines(index).contains("method"))
	  {
		 parseFile.append("<subroutineDec>\n")
      parseFile.append(lines(increaseIndex())+"\n")//<keyword> constructor or function or method </keyword>
      parseFile.append(lines(increaseIndex())+"\n")//<keyword> type </keyword>
      parseFile.append(lines(increaseIndex())+"\n")//<identifier> x </identifier>
      parseFile.append(lines(increaseIndex())+"\n")//(
		  parameterListParse()
      parseFile.append(lines(increaseIndex())+"\n")//)
		  subBodyParse()
      parseFile.append("</subroutineDec>\n")
	
	  }
  }
  
  def parameterListParse()
  {
    parseFile.append("<parameterList>\n")
   if(lines(index).contains("keyword") || lines(index).contains("identifier"))//??
   {
     parseFile.append(lines(increaseIndex())+"\n")//<keyword> type </keyword>
     parseFile.append(lines(increaseIndex())+"\n")//<identifier> x </identifier>
	   while (lines(index).contains(","))
		{
      parseFile.append(lines(increaseIndex())+"\n")// <symbol> , </symbol>
      parseFile.append(lines(increaseIndex())+"\n")//<keyword> type </keyword>
      parseFile.append(lines(increaseIndex())+"\n")// <identifier> y </identifier>
	  }	
   }
    parseFile.append("</parameterList>\n")
  }
   def subBodyParse()
   {
     parseFile.append("<subroutineBody>\n")
     parseFile.append(lines(increaseIndex())+"\n")//{
     	varDecParse()
     	
     	Statements.statementsParse()
     parseFile.append(lines(increaseIndex())+"\n")//}
     parseFile.append("</subroutineBody>\n")
   }
   
 def varDecParse()
 {
    while (lines(index).contains("var"))
	  {
      parseFile.append("<varDec>\n")
      parseFile.append(lines(increaseIndex())+"\n")//<keyword>  var</keyword>
      parseFile.append(lines(increaseIndex())+"\n")//<keyword> type </keyword>
      parseFile.append(lines(increaseIndex())+"\n")//<identifier> x </identifier>
	   
	   	while (lines(index).contains(","))
		{
      parseFile.append(lines(increaseIndex())+"\n")// <symbol> , </symbol>
      parseFile.append(lines(increaseIndex())+"\n")// <identifier> y </identifier>
	  }
      parseFile.append(lines(increaseIndex())+"\n")// <symbol> ; </symbol>
      parseFile.append("</varDec>\n")
	
	  }
 }
 def main(args:Array[String]):Unit={
    var dir_path="C:/000learning/principles/nand2tetris/nand2tetris/projects/10/Square"
    var tokenFiles=getTxmlFilesInPath(dir_path)
    
    for(currentFile<-tokenFiles)
    {
        println(currentFile.getName)
        parseFile=CreateXmlFile(dir_path,currentFile.getName.replace("T.xml",".xml"))
        lines=scala.io.Source.fromFile(currentFile.getPath).getLines().toList
        index=1
        classParse()		
        parseFile.close()    
        println("finish file")
    }
   
  
 }
}