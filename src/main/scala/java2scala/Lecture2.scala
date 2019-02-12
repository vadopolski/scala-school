package java2scala

object Lecture2 {

  class Animal(val name: String){
    override def toString = s"animal $name"
  }

  object Animal{
    def apply(idx: Int): Animal = new Animal(idx.toString)
  }

  class Cow(val idx: Int) extends Animal(idx.toString) {
    override def toString: String =
      s"Cow $name $idx"
  }

  def main(args: Array[String]): Unit = {
    val cow = new Cow(3)
    val idx = cow.idx
    val animal = Animal(5)


    println(cow)
    println(animal)
  }
}
