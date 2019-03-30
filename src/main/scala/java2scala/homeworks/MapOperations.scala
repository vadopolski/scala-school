package java2scala.homeworks

import cats.data.Ior

object MapOperations {
  implicit class MapSyntaxExtensions[K, V](val me: Map[K, V]) extends AnyVal {

    /** find items contained in the either maps*/
    def union[X](other: Map[K, X]): Map[K, Ior[V, X]] = ???

    /** find items contained in the either maps, merge elements with custom function*/
    def unionWith(other: Map[K, V])(f: (K, V, V) => V): Map[K, V] = ???

    /** find items contained in the either maps, merge elements with custom function*/
    def unionWithGen[X, Y](other: Map[K, X])(f: (K, V, X) => Y)(g: (K, V) => Y)(h: (K, X) => Y): Map[K, Y] = ???

    /** find elements contained in both maps*/
    def intersect[X](other: Map[K, X]): Map[K, (V, X)] = ???

    /** find elements contained in both maps*/
    def intersectWith[X, Y](other: Map[K, X])(f: (K, V, X) => Y): Map[K, Y] = ???

    /** find elements contained in either map*/
    def difference[X](other: Map[K, X]): Map[K, Either[V, X]] = ???

    /** find elements contained in either map, use custom functions to unify types*/
    def differenceWith[X, Y](other: Map[K, X])(f: V => Y)(g: X => Y): Map[K, Y] = ???
  }
}
