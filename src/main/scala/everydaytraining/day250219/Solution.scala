package everydaytraining.day250219

object Solution {

  def findMinCost(grid: Array[Array[Int]], i: Int, j: Int): Int = {
    if(i == grid.length -1 && j == grid(0).length - 1) return grid(i)(j)

    var min = Integer.MAX_VALUE

    if (i < grid.length -1) min = Math.min(findMinCost(grid, i + 1, j), min)

    if (j < grid(0).length - 1) min = Math.min(findMinCost(grid, i, j + 1), min)

    min += grid(i)(j)
    min
  }

  def minPathSum(grid: Array[Array[Int]]): Int = {
    findMinCost(grid, 0, 0)
  }

  def main(args: Array[String]): Unit = {
    val arr = Array(Array(1, 3, 1), Array(1, 5, 1), Array(4, 2, 1))

    val i = minPathSum(arr)
    print(i)
  }
}