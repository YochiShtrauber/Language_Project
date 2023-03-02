package SyntexAnalyzer

import java.io.{File,FileWriter}
import scala.util.matching.Regex
import scala.util.control.Breaks._ 

object Tokenizer {
  

  def CreateTXmlFile(dir_path:String,fileName:String):FileWriter={
     var splitPath=dir_path.split("/").toArray
     var file_name="/"+fileName+"T.xml"
     var file=new File(dir_path+file_name)
     file.delete()
     file.createNewFile()
     new FileWriter(file,true)      
  }
  
   def getJackFilesInPath(path: String):List[File]=
  {
    val dir=new File(path)
    if(dir.exists() && dir.isDirectory())
      dir.listFiles.filter(_.isFile()).filter(f=>f.getName.endsWith("jack")).toList
    else
      List[File]()
  }
  
   
   def tokenize(dir_path:String):Unit={
    
    var string="\"[\\w|\\W]*\"".r
    var identifier="[a-zA-Z_][\\w|_]*".r
    var integer="[0-9]*".r
    var whiteSpace="[\\s]*".r
    var comment="[//][\\w|\\W]*".r
    var tok=""
    var longComment=false
    //get jack file
    //get lines
    //foreach line
    //var dir_path="C:/000learning/principles/nand2tetris/nand2tetris/projects/11/myTest"
    var jackFiles=getJackFilesInPath(dir_path)
    for(currentFile<-jackFiles)
    {
        var tokenFile=CreateTXmlFile(dir_path,currentFile.getName.replace(".jack",""))
        var lines=scala.io.Source.fromFile(currentFile.getPath).getLines().toList
        tokenFile.append("<tokens>\n")
			var cline=""
        for(cline<-lines)
        {
          var line=cline
					if(currentFile.getName.contains("TicTacToeGame"))
						print()
          while(line.nonEmpty)
          {
          tok=(whiteSpace findFirstIn line).mkString
        		  if(tok.nonEmpty && line.startsWith(tok))
        		  {
        			  line=line.slice(tok.length(), line.length())
        		  }
        		  if(line.startsWith("//"))
        		  {
        		    line=""
        		  }
        		  if(line.startsWith("/**") || line.startsWith("/*"))
            {
              longComment=true
      	    }
            if(longComment)
            {
              if(line.contains("*/")) {
								longComment = false
								line=line.slice(line.indexOf("*/")+2,line.length)
							}
							else
							line=""
							println(line)
            }
          if(line.nonEmpty && symbols.contains((line.head).toString()))
          {
            tok=(line.head).toString()

        			  //if < or >  or & or " then 
        		    var str:String=tok
        		    if(tok.equals("<"))
        		      str="&lt;"
        		    else
        		      if(tok.equals(">"))
        		      str="&gt;"
        		      else
        		        if(tok.equals("&"))
        		          str="&amp;"
        		       if(line.contains("Constructs"))
        		       println(tok+" symbol")//write to file
        		    tokenFile.append("<symbol> "+str+" </symbol>\n")        		  }
        		  else
             {
        			  tok=(string findFirstIn line).mkString
        					  if(tok.nonEmpty && line.startsWith(tok))
        					  {
        					      tokenFile.append("<stringConstant>"+tok+"</stringConstant>\n")
        					  }
        					  else
        					  {
        						  tok=(identifier findFirstIn line).mkString
        								  if(tok.nonEmpty && line.startsWith(tok))
        								  {
        									  //if keyword then ... else ...
        								    if(keywords.contains(tok))
        								        {
        								      tokenFile.append("<keyword> "+tok+" </keyword>\n")
        								        }
        					             
        					          else
        					          {
															tokenFile.append("<identifier> "+tok+" </identifier>\n")
														}
        								  }
        								  else
        								  {
        									  tok=(integer findFirstIn line).mkString
        											  if(tok.nonEmpty && line.startsWith(tok))
        											  {
        											    tokenFile.append("<integerConstant> "+tok+" </integerConstant>\n")
        											  }

        								  }

        					  }
             }
             line=line.slice(tok.length(), line.length())
            }              
          }

        tokenFile.append("</tokens>\n") 
        tokenFile.close()    
        println("finish file")
    }
   
  }
}