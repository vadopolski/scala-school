package java2scala.homeworks.funcs

object ChurchNumerals {

  type Succ[Z] = Z => Z
  type ChNum[Z] = Succ[Z] => Z => Z

  def zero[Z]: ChNum[Z] =
    (_: Succ[Z]) => (z: Z) => z

  def succ[Z] (num: ChNum[Z]): ChNum[Z] =
    (s: Succ[Z]) => (z: Z) => s( num(s)(z) )

  // a couple of church constants
  def one[Z] : ChNum[Z] = succ(zero)
  def two[Z] : ChNum[Z] = succ(one)

  // the addition function
  def add[Z] (a: ChNum[Z]) (b: ChNum[Z]) =
    (s: Succ[Z]) => (z: Z) => a(s)( b(s)(z) )

//  def four[Z] : ChNum[Z] = two.add(two)

  // test
  def church_to_int (num: ChNum[Int]): Int = {
    println("wrwerwer")
    num((x: Int) => x + 1)(0)
  }

  def twoInt: Int = church_to_int(two)

  def main(args: Array[String]): Unit = {
    println(s"2 + 2 = ${twoInt}")
  }
}