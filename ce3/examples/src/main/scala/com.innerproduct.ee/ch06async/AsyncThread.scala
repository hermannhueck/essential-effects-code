package com.innerproduct.ee.ch06async

import cats.effect._
import com.innerproduct.ee.debug._
import scala.concurrent.ExecutionContext

object AsyncThread extends IOApp.Simple {

  val effect: IO[String] =
    IO.async_ { cb =>
      ExecutionContext.global.execute {
        new Runnable {
          // This doesn't run in the global context with CE3 ?????
          def run() = cb(Right("on global context"))
        }
      }
    }

  val run: IO[Unit] =
    IO("on default context").debug() *> effect.debug() *> IO.unit
}
