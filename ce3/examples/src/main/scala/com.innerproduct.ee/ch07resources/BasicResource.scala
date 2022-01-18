package com.innerproduct.ee.ch07resources

import cats.effect._
import com.innerproduct.ee.debug._

object BasicResource extends IOApp.Simple {

  val stringResource: Resource[IO, String] = // <1>
    Resource.make(
      IO("> acquiring stringResource").debug() *> IO("String")
    )(_ => IO("< releasing stringResource").debug().void)

  val run: IO[Unit] =
    stringResource
      .use { s => // <2>
        IO(s"$s is so cool!").debug()
      }.void
}
