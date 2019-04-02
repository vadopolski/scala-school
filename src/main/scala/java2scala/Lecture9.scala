package java2scala

import cats.Eval

trait ChurchList[A] { self =>
  def fold[R](z: Eval[R])(ag: A => Eval[R] => Eval[R]): Eval[R]

  def ::(x: A): ChurchList[A] = new ChurchList[A] {
    def fold[R](z: Eval[R])(ag: A => Eval[R] => Eval[R]): Eval[R] =
      Eval.defer(ag(x)(self.fold(z)(ag)))
  }

  def map[B](f: A => B): ChurchList[B] =
    new ChurchList[B] {
      def fold[R](z: Eval[R])(ag: B => Eval[R] => Eval[R]): Eval[R] =
        self.fold(z)(a => ag(f(a)))
    }

  def filter(p: A => Boolean): ChurchList[A] = new ChurchList[A] {
    def fold[R](z: Eval[R])(ag: A => Eval[R] => Eval[R]): Eval[R] =
      self.fold(z)(a => if (p(a)) ag(a) else identity)
  }

  def flatMap[B](f: A => ChurchList[B]): ChurchList[B] = new ChurchList[B] {
    def fold[R](z: Eval[R])(ag: B => Eval[R] => Eval[R]): Eval[R] =
      self.fold(z)(a => r => f(a).fold(r)(ag))
  }

  def ++(that: ChurchList[A]): ChurchList[A] = new ChurchList[A] {
    def fold[R](z: Eval[R])(ag: A => Eval[R] => Eval[R]): Eval[R] = self.fold(that.fold(z)(ag))(ag)
  }

  def headOption: Option[A] = fold[Option[A]](Eval.now(None))(a => b => Eval.now(Some(a))).value

  def toList: List[A] = fold[List[A]](Eval.now(Nil))(e => ll => ll.map(e :: _)).value
}

object ChurchList {
  def apply[A](xs: A*): ChurchList[A] = new ChurchList[A] {
    def fold[R](z: Eval[R])(ag: A => Eval[R] => Eval[R]): Eval[R] = xs.foldRight(z)((a, lb) => ag(a)(lb))
  }

  def empty[A]: ChurchList[A] = new ChurchList[A] {
    def fold[R](z: Eval[R])(ag: A => Eval[R] => Eval[R]): Eval[R] = z
  }
}

object Lecture9 extends App {
  val list = List.range(1, 10000).foldRight(ChurchList.empty[Int])(_ :: _)

  println(list.fold(Eval.now(0))(x => y => y.map(_ + x)).value)
}
