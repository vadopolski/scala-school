package java2scala.homeworks

object LectureMy63 extends App {



final case class Fruit(name: String, age: Int)

val fruit = Fruit("pear", 2)

fruit match {
  case Fruit(name, color) => println((name, color))
}


final class Fruit2(name: String, age: Int)

//object Fruit2{
//  def apply(name: String, color: Long) : Fruit2 = new Fruit2(name, color)
//
//}
//
//fruit match {
//  case Fruit2(name, color) => println((name, color))
//}

}