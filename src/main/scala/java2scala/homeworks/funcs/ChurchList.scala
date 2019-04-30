package java2scala.homeworks.funcs

trait ChurchList[A] { self =>
  def ::(x: A): ChurchList[A] = new ChurchList[A] {
    def fold[R](z: R)(ag: A => R => R): R = ag(x)(self.fold(z)(ag))
  }

  def fold[R](z: R)(ag: A => R => R): R

  def map[B](f: A => B): ChurchList[B] =
    new ChurchList[B] {
      def fold[R](z: R)(ag: B => R => R): R = self.fold(z)(a => ag(f(a)))
    }

  def filter(p: A => Boolean): ChurchList[A] =
    new ChurchList[A] {
      def fold[R](z: R)(ag: A => R => R): R = self.fold(z)(a => if (p(a)) ag(a) else identity)
    }

  def flatMap[B](f: A => ChurchList[B]): ChurchList[B] = new ChurchList[B] {
    def fold[R](z: R)(ag: B => R => R): R =
      self.fold(z)(a => r => f(a).fold(r)(ag))
  }

  def ++(that: ChurchList[A]): ChurchList[A] = new ChurchList[A] {
    def fold[R](z: R)(ag: A => R => R): R = self.fold(that.fold(z)(ag))(ag)
  }

  def headOption: Option[A] = fold[Option[A]](None)(a => _.orElse(Some(a)))

  def toList: List[A] = fold[List[A]](Nil)(e => e :: _)
}

object ChurchList {
  def apply[A](xs: A*): ChurchList[A] = new ChurchList[A] {
    def fold[R](z: R)(ag: A => R => R): R = xs.foldRight(z)(Function.uncurried(ag))
  }

  def empty[A]: ChurchList[A] = new ChurchList[A] {
    def fold[R](z: R)(ag: A => R => R): R = z
  }
}
