package com.innerproduct.ee.ch04concurrent

import cats.effect._
import com.innerproduct.ee.debug._

object Start extends IOApp.Simple {

  val task: IO[String] =
    IO("task").debug() // <2>

  val run: IO[Unit] =
    for {
      _ <- task.start // <1>
      _ <- IO("task was started").debug() // <2>
    } yield ()
}
