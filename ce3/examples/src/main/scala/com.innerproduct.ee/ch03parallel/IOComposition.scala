package com.innerproduct.ee.ch03parallel

import cats.effect._
import cats.implicits._

object IOComposition extends App {
  val hello = IO(println(s"[${Thread.currentThread.getName}] Hello")) // <1>
  val world = IO(println(s"[${Thread.currentThread.getName}] World"))

  val hw1: IO[Unit] =
    for {
      _ <- hello
      _ <- world
    } yield ()

  val hw2: IO[Unit] =
    (hello, world).mapN((_, _) => ())

  import cats.effect.unsafe.implicits.global // implicit IORuntime

  hw1.unsafeRunSync() // <2>
  hw2.unsafeRunSync() // <3>
}
