package java2scala.homeworks.funiso

/** известен также как "изоморфизм"
  * должен удовлетворять законам
  * from(to(a : A)) == a
  * to(from(b : B)) == b
  */
trait Equivalent[A, B] {
  def to(a: A): B
  def from(b: B): A
}
