package com.innerproduct.ee.ch05contexts

import cats.effect._
import com.innerproduct.ee.debug._

object Shifting extends IOApp.Simple {

  val run: IO[Unit] =
    for {
      _ <- IO("one").debug()
      _ <- IO.cede
      _ <- IO("two").debug()
      _ <- IO.cede
      _ <- IO("three").debug()
    } yield ()
}
