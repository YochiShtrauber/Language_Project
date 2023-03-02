package VmGenerator

object Statements {
  def 	statementsParse()
  {
    statementParse()
  }

  def 	statementParse()
  {
    var line=lines(lineIndex)
    while(line.contains("let")|| line.contains("if") || line.contains("while")
      || line.contains("do")||line.contains("return"))
    {

      if(lines(lineIndex).contains("let"))
        letStatement()
      if(lines(lineIndex).contains("if"))
        ifStatement()
      if(lines(lineIndex).contains("while"))
        whileStatement()
      if(lines(lineIndex).contains("do"))
        doStatement()
      if(lines(lineIndex).contains("return"))
        returnStatement()
      line=lines(lineIndex)
    }
  }

  def letStatement()
  {//let x=5+y*func()
    //push the value
    var popLine="pop "
    var vartype=""
    var arrType=false
    increaseLineIndex()//let
    val varName=getToken(lines(increaseLineIndex()))// var name
    var symbol=listContains(tableFunc,varName)
    if(symbol!=null) {
     /* if (sym.kind == "var") {
        vartype="local "+sym.index.toString
      }
      else
        vartype="argument "+sym.index.toString*/
      vartype=symbol.kind+' '+symbol.index
      //popLine += vartype
      popLine+=symbol.kind+" "+symbol.index
    }
    else
    {
      symbol=listContains(tableClass,varName)
      if(symbol!=null)
        if (symbol.kind == "field")//???
          vartype = "this "+ symbol.index.toString
        else
          vartype = "static "+symbol.index.toString
      popLine += vartype
    }
    if(lines(lineIndex).contains("["))
    {
      increaseLineIndex()//[
      Expressions.expressionParse()
      vmFile.append("push "+vartype+"\n")
      vmFile.append("add\n")
      increaseLineIndex()//]
      arrType=true
    }

    increaseLineIndex()//=
    Expressions.expressionParse(false)
    if(arrType) {
      popLine="pop temp 0\npop pointer 1\npush temp 0\npop that 0"
      arrType=false
    }
    vmFile.append(popLine+"\n")
    increaseLineIndex()//;
  }

  def ifStatement()
  {
    val trueLabel="IF_TRUE"+ifTrueCounter
    ifTrueCounter+=1
    val falseLabel="IF_FALSE"+ifFalseCounter
    ifFalseCounter+=1
    val endLabel="IF_END"+ifEndCounter
    ifEndCounter+=1
    increaseLineIndex()//if
    increaseLineIndex()// (
    Expressions.expressionParse()
    increaseLineIndex()//)
    vmFile.append("if-goto "+trueLabel+"\n" )
    vmFile.append("goto "+falseLabel+"\n")
    vmFile.append("label "+trueLabel+"\n")
    increaseLineIndex()//{
    statementsParse()
    increaseLineIndex()//}
    if(lines(lineIndex).contains("else"))
      vmFile.append("goto "+endLabel+"\n")
    vmFile.append("label "+falseLabel+"\n")
    if(lines(lineIndex).contains("else")) {
      increaseLineIndex() //else
      increaseLineIndex() //{
      statementsParse()
      increaseLineIndex()//}
      vmFile.append("label "+endLabel+"\n")
    }
  }

  def whileStatement()
  {
    increaseLineIndex()//while
    increaseLineIndex()// (
    var whileBegLabel="WHILE_EXP"+whileBegCounter
    whileBegCounter+=1
    var whileEndLabel="WHILE_END"+whileEndCounter
    whileEndCounter+=1
    vmFile.append("label "+whileBegLabel+"\n")
    Expressions.expressionParse()
    vmFile.append("not\n")
    vmFile.append("if-goto "+whileEndLabel+"\n")
    increaseLineIndex()//)
    increaseLineIndex()//{
    statementsParse()
    vmFile.append("goto "+whileBegLabel+"\n")
    increaseLineIndex()//}
    vmFile.append("label "+whileEndLabel+"\n")
  }

  def doStatement()
  {
    increaseLineIndex()//do
    Expressions.subCallParse()
    increaseLineIndex()//;
    vmFile.append("pop temp 0\n")
  }

  def returnStatement()
  {
    val vmreturn=getToken(lines(increaseLineIndex()))//return      return;     return func()*8+x;
    if(lines(lineIndex).contains(";"))//increase or index+1
    {
           vmFile.append("push constant 0\n")
    }
    else {
      /*if (lines(lineIndex).contains("Constant") || lines(lineIndex).contains("keyword")
        || lines(lineIndex).contains("identifier") || lines(lineIndex).contains("-") || lines(lineIndex).contains("(") || lines(lineIndex).contains("~"))
       */
        Expressions.expressionParse()
    }
    vmFile.append(vmreturn+"\n")//return
    increaseLineIndex()//;
  }
}
