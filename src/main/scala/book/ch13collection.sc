val coll = Seq(1, 4, 6, 8 ,9, 9)
val set = coll.toSet
coll.head
set.head
val buffer = coll.toBuffer
buffer(1)

coll == buffer
coll sameElements set
Seq(1,2,3) == Set(1,2,3)
Seq(1,2,3) sameElements  Set(1,2,3)


def digits(n: Int): Set[Int] =
  if (n < 0) digits(-n)
  else if (n < 10) Set(n)
  else digits(n / 10) + (n % 10)

digits(15)

