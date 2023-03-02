package SyntexAnalyzer

object Statements {
  def 	statementsParse()
  {
    parseFile.append("<statements>\n")
    statementParse()
    parseFile.append("</statements>\n")
  }
  
  def 	statementParse()
  {
    var line=lines(index)
    while(line.contains("let")|| line.contains("if") || line.contains("while") 
        || line.contains("do")||line.contains("return"))
    {
      
      if(lines(index).contains("let"))
      letStatement()
      
      if(lines(index).contains("if"))
      ifStatement()
      if(lines(index).contains("while"))
      whileStatement()
      if(lines(index).contains("do"))
      doStatement()
      if(lines(index).contains("return"))
      returnStatement()
      line=lines(index)
    }    
  }
  
  def letStatement()
  {
      parseFile.append("<letStatement>\n")
    	parseFile.append(lines(increaseIndex())+"\n")//let
      parseFile.append(lines(increaseIndex())+"\n")// var name
      if(lines(index).contains("["))
      {

        parseFile.append(lines(increaseIndex())+"\n")//[
        Expressions.expressionParse()

        parseFile.append(lines(increaseIndex())+"\n")//]
      }

      parseFile.append(lines(increaseIndex())+"\n")//=
      Expressions.expressionParse()
      parseFile.append(lines(increaseIndex())+"\n")//;
      parseFile.append("</letStatement>\n")

  }
  
  def ifStatement()
  {
      parseFile.append("<ifStatement>\n")
    parseFile.append(lines(increaseIndex())+"\n")//if
    parseFile.append(lines(increaseIndex())+"\n")// (
      Expressions.expressionParse()
    parseFile.append(lines(increaseIndex())+"\n")//)
    parseFile.append(lines(increaseIndex())+"\n")//{
      statementsParse()
    parseFile.append(lines(increaseIndex())+"\n")//}
      if(lines(index).contains("else")) {
        parseFile.append(lines(increaseIndex()) + "\n") //else
        parseFile.append(lines(increaseIndex()) + "\n") //{
        statementsParse()
        parseFile.append(lines(increaseIndex()) + "\n") //}
      }
    parseFile.append("</ifStatement>\n")
  }
  
   def whileStatement()
  {
      parseFile.append("<whileStatement>\n")
    	parseFile.append(lines(increaseIndex())+"\n")//while
      parseFile.append(lines(increaseIndex())+"\n")// (
      Expressions.expressionParse()
      parseFile.append(lines(increaseIndex())+"\n")//)
      parseFile.append(lines(increaseIndex())+"\n")//{
      statementsParse()
      parseFile.append(lines(increaseIndex())+"\n")//}
      parseFile.append("</whileStatement>\n")
  }
  
   def doStatement()
   {
     parseFile.append("<doStatement>\n")
     parseFile.append(lines(increaseIndex())+"\n")//do
     Expressions.subCallParse()
     parseFile.append(lines(increaseIndex())+"\n")//;
     parseFile.append("</doStatement>\n")
   }
   
    def returnStatement()
   {
     parseFile.append("<returnStatement>\n")
     parseFile.append(lines(increaseIndex())+"\n")//return
     if(lines(index).contains("Constant")||lines(index).contains("keyword")
         ||lines(index).contains("identifier")||lines(index).contains("-")||lines(index).contains("(")||lines(index).contains("~"))
     Expressions.expressionParse()
     parseFile.append(lines(increaseIndex())+"\n")//;
     parseFile.append("</returnStatement>\n")
   }
}