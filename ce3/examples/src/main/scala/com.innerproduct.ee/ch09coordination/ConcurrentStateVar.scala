package com.innerproduct.ee.ch09coordination

import cats.effect._
import cats.implicits._
import com.innerproduct.ee.debug._
import scala.concurrent.duration._

object ConcurrentStateVar extends IOApp.Simple {

  var ticks: Long = 0L // <2>

  val tickingClock: IO[Unit] =
    for {
      _ <- IO.sleep(1.second)
      _ <- IO(System.currentTimeMillis).debug()
      _  = (ticks = ticks + 1) // <3>
      _ <- tickingClock
    } yield ()

  val printTicks: IO[Unit] =
    for {
      _ <- IO.sleep(5.seconds)
      _ <- IO(s"TICKS: $ticks").debug().void // <4>
      _ <- printTicks
    } yield ()

  val run: IO[Unit] =
    for {
      _ <- (tickingClock, printTicks).parTupled // <1>
    } yield ()
}
