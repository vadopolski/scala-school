package contest124.task1

class TreeNode(var _value: Int) {
  var value: Int = _value
  var left: TreeNode = null
  var right: TreeNode = null
}


object Solution {
  var xParents: TreeNode = _
  var yParents: TreeNode = _
  var xDepth = 0
  var yDepth = 0


  def getDepthAndParents(root: TreeNode, x: Int, y: Int, depth: Int, parent: TreeNode): Int = {
    if (root == null)
      return depth

    if (x == root.value) {
      xParents = parent
      xDepth = depth
    } else if (y == root.value) {
      yParents = parent
      yDepth = depth
    }

    getDepthAndParents(root.left, x, y, depth + 1, root)
    getDepthAndParents(root.right, x, y, depth + 1, root)

    depth
  }

  def isCousins(root: TreeNode, x: Int, y: Int): Boolean = {
    getDepthAndParents(root, x, y, 0, null)

    (xDepth == yDepth) && (xParents != yParents)
  }

  def insertLevel(input: Array[Int], i: Int): TreeNode = {
    if (i < input.length) {
      val treeNode = new TreeNode(input(i))
      treeNode.left = insertLevel(input, i * 2 + 1)
      treeNode.right = insertLevel(input, i * 2 + 2)
      treeNode
    } else null
  }

  def main(args: Array[String]) {
    var input: Array[Int] = null
    input = Array(1, 2, 3, 4, 5)
    var root: TreeNode = null
    root = insertLevel(input, 0)
    println(isCousins(root, 3, 4))
    for (x <- input) println(x)
  }
}