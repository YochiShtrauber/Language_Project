package SyntexAnalyzer

object Expressions {
  def expressionParse()
  {

    parseFile.append("<expression>\n")
    termParse()
   // "{","}","(",")","[","]",".",",",";","~"
    while(lines(index).contains("+") || lines(index).contains("-") ||lines(index).contains("*")||lines(index).equals("<symbol> / </symbol>")
    ||lines(index).contains("&amp;") ||lines(index).contains("|")||lines(index).contains("&gt;")||lines(index).contains("&lt;")||lines(index).contains("="))
    {
      parseFile.append(lines(increaseIndex())+"\n")
      termParse()
    }
    parseFile.append("</expression>\n")
  }
  
  def termParse()
  {
    parseFile.append("<term>\n")
   if(lines(index).contains("Constant")||lines(index).contains("keyword")) {
     parseFile.append(lines(increaseIndex()) + "\n")
   }
   else
   {
     if(lines(index).contains("identifier"))
     {  

        var temp=index//index is name of function or variable
        temp+=1//we will do look ahead of 1
        if(lines(temp).contains(".")||lines(temp).contains("("))
        {
          subCallParse()
        }
        else
        {
          parseFile.append(lines(increaseIndex())+"\n")//x
         if(lines(index).contains("["))
          {

            parseFile.append(lines(increaseIndex())+"\n")//[
             expressionParse()
            parseFile.append(lines(increaseIndex())+"\n")//]
           }
        }
     }
     else
     {
        if(lines(index).contains("("))
          {
            parseFile.append(lines(increaseIndex())+"\n")//(
             expressionParse()
            parseFile.append(lines(increaseIndex())+"\n")//)
           }
        else
        {
          if(lines(index).contains("-")||lines(index).contains("~"))
          {
            parseFile.append(lines(increaseIndex())+"\n")//~ or -
            termParse()
          }
        }
     }
   }
    parseFile.append("</term>\n")
  }
  
  def subCallParse()
  {

    parseFile.append(lines(increaseIndex())+"\n")//identifier or name func or class
    if(lines(index).contains("."))
    {
      parseFile.append(lines(increaseIndex())+"\n")//.
      parseFile.append(lines(increaseIndex())+"\n")//func name
    }

    parseFile.append(lines(increaseIndex())+"\n")//(
    expressionListParse()
    parseFile.append(lines(increaseIndex())+"\n")//)
  }
  
  def expressionListParse()
  {
    parseFile.append("<expressionList>\n")
    if(lines(index).contains("Constant") || lines(index).contains("keyword") ||
        lines(index).contains("identifier") ||  lines(index).contains("(")||lines(index).contains("-")||lines(index).contains("~"))
    {
      expressionParse()//?
      while(lines(index).contains(","))
      {
        parseFile.append(lines(increaseIndex())+"\n")//,
        expressionParse()
      }
    }
    parseFile.append("</expressionList>\n")
  }
  
}