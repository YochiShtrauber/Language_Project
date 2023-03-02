package VmGenerator

object ProgramStructure {
  var funcLine=""
  var construcFlag=false
  var methodFlag=false
  def classParse()
  {
    argIndex=0
    staticIndex=0
    localIndex=0
    varIndex=0
    fieldIndex=0
    tableClass.clear()

    increaseLineIndex()//<keyword> class </keyword>
    className=getToken(lines(increaseLineIndex()))//<identifier> class_name </identifier>
    increaseLineIndex()//<symbol> { </symbol>
    classVarDecParse()
    subDecParse()
    increaseLineIndex()//<symbol> } </symbol>
  }

  def  classVarDecParse()
  {
 //Symbol(var name:String,var varType:String,var kind:String,var index:Int)
    while (lines(lineIndex).contains("static") ||  lines(lineIndex).contains("field"))
    {

      val varKind = getToken(lines(increaseLineIndex())) //<keyword> field or static </keyword>
      val varType = getToken(lines(increaseLineIndex())) //<keyword> type int string...</keyword>
      var varName = getToken(lines(increaseLineIndex())) //<identifier> x </identifier>
      varKind match{
       case "field"=>tableClass+=new Symbol(varName,varType,varKind,fieldIndex)
                      fieldIndex+=1
       case "static"=>tableClass+=new Symbol(varName,varType,varKind,staticIndex)
                      staticIndex+=1
       case _=>println("error")
      }
      /*if(varKind.contains("field"))
      {
        tableClass+=new Symbol(varName,varType,varKind,fieldIndex)
        fieldIndex+=1
      }
      else
      {
        if(varKind.contains("static"))
        {
          tableClass+=new Symbol(varName,varType,varKind,staticIndex)
          staticIndex+=1
        }
      }*/
      while (lines(lineIndex).contains(","))
      {
        increaseLineIndex()// <symbol> , </symbol>
        varName=getToken(lines(increaseLineIndex())) // <identifier> y </identifier>
        varKind match{
          case "field"=>tableClass+=new Symbol(varName,varType,varKind,fieldIndex)
            fieldIndex+=1
          case "static"=>tableClass+=new Symbol(varName,varType,varKind,staticIndex)
            staticIndex+=1
          case _=>println("error")
        }
       /* if(varKind.contains("field"))
        {
          tableClass+=new Symbol(varName,varType,varKind,fieldIndex)
          fieldIndex+=1
        }
        else
        {
          if(varKind.contains("static"))
          {
            tableClass+=new Symbol(varName,varType,varKind,staticIndex)
            staticIndex+=1
          }
        }*/
      }
      increaseLineIndex()// <symbol> ; </symbol>

    }
  }

  def subDecParse()
  {
    //labelcounter=0
    while (lines(lineIndex).contains("constructor") ||  lines(lineIndex).contains("function")||  lines(lineIndex).contains("method"))
    {
      funcLine="function "
      varIndex=0
      argIndex=0
      ifTrueCounter=0
      ifFalseCounter=0
      ifEndCounter=0
      whileBegCounter=0
      whileEndCounter=0
      tableFunc.clear()
      if(lines(lineIndex).contains("method")) {
        tableFunc += new Symbol("this", className, "argument", argIndex)
        argIndex += 1
        methodFlag=true
      }
      if(lines(lineIndex).contains("constructor"))
        construcFlag=true
      increaseLineIndex()
      increaseLineIndex()//<keyword> return type </keyword>
      funcLine+=className+"."+getToken(lines(increaseLineIndex()))+" "//<identifier> func name </identifier>
      increaseLineIndex()//(
      parameterListParse()
      increaseLineIndex()//)
      subBodyParse()
    }
  }
  def constructorParse(): Unit = {
    val n = tableClass.filter(t => t.kind.equals("field")).length
    vmFile.append("push constant "+n+"\n")
    vmFile.append("call Memory.alloc 1\n")
    vmFile.append("pop pointer 0\n")
    construcFlag=false
  }
  def methodParse() {
    vmFile.append("push argument 0\n")
    vmFile.append("pop pointer 0\n")
    methodFlag=false
  }
  def parameterListParse()
  {
    if(lines(lineIndex).contains("keyword") || lines(lineIndex).contains("identifier"))//??
      {
        var varType=getToken(lines(increaseLineIndex()))//<keyword> type int string</keyword>
        var varName=getToken(lines(increaseLineIndex()))//<identifier> x </identifier>
        val varKind="argument"
        tableFunc+=new Symbol(varName,varType,varKind,argIndex)
        argIndex+=1
        while (lines(lineIndex).contains(","))
        {
          increaseLineIndex()// <symbol> , </symbol>
          varType=getToken(lines(increaseLineIndex()))//<keyword> type </keyword>
          varName=getToken(lines(increaseLineIndex()))// <identifier> y </identifier>
          tableFunc+=new Symbol(varName,varType,varKind,argIndex)
          argIndex+=1
        }
      }
  }
  def subBodyParse()
  {
    increaseLineIndex()//{
    varDecParse()
    Statements.statementsParse()
    increaseLineIndex()//}
  }

  def varDecParse()
  {
    //var varCount=0
    while (lines(lineIndex).contains("var"))
    {
      val varKind="local"//getToken(lines(increaseLineIndex()))//<keyword>  var</keyword>
      increaseLineIndex()
      val varType=getToken(lines(increaseLineIndex()))//<keyword> type </keyword>
      var varName=getToken(lines(increaseLineIndex()))//<identifier> x </identifier>
      tableFunc+=new Symbol(varName,varType,varKind,varIndex)
      varIndex+=1
      //varCount+=1
      while (lines(lineIndex).contains(","))
      {
        increaseLineIndex()// <symbol> , </symbol>
        varName=getToken(lines(increaseLineIndex()))// <identifier> y </identifier>
        tableFunc+=new Symbol(varName,varType,varKind,varIndex)
        varIndex+=1
        //varCount+=1
      }
      increaseLineIndex()// <symbol> ; </symbol>
    }
    //funcLine+=varCount+"\n"      method int getx(){ ....}   function x.getx n
    funcLine+=varIndex//? maybe+1
    vmFile.append(funcLine+"\n")
    if(construcFlag)
      constructorParse()
    if(methodFlag)
      methodParse()
  }
}
