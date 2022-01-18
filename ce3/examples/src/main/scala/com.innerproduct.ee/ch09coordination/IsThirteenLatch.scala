package com.innerproduct.ee.ch09coordination

import cats.effect._
import cats.implicits._
import com.innerproduct.ee.debug._
import scala.concurrent.duration._

object IsThirteenLatch extends IOApp.Simple {

  val run: IO[Unit] =
    for {
      latch <- CountdownLatch(13)
      _ <- (beeper(latch), tickingClock(latch)).parTupled
    } yield ()

  def beeper(latch: CountdownLatch) =
    for {
      _ <- latch.await
      _ <- IO("BEEP!").debug()
    } yield ()

  def tickingClock(latch: CountdownLatch): IO[Unit] =
    for {
      _ <- IO.sleep(1.second)
      _ <- IO(System.currentTimeMillis).debug()
      _ <- latch.decrement
      _ <- tickingClock(latch)
    } yield ()
}
