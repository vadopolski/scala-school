package java2scala

object Lecture2 {

  trait Food

  abstract class Animal[Self, Eats](name: String, full: Double = 0) {
    animal: Self =>
    def eat(food: Eats): Self

    def eatLegacy(food: Food): Animal[Self, Eats]
  }

  case class Grass() extends Food

  object Animal

  case class Cow(name: String, full: Double = 0) extends Animal[Cow, Grass](name, full) with Food {

    override def eatLegacy(food: Food): Cow =
      if (food.isInstanceOf[Grass]) eat(food.asInstanceOf[Grass])
      else {
        println("I dont eat it")
        this
      }

    override def eat(food: Grass): Cow = {
      println("yummi")
      Cow(name, full + 0.1)
    }

  }

  case class Lion(name: String, full: Double = 0) extends Animal[Lion, Cow](name, full) {
    def eat(cow: Cow): Lion = {
      println(s"that ${cow.name} was delicious, thank you")
      Lion(name, full)
    }

    def eatLegacy(food: Food): Lion = food match {
      case cow: Cow => eat(cow)

      case _ =>
        println("I dont eat it")
        this
    }

  }

  def main(args: Array[String]): Unit = {
    val cow   =  Cow("maggy")
    val lion  = Lion("john")
    val grass = Grass()
    val cow2  = cow.eat(grass)
    lion.eat(cow2)

//    cow: cow2.type

    println(cow)
    println(cow2)
  }
}
