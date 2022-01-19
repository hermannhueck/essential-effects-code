package com.innerproduct.ee.ch04concurrent

import cats.effect._
import com.innerproduct.ee.debug._
import scala.concurrent.duration._

object JoinAfterStart extends IOApp.Simple {

  val task: IO[String] =
    IO.sleep(2.seconds) *> IO("task").debug() // <1>

  val run: IO[Unit] =
    for {
      fiber <- task.start
      _     <- IO("pre-join").debug()
      _     <- fiber.join.debug() // <2>
      _     <- IO("post-join").debug()
    } yield ()
}
