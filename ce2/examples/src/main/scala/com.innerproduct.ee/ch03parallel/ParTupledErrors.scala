package com.innerproduct.ee.ch03parallel

import cats.effect._
import cats.implicits._
import com.innerproduct.ee.debug._

object ParTupledErrors extends IOApp {
  def run(args: List[String]): IO[ExitCode] =
    e1.attempt.debug() *> // <1>
      IO("---").debug() *>
      e2.attempt.debug() *>
      IO("---").debug() *>
      e3.attempt.debug() *>
      IO.pure(ExitCode.Success)

  val ok  = IO("hi").debug()
  val ko1 = IO.raiseError[String](new RuntimeException("oh!")).debug()
  val ko2 = IO.raiseError[String](new RuntimeException("noes!")).debug()

  val e1 = (ok, ko1).parTupled.void
  val e2 = (ko1, ok).parTupled.void
  val e3 = (ko1, ko2).parTupled.void
}
