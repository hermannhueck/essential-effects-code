package com.innerproduct.ee.ch09coordination

import cats.effect._
import cats.effect.std.CountDownLatch
import cats.implicits._
import com.innerproduct.ee.debug._
import scala.concurrent.duration._

object IsThirteenStdLatch2 extends IOApp.Simple {

  val run: IO[Unit] =
    for {
      latch <- CountDownLatch[IO](13)
      _     <- IO.race(beeper(latch), tickingClock(latch))
    } yield ()

  def beeper(latch: CountDownLatch[IO]) =
    for {
      _ <- latch.await
      _ <- IO("BEEP!").debug()
    } yield ()

  def tickingClock(latch: CountDownLatch[IO]): IO[Unit] =
    for {
      _ <- IO.sleep(1.second)
      _ <- IO(System.currentTimeMillis).debug()
      _ <- latch.release
      _ <- tickingClock(latch)
    } yield ()
}
