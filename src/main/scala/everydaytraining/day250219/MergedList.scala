package everydaytraining.day250219

class ListNode(var _x: Int = 0) {
  var next: ListNode = null
  var x: Int         = _x
}

object MergedList {
  def go(l1: ListNode, l2: ListNode, result: ListNode): ListNode = {
    if(l1 == null && l2 == null) return null
    if(l1 == null || l2 == null) return if(l2 == null) return l1 else l2

    if (l1.x <= l2.x) {
      result.x = l1.x
      if (l1.next != null) result.next = go(l1.next, l2, new ListNode())
      else result.next = l2
    } else {
      result.x = l2.x
      if (l2.next != null) result.next = go(l1, l2.next, new ListNode())
      else result.next = l1
    }
    result
  }

  def mergeTwoLists(l1: ListNode, l2: ListNode): ListNode = go(l1, l2, new ListNode())

def main(args: Array[String]): Unit = {
    val listNode1: ListNode = new ListNode(1)
    listNode1.next = new ListNode(3)
    listNode1.next.next = new ListNode(5)
    listNode1.next.next.next = new ListNode(7)
    listNode1.next.next.next.next = new ListNode(9)

    val listNode2: ListNode = new ListNode(2)
    listNode2.next = new ListNode(4)
    listNode2.next.next = new ListNode(6)
    listNode2.next.next.next = new ListNode(8)
    listNode2.next.next.next.next = new ListNode(10)

//    val result = mergeTwoLists(listNode1, listNode2)
    val result = mergeTwoLists(null, null)

    println("Test")
  }

}
