package com.innerproduct.ee.control

import cats.effect._
import com.innerproduct.ee.debug._

object Cancel extends IOApp {

  def run(args: List[String]): IO[ExitCode] =
    for {
      fiber <- task.start // <2>
      _ <- IO("pre-cancel").debug
      // <3>
      _ <- IO("canceled").debug
    } yield ExitCode.Success

  val task: IO[Nothing] =
    IO("task").debug *>
      IO.never // <1>
}
