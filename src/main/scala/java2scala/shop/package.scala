package java2scala

package object shop {
  type CancelAction  = () => Unit
  type CancelCont[A] = (A => Unit) => CancelAction
}
