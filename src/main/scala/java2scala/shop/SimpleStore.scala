package java2scala.shop
import java.util.UUID

import cats.effect.IO
import io.circe.Decoder

trait SimpleStore[A] {
  def all: IO[List[Product]]

  def getById(id: UUID): IO[Option[Product]]

  def byId(id: UUID): IO[Product]
}


object SimpleStore{
  def fromResource[A : Decoder]
}
