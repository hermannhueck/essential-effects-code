package com.innerproduct.ee.ch07resources

import cats.effect._
import com.innerproduct.ee.debug._

object BasicResourceFailure extends IOApp.Simple {

  val stringResource: Resource[IO, String] =
    Resource.make(
      IO("> acquiring stringResource").debug() *> IO("String")
    )(_ => IO("< releasing stringResource").debug().void)

  val run: IO[Unit] =
    stringResource
      .use(_ => IO.raiseError(new RuntimeException("oh noes!"))) // <1>
      .attempt
      .debug()
      .void
}
