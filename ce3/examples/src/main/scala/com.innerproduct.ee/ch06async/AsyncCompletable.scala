package com.innerproduct.ee.ch06async

import cats.effect._
import com.innerproduct.ee.debug._
import java.util.concurrent.CompletableFuture
import scala.jdk.FunctionConverters._

object AsyncCompletable extends IOApp.Simple {

  def cf(): CompletableFuture[String] =
    CompletableFuture.completedFuture("woo!")

  def fromCF[A](iocfa: IO[CompletableFuture[A]]): IO[A] =
    iocfa.flatMap { cfa =>
      IO.async_ { cb =>
        val handler: (A, Throwable) => Unit = {
          case (a, null) => cb(Right(a))
          case (null, t) => cb(Left(t))
          case (a, t) =>
            sys.error(s"CompletableFuture handler should always have one null, got: $a, $t")
        }

        cfa.handle[Unit](handler.asJavaBiFunction)

        ()
      }
    }

  val effect: IO[String] =
    fromCF(IO(cf()))

  val run: IO[Unit] =
    effect.debug().void
}
