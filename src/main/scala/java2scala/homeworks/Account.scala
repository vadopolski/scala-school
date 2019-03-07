package java2scala.homeworks

class Account private (val id: Int, initialBalance: Double) {
  private var balance = initialBalance

}

object Account {
  def apply(initialBalance: Double) =
    new Account(1, initialBalance)

}

object AccountDriver extends App {
  val accct = Account(100000)

  println(accct.id)


}