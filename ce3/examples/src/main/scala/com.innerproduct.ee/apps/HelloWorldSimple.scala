package com.innerproduct.ee.apps

import cats.effect._

object HelloWorldSimple extends IOApp.Simple {

  val run: IO[Unit] =
    for {
      _ <- putStr("hello")
      _ <- putStr("world")
    } yield ()

  def putStr(s: => String): IO[String] =
    IO(s)
}
