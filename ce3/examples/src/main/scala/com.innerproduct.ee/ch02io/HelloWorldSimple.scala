package com.innerproduct.ee.ch02io

import cats.effect._

object HelloWorldSimple extends IOApp.Simple {

  val run: IO[Unit] =
    IO(println("Hello world!"))
}
