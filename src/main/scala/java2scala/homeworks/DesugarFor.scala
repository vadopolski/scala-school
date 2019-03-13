package java2scala.homeworks
import scala.reflect.runtime._
import scala.tools.reflect._

object DesugarFor extends App{
  val toolbox = currentMirror.mkToolBox()

  val xxx = List(1, 2, 3)

  val tree = toolbox.parse(
    """
      |for {
      |   x      <- List(1, 2, 3)
      |   y      <- List.range(0, x)
      |   (z, i) <- List.range(y, x).zipWithIndex
      |} yield result(x, y, z)
    """.stripMargin
  )

  println(tree)
}
