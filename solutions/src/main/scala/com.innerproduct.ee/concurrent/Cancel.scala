package com.innerproduct.ee.concurrent

import cats.effect._
import cats.effect.implicits._
import com.innerproduct.ee.debug._

object Cancel extends IOApp {

  def run(args: List[String]): IO[ExitCode] =
    for {
      fiber <- 
        task
          .onCancel(IO("i was cancelled").debug().void) // <1>
          .start
      _ <- IO("pre-cancel").debug()
      _ <- fiber.cancel // <2>
      _ <- IO("canceled").debug()
    } yield ExitCode.Success

  val task: IO[String] =
    IO("task").debug() *>
      IO.never
}
