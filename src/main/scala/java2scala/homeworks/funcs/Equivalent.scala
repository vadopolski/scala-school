package java2scala.homeworks.funcs

/** известен также как "изоморфизм"
  * должен удовлетворять законам
  * from(to(a : A)) == a
  * to(from(b : B)) == b
  */
trait Equivalent[A, B] {
  def to(a: A): B
  def from(b: B): A
}

object Equivalent {
  def apply[A, B](fto: A => B)(ffrom: B => A): Equivalent[A, B] = new Equivalent[A, B] {
    def to(a: A): B   = fto(a)
    def from(b: B): A = ffrom(b)
  }
}
