package com.innerproduct.ee.other

import cats.effect._

object NeverFinish extends IOApp {
  def run(args: List[String]): IO[ExitCode] =
    IO(println("You'll never stop me!!!")) *> IO.never *> IO(
      println("None shall read this... ever!")
    ) *> IO.pure(ExitCode.Success)
}
