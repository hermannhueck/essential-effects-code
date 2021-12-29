package com.innerproduct.ee.ch02io

import cats.effect._
import scala.concurrent.duration._

object TickingClock extends IOApp {

  def run(args: List[String]): IO[ExitCode] =
    tickingClock.as(ExitCode.Success)

  val tickingClock: IO[Unit] = // <1>
    for {
      _ <- IO(System.currentTimeMillis)
      _ <- IO.sleep(1.second)
      _ <- tickingClock
    } yield ()
}
