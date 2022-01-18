package com.innerproduct.ee.ch02io

import cats.effect._
import scala.concurrent.duration._

object TickingClock extends IOApp.Simple {

  val tickingClock: IO[Unit] = // <1>
    for {
      _ <- IO(System.currentTimeMillis)
      _ <- IO.sleep(1.second)
      _ <- tickingClock
    } yield ()

  val run: IO[Unit] = tickingClock
}
