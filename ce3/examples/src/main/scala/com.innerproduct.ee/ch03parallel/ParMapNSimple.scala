package com.innerproduct.ee.ch03parallel

import cats.effect._
import cats.implicits._
import com.innerproduct.ee.debug._

object ParMapNSimple extends IOApp.Simple {

  val hello = IO("hello").debug() // <1>
  val world = IO("world").debug() // <1>

  val par =
    (hello, world)
      .parMapN((h, w) => s"$h $w") // <2>
      .debug()                     // <3>

  val run: IO[Unit] =
    par.void
}
