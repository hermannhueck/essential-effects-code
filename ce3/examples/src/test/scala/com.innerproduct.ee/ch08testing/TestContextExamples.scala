package com.innerproduct.ee.ch08testing

// see https://typelevel.org/cats-effect/docs/core/test-runtime

import cats.effect._
// import cats.effect.laws.util.TestContext
import cats.effect.kernel.testkit.TestContext
import java.util.concurrent.TimeoutException
import munit.FunSuite
import scala.concurrent.duration._
// import scala.util._

trait EffectTesting {
  val ctx = TestContext()
  // implicit val cs: ContextShift[IO] = ctx.ioContextShift
  // implicit val timer: Timer[IO] = ctx.timer
}

class TestContextExamples extends FunSuite with EffectTesting {
  
  test("IO.sleep") {
    // tag::sleep[]
    val timeoutError = new TimeoutException
    @annotation.nowarn("msg=never used")
    val timeout =
      IO.sleep(10.seconds) *> IO.raiseError[Int](timeoutError) // <1>
    // val f = timeout.unsafeToFuture() // <2>

    // // Not yet
    // ctx.tick(5.seconds) // <3>
    // assertEquals(f.value, None) // <3>

    // // Good to go:
    // ctx.tick(5.seconds) // <4>
    // assertEquals(f.value, Some(Failure(timeoutError))) // <4>
    // // end::sleep[]
  }

  test("IO.race") {
    @annotation.nowarn("msg=never used")
    val t1 = IO.sleep(100.millis)
    @annotation.nowarn("msg=never used")
    val t2 = IO.sleep(200.millis)
    // val f = IO.race(t1, t2).unsafeToFuture()

    // ctx.tick()
    // assertEquals(f.value, None)

    // ctx.tick(100.millis)
    // assertEquals(f.value, Some(Success(Left(()))))
  }
}
