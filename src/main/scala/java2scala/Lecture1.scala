package java2scala
import scala.annotation.tailrec

object Greeting {
  println("Hello from other world")
  val greeting = "Aloha"
}

object Lecture1 {

  def emptyString = {
    println("here your empty string, dude")
    ""
  }

//  java.util.Collections.nCopies()

  @tailrec def repeat(word: String = "lol", rep: Int, acc: String = emptyString): String = {
    print(rep)
    if (rep == 0) acc
    else
      repeat(
        word,
        acc = word + acc,
        rep = rep - 1,
      )
  }

  def greeting(name: String, rep: Int) =
    s"Aloha, ${repeat(rep = rep)} "

  def name = {
    println("calculating name")
    "Oleg"
  }

  def main(args: Array[String]): Unit = {
    def name1 = {
      println("ohhhh")
      "ololo" + name
    }

    println(s"${greeting("Vitaly", 4)}!")
    println(s"${greeting("Vitaly", 4)}!")
  }
}
