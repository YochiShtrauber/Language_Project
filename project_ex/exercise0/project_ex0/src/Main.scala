import scala.io.Source
import java.io._


object Main {
  
  def getListOfFiles(dir: String):List[File] = 
  {
    val d = new File(dir)
    if (d.exists && d.isDirectory)
    {
        d.listFiles.filter(_.isFile).toList
    } 
    else 
    {
        List[File]()
    }
  }

  
  def main(args:Array[String]):Unit={
//create a file and read a file
/*val pw = new PrintWriter(new File("hello.txt" ))
pw.write("Hello, world")
pw.close
val pr=Source.fromFile("r.txt").getLines.mkString
print(pr)*/
  val path: String="C:/Users/user/Desktop/scala_dir_ex0" 
  
  val lst:List[File]=getListOfFiles(path)
  
  val i:Int=0
for(file<-lst)
{
  println(i)
  if(file.getName().takeRight(4) == ".txt")
  {
    val write=new PrintWriter(new FileOutputStream(file,true))
    write.write(i.toString())
val pr=Source.fromFile(file).getLines.mkString
print(pr)
     i=+1
     println(i)
  }
}
  }
}