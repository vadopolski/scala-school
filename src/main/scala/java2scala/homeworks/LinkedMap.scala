package java2scala.homeworks

sealed trait LinkedMap[K, V] extends Traversable[(K, V)] {

  /** должен вернуть `false` если коллекция содержит хотя бы один элемент */
  override def isEmpty: Boolean = !this.toMap.nonEmpty

  /** должен вернуть `true` если коллекция содержит ключ `key` */
  def contains(key: K): Boolean = this.toMap.get(key) match {
    case Some(res) => true
    case _ => false
  }

  /** возвращает Some со значением значения, если коллекция содержит ключ `key`
    * и None если не содержит */
  def apply(key: K): Option[V] = this.toMap.get(key) match {
    case Some(res) => Some(res)
    case _ => None
  }

  /** возвращает новый LinkedMap[K, V],
    * в котором добавлено или изменено значение для ключа `key` на `value` */
  def update(key: K, value: V): LinkedMap[K, V]  =new LinkedMap[K, V] {} /*{
    val map: LinkedMap[K, V] = new LinkedMap[K, V] {}
    for ((k, v) <- this.toList) {
      map.update(k, v)
    }
    map.update(key, value)
    return map
  }*/

  /** возвращает новый LinkedMap[K, V]
    * состоящий из тех же позиций, но в обратном порядке */
  def reverse: LinkedMap[K, V] = {
    val map: LinkedMap[K, V] = new LinkedMap[K, V] {}
    for (k <- this.toMap.keys) {
      map ++ LinkedMap(k -> this.toMap.get(k))
    }
    return map
  }

  /** создаёт новый LinkedMap, состоящий из элементов `this` и `other`
    * если какой-то ключ встречается в обеих коллекциях,
    * может быть выбрано любое значение */
  def ++(other: LinkedMap[K, V]): LinkedMap[K, V] = {
    val map: LinkedMap[K, V] = new LinkedMap[K, V] {}
    for ((k, v) <- this.toMap) {
      map.update(k, v)
    }
    for ((k, v) <- other.toMap) {
      map.update(k, v)
    }
    return map
  }

  /** создаёт новый LinkedMap , где ко всем значениям применена заданная функция */
  def mapValues[W](f: V => W): LinkedMap[K, W] = {
    val map: LinkedMap[K, W] = new LinkedMap[K, W] {}
    for ((k, v) <- this.toMap) {
      map.update(k, f(v))
    }
    return map
  }

  /** создаёт новый LinkedMap , где ко всем значениям применена заданная функция,
    * учитывающая ключ */
  def mapWithKey[W](f: (K, V) => W): LinkedMap[K, W] = {
    val map: LinkedMap[K, W] = new LinkedMap[K, W] {}
    for ((k, v) <- this.toMap) {
      map.update(k, f(k, v))
    }
    return map
  }

  /** конструирует новый LinkedMap, содеоржащий все записи текущего, кроме заданного ключа */
  def delete(key: K): LinkedMap[K, V] = {
    val map: LinkedMap[K, V] = new LinkedMap[K, V] {}
    for ((k, v) <- this.toMap;
         if k != key) {
      map.update(k, v)
    }
    return map
  }

  /** применяет действие `action` с побочным эффектом ко всем элементам коллекции */
  def foreach[U](action: ((K, V)) => U): Unit = {}  /*{
   for((k,v)<-this.toMap){
      action(k,v)
    }
  } */
}


object LinkedMap {

  /** конструирует новый `LinkedMap` на основании приведённых элементов
    * каждый ключ должен присутствовать в результате только один раз
    * если в исходных данныхх ключ встречается несколько раз, может быть
    * выбрано любое из значений
    */
  def apply[K, V](kvs: (K, V)*): LinkedMap[K, V] = {
    val map:LinkedMap[K, V] = new LinkedMap[K,V]{}
    for((key,value)<-kvs) {
      map.update(key,value)
    }
    return map
  }

  final case class Cons[K, V](key: K, value: V, rest: LinkedMap[K, V]) extends LinkedMap[K, V]
  final case class Empty[K, V]()                                       extends LinkedMap[K, V]
}
