package java2scala.homeworks

sealed trait LinkedMap[K, V] {
  def isEmpty: Boolean = ???

  def contains(key: K): Boolean = ???

  def apply(key: K): Option[V] = ???

  def update(key: K, value: V): LinkedMap[K, V] = ???

  def ++(other: LinkedMap[K, V]): LinkedMap[K, V] = ???

  def mapValues[W](f: V => W): LinkedMap[K, W] = ???

  def mapWithKey[W](f: (K, V) => W): LinkedMap[K, W] = ???

  def delete(key: K): LinkedMap[K, V] = ???

  def foreach(kv: ((K, V)) => Unit): Unit = ???

  override def toString: String = ???
}

object LinkedMap {
  def apply[K, V](kvs: (K, V)*): LinkedMap[K, V] = ???

  final case class Cons[K, V](key: K, value: V, tail: LinkedMap[K, V]) extends LinkedMap[K, V]
  final case class Empty[K, V]()                                       extends LinkedMap[K, V]
}
