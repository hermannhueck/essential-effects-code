package com.innerproduct.ee.ch07resources

import cats.effect._
import cats.implicits._

object ResourceApp extends IOApp.Simple {

  @annotation.nowarn("msg=dead code")
  val resourceA: Resource[IO, DependencyA] = ???
  @annotation.nowarn("msg=dead code")
  val resourceB: Resource[IO, DependencyB] = ???
  @annotation.nowarn("msg=dead code")
  val resourceC: Resource[IO, DependencyC] = ???

  val resources: Resource[IO, (DependencyA, DependencyB, DependencyC)] = // <1>
    (resourceA, resourceB, resourceC).tupled

  def applicationLogic( // <2>
      a: DependencyA,
      b: DependencyB,
      c: DependencyC
  ): IO[ExitCode] =
    ???

  val run: IO[Unit] =
    resources // <1>
      .use { // <3>
        case (a, b, c) =>
          applicationLogic(a, b, c) // <2>
      }
      .void
}

trait DependencyA
trait DependencyB
trait DependencyC
