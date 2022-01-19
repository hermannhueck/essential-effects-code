package com.innerproduct.ee.ch08testing

// see https://typelevel.org/cats-effect/docs/core/test-runtime

import cats.effect._
import java.util.concurrent.TimeoutException
import scala.concurrent.duration._
import cats.effect.testkit.TestControl
import munit.CatsEffectSuite

class TestControlExamples extends CatsEffectSuite {

  test("IO.sleep") {
    // tag::sleep[]
    val timeoutError = new TimeoutException
    val timeout      = IO.sleep(10.seconds) *> IO.raiseError[Int](timeoutError) // <1>

    TestControl.execute(timeout) flatMap { control =>
      for {
        _ <- control.results.assertEquals(None)
        _ <- control.tick
        // Not yet
        _ <- control.advanceAndTick(5.seconds)
        _ <- control.results.assertEquals(None)
        // Good to go:
        _ <- control.advanceAndTick(5.seconds)
        // end::sleep[]
        _ <- control.results.assertEquals(Some(Outcome.errored[cats.Id, Throwable, Int](timeoutError)))
      } yield ()
    }
  }

  test("IO.race") {

    val t1   = IO.sleep(100.millis)
    val t2   = IO.sleep(200.millis)
    val race = IO.race(t1, t2)

    TestControl.execute(race) flatMap { control =>
      for {
        _ <- control.results.assertEquals(None)
        _ <- control.tick
        // Good to go:
        _ <- control.advanceAndTick(100.millis)
        // end::race[]
        _ <- control.results.assertEquals(Some(Outcome.succeeded[cats.Id, Throwable, Either[Unit, Unit]](Left(()))))
      } yield ()
    }
  }
}
