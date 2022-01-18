package com.innerproduct.ee.ch02io

import cats.effect._

object HelloWorldSimple2 extends IOApp.Simple {

  val run: IO[Unit] =
    for {
      _ <- putStr("hello")
      _ <- putStr("world")
    } yield ()

  def putStr(s: => String): IO[String] =
    IO(s)
}
