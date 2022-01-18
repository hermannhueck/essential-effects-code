package com.innerproduct.ee.ch06async

import cats.effect._
import com.innerproduct.ee.debug._

object Never extends IOApp.Simple {

  val never: IO[Nothing] =
    IO.async_(_ => ()) // <1>

  val run: IO[Unit] =
    never
      .guarantee(IO("i guess never is now").debug().void)
      .void
}
