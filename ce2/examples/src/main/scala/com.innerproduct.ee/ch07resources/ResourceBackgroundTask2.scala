package com.innerproduct.ee.ch07resources

import cats.effect._
import cats.implicits._
import com.innerproduct.ee.debug._
import scala.concurrent.duration._

object ResourceBackgroundTask2 extends IOApp {
  def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- backgroundTask.use { _ =>
             IO.sleep(1000.millis) *> IO("backgroundTask is so cool!").debug() // <1>
           }
      _ <- IO("done!").debug()
    } yield ExitCode.Success

  val backgroundTask: Resource[IO, Unit] = {
    val loop =
      (IO("looping...").debug() *> IO.sleep(100.millis)).foreverM // <2>

    loop.background.void
  }
}
