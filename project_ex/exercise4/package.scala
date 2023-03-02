import java.io.{File,FileWriter}

package object SyntexAnalyzer {
  var keywords:List[String] = List("class","constructor","function","method","field","static","var","int","char","boolean","void","true","false","null","this","let","do","if","else","while","return")
  var symbols:List[String]=List("{","}","(",")","[","]",".",",",";","+","-","*","/","&","|","<",">","=","~")
  
  var parseFile=new FileWriter("temp.txt",true)
   var index=0
   def increaseIndex():Int=
   {
     var temp=index
     index+=1
     temp
   }
   var lines:List[String]=List()
  
   
}
