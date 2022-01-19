package com.innerproduct.ee.ch05contexts

import cats.effect._
import com.innerproduct.ee.debug._

object Blocking extends IOApp.Simple {

  val run: IO[Unit] =
    withBlocker

  def withBlocker: IO[Unit] =
    for {
      _ <- IO("on default").debug()
      _ <- IO.blocking("on blocker")
             .debug() // <2> // debug runs on io-compute-X thread, as IO.blocking immediately shifts back.
    } yield ()
}
