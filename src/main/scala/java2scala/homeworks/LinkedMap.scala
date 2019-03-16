package java2scala.homeworks

import java2scala.homeworks.LinkedMap.{Empty, Cons}


sealed trait LinkedMap[K, V] extends Traversable[(K, V)] {

  /** должен вернуть `false` если коллекция содержит хотя бы один элемент */
  override def isEmpty: Boolean = {
    this match {
      case Empty() => true
      case Cons(_, _, _) => false
    }
  }

  /** должен вернуть `true` если коллекция содержит ключ `key` */
  def contains(key: K): Boolean = {
    this match {
      case Cons(k, _, _) if key == k => true
      case Cons(k, _, t) if key != k => t.contains(key)
      case Empty() => false
    }
  }

  /** возвращает Some со значением значения, если коллекция содержит ключ `key`
    * и None если не содержит */
  def apply(key: K): Option[V] = {
    this match {
      case Cons(k, v, _) if key == k => Some(v)
      case Cons(k, _, t) if key != k => t.apply(key)
      case Empty() => None
    }
  }

  /** возвращает новый LinkedMap[K, V],
    * в котором добавлено или изменено значение для ключа `key` на `value` */
  def update(key: K, value: V): LinkedMap[K, V] = {
    this match {
      case Cons(k, _, tail) if k == key => Cons(k, value, tail.update(key, value))
      case Cons(k, v, tail) if k != key => Cons(k, v, tail.update(key, value))
      case Empty() => Cons(key, value, Empty())
    }
  }


  /** возвращает новый LinkedMap[K, V]
    * состоящий из тех же позиций, но в обратном порядке */
  def reverse: LinkedMap[K, V] = {
    foldLeft(LinkedMap[K, V]()) { (acc, entry) =>
      val (key, head) = entry
      Cons(key, head, acc)
    }
  }

  /** создаёт новый LinkedMap, состоящий из элементов `this` и `other`
    * если какой-то ключ встречается в обеих коллекциях,
    * может быть выбрано любое значение */
  def ++(other: LinkedMap[K, V]): LinkedMap[K, V] = {
    this match {
      case Cons(k, v, t) => Cons(k, v, t.++(other))
      case Empty() => other
    }
  }

  /** создаёт новый LinkedMap , где ко всем значениям применена заданная функция */
  def mapValues[W](f: V => W): LinkedMap[K, W] = ???

  /** создаёт новый LinkedMap , где ко всем значениям применена заданная функция,
    * учитывающая ключ */
  def mapWithKey[W](f: (K, V) => W): LinkedMap[K, W] = ???

  /** конструирует новый LinkedMap, содеоржащий все записи текущего, кроме заданного ключа */
  def delete(key: K): LinkedMap[K, V] = this match {
    case Cons(k, _, t) if key == k => t.delete(key)
    case Cons(k, v, t) if key != k => Cons(k, v, t.delete(key))
    case Empty() => Empty()
  }

  /** применяет действие `action` с побочным эффектом ко всем элементам коллекции */
  def foreach[U](action: ((K, V)) => U): Unit = {
    this match {
      case Cons(key, value, tail) => {
        action((key, value))
        tail.foreach(action)
      }
      case Empty() => None
    }
  }
}

object LinkedMap {

  /** конструирует новый `LinkedMap` на основании приведённых элементов
    * каждый ключ должен присутствовать в результате только один раз
    * если в исходных данныхх ключ встречается несколько раз, может быть
    * выбрано любое из значений
    */
  def apply[K, V](kvs: (K, V)*): LinkedMap[K, V] = {
    if (kvs.isEmpty) {
      Empty[K, V]
    } else {
      val (key, value) = kvs.head
      Cons(key, value, apply(kvs.tail: _*))
    }
  }

  final case class Cons[K, V](key: K, value: V, rest: LinkedMap[K, V]) extends LinkedMap[K, V]

  final case class Empty[K, V]() extends LinkedMap[K, V]

  def main(args: Array[String]): Unit = {
    val first = LinkedMap(1 -> "Test1", 2 -> "Test2")

    first.foreach(print)
    val deleted = first.delete(2)
    println
    deleted.foreach(print)
    println
    val sum = deleted ++ first
    sum.foreach(print)
  }
}