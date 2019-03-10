package java2scala.homeworks

sealed trait LinkedMap[+K, +V] extends Traversable[(K, V)] {
  /** должен вернуть `true` если коллекция содержит ключ `key` */
  def contains[K1 >: K](key: K1): Boolean

//  /** возвращает Some со значением значения, если коллекция содержит ключ `key`
//    * и None если не содержит */
//  def apply(key: K): Option[V] = this.toMap.get(key) match {
//    case Some(res) => Some(res)
//    case _ => None
//  }

  /** возвращает новый LinkedMap[K, V],
    * в котором добавлено или изменено значение для ключа `key` на `value` */
//  def update(key: K, value: V): LinkedMap[K, V] = new LinkedMap[K, V] {} /*{
//    val map: LinkedMap[K, V] = new LinkedMap[K, V] {}
//    for ((k, v) <- this.toList) {
//      map.update(k, v)
//    }
//    map.update(key, value)
//    return map
//  }*/

//  /** возвращает новый LinkedMap[K, V]
//    * состоящий из тех же позиций, но в обратном порядке */
//  def reverse: LinkedMap[K, V] = {
//    val map: LinkedMap[K, V] = new LinkedMap[K, V] {}
//    for (k <- this.toMap.keys) {
//      map ++ LinkedMap(k -> this.toMap.get(k))
//    }
//    map
//  }

//  /** создаёт новый LinkedMap, состоящий из элементов `this` и `other`
//    * если какой-то ключ встречается в обеих коллекциях,
//    * может быть выбрано любое значение*/
//  def ++(other: LinkedMap[K, V]): LinkedMap[K, V] = {
//    val map: LinkedMap[K, V] = new LinkedMap[K, V] {}
//    for ((k, v) <- this.toMap) {
//      map.update(k, v)
//    }
//    for ((k, v) <- other.toMap) {
//      map.update(k, v)
//    }
//    map
//  }

//  /** создаёт новый LinkedMap , где ко всем значениям применена заданная функция */
//  def mapValues[W](f: V => W): LinkedMap[K, W] = {
//    val map: LinkedMap[K, W] = new LinkedMap[K, W] {}
//    for ((k, v) <- this.toMap) {
//      map.update(k, f(v))
//    }
//    map
//  }
//
//  /** создаёт новый LinkedMap , где ко всем значениям применена заданная функция,
//    * учитывающая ключ*/
//  def mapWithKey[W](f: (K, V) => W): LinkedMap[K, W] = {
//    val map: LinkedMap[K, W] = new LinkedMap[K, W] {}
//    for ((k, v) <- this.toMap) {
//      map.update(k, f(k, v))
//    }
//    map
//  }

//  /** конструирует новый LinkedMap, содеоржащий все записи текущего, кроме заданного ключа */
//  def delete(key: K): LinkedMap[K, V] = {
//    val map: LinkedMap[K, V] = new LinkedMap[K, V] {}
//    for ((k, v) <- this.toMap
//         if k != key) {
//      map.update(k, v)
//    }
//    map
//  }

  /** применяет действие `action` с побочным эффектом ко всем элементам коллекции */
  def foreach[U](action: ((K, V)) => U): Unit = {}  /*{
   for((k,v)<-this.toMap){
      action(k,v)
    }
  } */
}

object LinkedMap {
  def apply[K, V](kvs: (K, V)*): LinkedMap[K, V] = {
    if(kvs.isEmpty){
      Empty
    } else {
      Cons(kvs.head _1, kvs.head _2, apply(kvs.tail: _*))
    }
  }

  final case class Cons[K, V](key: K, value: V, rest: LinkedMap[K, V]) extends LinkedMap[K, V] {
    override def isEmpty = false
    override def contains[K1 >: K](key: K1) = {
      var these = this
      while (!these.isEmpty){
        if(these.key == key) return true
        these = these.rest
      }
      false
    }
  }

  case object Empty extends LinkedMap[Nothing, Nothing]{
    override def isEmpty = true
    override def contains[K1 >: Nothing](key: K1): Boolean = false
  }

  def main(args: Array[String]): Unit = {
    println("Test")
    val empty = LinkedMap[Int, String]()

    val singleton = Map(1 -> "Test", 2 -> "Test2", 3 -> "Test3")
    singleton.get(1)

    val mainList = List(3, 2, 1)

    println(mainList.contains(4))

    println(singleton.get(1))
  }
}