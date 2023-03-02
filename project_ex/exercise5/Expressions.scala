package VmGenerator

object Expressions {
  def expressionParse(flag:Boolean=true)
  {
    termParse()
    while(lines(lineIndex).contains("+") || lines(lineIndex).contains("-") ||lines(lineIndex).contains("*")||lines(lineIndex).equals("<symbol> / </symbol>")
      ||lines(lineIndex).contains("&amp;") ||lines(lineIndex).contains("|")||lines(lineIndex).contains("&gt;")||lines(lineIndex).contains("&lt;")||lines(lineIndex).contains("=")) {
      val sign=getToken(lines(increaseLineIndex()))
      termParse()
      if(flag || !lines(lineIndex).contains("="))
        writevmsign(sign)
    }
  }
  def writevmsign(sign:String): Unit =
  {
    sign match{
      case "+"=>vmFile.append("add\n")
      case "-"=>vmFile.append("sub\n")
      case "*"=>vmFile.append("call Math.multiply 2\n")
      case "/"=>vmFile.append("call Math.divide 2\n")
      case "="=>vmFile.append("eq\n")
      case "&gt;"=>vmFile.append("gt\n")
      case "&lt;"=>vmFile.append("lt\n")
      case "&amp;"=>vmFile.append("and\n")
      case "|"=>vmFile.append("or\n")
      case _=>println(sign+"error")
    }
  }
  def countOccurrences(src: String, tgt: String): Int =
    src.sliding(tgt.length).count(window => window == tgt)

  def stringParse(): Unit ={
    val stringConstant=getToken(lines(increaseLineIndex()))
    var countBackSlash = stringConstant.count(c => c.toInt == 92)//counts number of \ in string
    val doubleBackSlash=countOccurrences(stringConstant,"\\\\")//count number of \\ in string
    countBackSlash=countBackSlash-doubleBackSlash
    vmFile.append("push constant "+(stringConstant.length-countBackSlash)+"\n")//push length
    vmFile.append("call String.new 1\n")//alloc memory to string
    //map with special characters like \n \t... and their ascii
    val specialChars = Map('t' -> "9",'n' -> "10", 'r' -> "13",34.toChar->"34",92.toChar->"92")
    var flagDouble=false//flag for if their is \ in string
    for(i<-stringConstant) {//foreach char in string
      if(i.toInt!=92 || flagDouble)//if current char is not \ or there was \ before current char
      {
        var letter=(i.toInt).toString//ascii of current char
        if(flagDouble) {//if their was \ before current char
          letter=specialChars(i)
          flagDouble = false
        }
        vmFile.append("push constant "+letter+"\n")
        vmFile.append("call String.appendChar 2\n")
      }
      else
        {
          if(!flagDouble)
            flagDouble=true//set the flag and ignore the \
        }
    }
  }

  def termParse()
  {
    if(lines(lineIndex).contains("Constant")) {
      if(lines(lineIndex).contains("string"))
          stringParse()
      else
          vmFile.append("push constant "+getToken(lines(increaseLineIndex()))+"\n")
    }
    else
    {
      if(lines(lineIndex).contains("keyword")) { //?what about keyword-true or false this
        if(lines(lineIndex).contains("this")) {
          increaseLineIndex()
          vmFile.append("push pointer 0\n")   //?
        }
        else{
        vmFile.append("push constant 0\n")
        if (getToken(lines(increaseLineIndex())).equals("true"))
          vmFile.append("not\n")}
      }
      else
      {
      if(lines(lineIndex).contains("identifier"))//
      {
        var temp=lineIndex//index is name of function or variable
        temp+=1//we will do look ahead of 1
        if(lines(temp).contains(".")||lines(temp).contains("("))
        {
          subCallParse()
        }
        else
        {
          val nameVar=getToken(lines(increaseLineIndex()))//x  ??local?? what about field or static
          var symbol=listContains(tableFunc,nameVar)
          var varType=""
          if(symbol!=null)
            {
              val pushLine="push "
             /* if(symbol.kind=="var")
               varType="local "
              else
                if(symbol.kind=="argument")
                  varType="argument "*/
              varType=symbol.kind+" "+symbol.index
              if(!symbol.varType.contains("Array") || !lines(lineIndex).contains("["))
                vmFile.append(pushLine+varType+"\n")
            }
          else
            {
              symbol=listContains(tableClass,nameVar)
              if(symbol!=null)
              {
                val pushLine="push "
                if(symbol.kind=="field")
                  varType="this "
                else
                  if(symbol.kind=="static")
                    varType="static "
                varType+=symbol.index
                if(!symbol.varType.contains("Array")|| !lines(lineIndex).contains("["))//?
                  vmFile.append(pushLine+varType+"\n")
              }
            }
         if(lines(lineIndex).contains("["))
          {
            increaseLineIndex()//[
            expressionParse()
            vmFile.append("push "+varType+"\n") //?
            vmFile.append("add\n")
            vmFile.append("pop pointer 1\npush that 0\n")
            increaseLineIndex()//]
          }
        }
      }
      else
      {
        if(lines(lineIndex).contains("("))
        {
         increaseLineIndex()//(
          expressionParse()
          increaseLineIndex()//)
        }
        else {
          if (lines(lineIndex).contains("-") || lines(lineIndex).contains("~")) {
            val un = getToken(lines(increaseLineIndex())) //~ or -
            termParse()
            if (un.equals("-")) {
              vmFile.append("neg\n")
            }
            else {
              vmFile.append("not\n")
            }
          }
        }
      }
      }
    }
  }

  def subCallParse()
  {
    var arg:Int=0
    var name=getToken(lines(increaseLineIndex()))//identifier- or name func or class    get(x,h,k) - push sq ,push args ,call square.new n
    if(lines(lineIndex).contains("."))
    {
      var sym=listContains(tableFunc,name)
      if(sym!=null) {
        name = sym.varType
       /* if(sym.kind=="var")
          vmFile.append("push local "+sym.index+"\n")//sym.index?
        else
          vmFile.append("push argument "+sym.index+"\n")*/
        vmFile.append("push "+sym.kind+" "+sym.index+"\n")
        arg=1
      }
      sym=listContains(tableClass,name)
      if(sym!=null)
        {
          name = sym.varType
          if(sym.kind=="field")
            vmFile.append("push this "+sym.index+"\n")
          else
            vmFile.append("push static "+sym.index+"\n")
          arg=1
        }

      name=name+getToken(lines(increaseLineIndex()))//.
      name+=getToken(lines(increaseLineIndex())+"\n")//func name
    }
    else {
      vmFile.append("push pointer 0\n")
      name = className+"."+name
      arg=1
    }
    increaseLineIndex()//(
    arg=arg+expressionListParse()
    increaseLineIndex()//)
    vmFile.append("call "+name+" "+arg+"\n")
  }

  def expressionListParse():Int=
  {
    var countExp=0
    if(lines(lineIndex).contains("Constant") || lines(lineIndex).contains("keyword") ||
      lines(lineIndex).contains("identifier") ||  lines(lineIndex).contains("(")||lines(lineIndex).contains("-")||lines(lineIndex).contains("~"))
    {
      expressionParse()//
      countExp+=1
      while(lines(lineIndex).contains(","))
      {
        increaseLineIndex()//,
        expressionParse()
        countExp+=1
      }
    }
    countExp
  }
}
