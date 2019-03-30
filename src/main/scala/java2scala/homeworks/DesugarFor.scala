package java2scala.homeworks
import scala.reflect.runtime._
import scala.tools.reflect._

object DesugarFor extends App{
  val toolbox = currentMirror.mkToolBox()

  val tree = toolbox.parse(
    """
      |for((x, i) <- xs.zipWithIndex) yield x + i
    """.stripMargin
  )

  println(tree)
}
