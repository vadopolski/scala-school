package java2scala

package object tagless {
  type ProdF[A] = Product => A
  type ShowF[A] = String
}
