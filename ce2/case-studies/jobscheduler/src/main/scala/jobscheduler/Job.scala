package jobscheduler

import java.util.UUID
import cats.effect.IO
import cats.effect.Fiber
import cats.effect.concurrent.Deferred
import cats.effect.ExitCase
import cats.effect.ContextShift

sealed trait Job

object Job {

  case class Id(value: UUID) extends AnyVal

  case class Scheduled(id: Id, task: IO[_]) extends Job {
    def start(implicit cs: ContextShift[IO]): IO[Job.Running] =
      for {
        exitCase <- Deferred[IO, ExitCase[Throwable]]
        fiber    <- task
                      .void
                      .guaranteeCase(exitCase.complete)
                      .start
      } yield Job.Running(id, fiber, exitCase)
  }

  case class Running(id: Id, fiber: Fiber[IO, Unit], exitCase: Deferred[IO, ExitCase[Throwable]]) extends Job {
    val await: IO[Completed] = exitCase.get.map(Completed(id, _))
  }

  case class Completed(id: Id, exitCase: ExitCase[Throwable]) extends Job

  def create[A](task: IO[A]): IO[Scheduled] =
    IO(Id(UUID.randomUUID())).map(Scheduled(_, task))
}
