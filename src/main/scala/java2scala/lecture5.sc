import scala.collection.mutable._
import scala.util.Random

val lls = List(List(1, 2), List(3, 4, 5), List(2))
lls.flatten
//lls.foldLeft(List[Int]())(_ ::: _)
val rand = new Random(123)
val x = List.fill(10, 10)(rand.nextInt)
val y = List.tabulate(10, 10)((i, j) => i * j)
List(1, 2, 3).flatMap(i => Iterator.fill(i)(i))

List(1, 2, 3) ::: List(2, 3)
List(1, 2, 3) ++ List(2, 3)
List(1, 2, 3) ++ Vector(2, 3) ++ Iterator(3, 4)


val u = Vector(1, 2, 3)

2 +: u




List(List(List(List(1)))).flatten.flatten

//
//val u = List(1, 2, 3, 4)
//List("asdf", "asd")
//List()
//List(1, "dwfsdfsf")
//List("sdfsdf", (1, 2))
//
//class X
//val List(x, y, _*) = u
//
//List("sadsdfd", new X)
//
//List((1, 2), (3, 4))
//val s = List((1, 2), (3, "sdf"))
//
//s.head
//
//s.tail
//
//val vec = Vector(1, 2, 3, 4)
//vec.head
//vec.tail
//u.filter(_ % 2 == 0)
//
//u.foldLeft(0)( (acc, elem) => acc + elem)
//u.sum
//
//u.foldLeft("")( (acc, elem) => acc + "," + elem.toString)
//u.foldLeft(("", false)){ case ((acc, seen), elem) => (
//  acc +  (if(seen) "," + elem.toString else ""), true )
//}._1
//u.mkString
//u.mkString(",")
//u.mkString("List(", ",", ")")
//u.scanLeft("")( (acc, elem) => acc + "," + elem.toString)
//
//u.take(2)
//u.drop(2)
//
//u.drop(1)
//
//val empty = List()
//empty.headOption
//u.headOption
//
//empty.drop(1)
//
//u.map(_ * 2)
//u.map(_ * 2).map(_ + 1)
//
//LinearSeq
//IndexedSeq
//
//u(2)
////u(5)
//u.lift(1)
//u.lift(5)
//u.iterator.map(_ * 2).filter( _ > 5).map( _ / 2).toList
//
//u.map(_.toString)
//u.map(x => (x, x.toString))
//
//val pf : PartialFunction[Int, String] = {
//  case 1 => "one"
//  case 2 => "two"
//}
//
//pf.isDefinedAt(1)
//pf.isDefinedAt(5)
//pf.lift(1)
//pf.lift(5)
//
//u.collect(pf)
//u.collect{
//  case 3 => "three"
//}









