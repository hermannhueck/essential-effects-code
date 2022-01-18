package com.innerproduct.ee.ch09coordination

import cats.effect._
// import cats.effect.concurrent.Ref
import cats.implicits._

object RefUpdateImpure extends IOApp.Simple {

  def task(id: Int, ref: Ref[IO, Int]): IO[Unit] =
    ref
      .modify(previous => id -> println(s"$previous->$id")) // <2>
      .replicateA(3) // <3>
      .void

  val run: IO[Unit] =
    for {
      ref <- Ref[IO].of(0)
      _ <- List(1, 2, 3).parTraverse(task(_, ref)) // <1>
    } yield ()
}
