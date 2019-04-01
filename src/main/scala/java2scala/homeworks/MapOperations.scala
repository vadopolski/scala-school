package java2scala.homeworks

import cats.data.Ior

object MapOperations extends App {

  implicit class MapSyntaxExtensions[K, V](val me: Map[K, V]) extends AnyVal {

    /** find items contained in the either maps */
    def union[X](other: Map[K, X]): Map[K, Ior[V, X]] = {
      me.filter(kv => !other.contains(kv._1)).map(kv => (kv._1, Ior.Left(kv._2))) ++
        other.filter(kv => !me.contains(kv._1)).map(kv => (kv._1, Ior.Right(kv._2))) ++
        me.filter(kv => other.contains(kv._1)).map(kv => (kv._1, Ior.Both(kv._2, other(kv._1))))
    }

    /** find items contained in the either maps, merge elements with custom function */
    def unionWith(other: Map[K, V])(f: (K, V, V) => V): Map[K, V] = {
      me.filter(kv => !other.contains(kv._1)).map(kv => (kv._1, kv._2)) ++
        other.filter(kv => !me.contains(kv._1)).map(kv => (kv._1, kv._2)) ++
        me.filter(kv => other.contains(kv._1)).map(kv => (kv._1, f(kv._1, kv._2, other(kv._1))))
    }

    /** find items contained in the either maps, merge elements with custom function */
    def unionWithGen[X, Y](other: Map[K, X])(f: (K, V, X) => Y)(g: (K, V) => Y)(h: (K, X) => Y): Map[K, Y] = {
      me.filter(kv => !other.contains(kv._1)).map(kv => (kv._1, g(kv._1, kv._2))) ++
        other.filter(kv => !me.contains(kv._1)).map(kv => (kv._1, h(kv._1, kv._2))) ++
        me.filter(kv => other.contains(kv._1)).map(kv => (kv._1, f(kv._1, kv._2, other(kv._1))))
    }

    /** find elements contained in both maps */
    def intersect[X](other: Map[K, X]): Map[K, (V, X)] = {
      me.filter(kv => other.contains(kv._1)).map(kv => (kv._1, (kv._2, other(kv._1))))
    }

    /** find elements contained in both maps */
    def intersectWith[X, Y](other: Map[K, X])(f: (K, V, X) => Y): Map[K, Y] = {
      me.filter(kv => other.contains(kv._1)).map(kv => (kv._1, f(kv._1, kv._2, other(kv._1))))
    }

    /** find elements contained in either map */
    def difference[X](other: Map[K, X]): Map[K, Either[V, X]] = {
      me.filter(kv => !other.contains(kv._1)).map(kv => (kv._1, Left(kv._2))) ++
        other.filter(kv => !me.contains(kv._1)).map(kv => (kv._1, Right(kv._2)))
    }

    /** find elements contained in either map, use custom functions to unify types */
    def differenceWith[X, Y](other: Map[K, X])(f: V => Y)(g: X => Y): Map[K, Y] = {
      me.filter(kv => !other.contains(kv._1)).map(kv => (kv._1, f(kv._2))) ++
        other.filter(kv => !me.contains(kv._1)).map(kv => (kv._1, g(kv._2)))
    }
  }
}