package jobscheduler

import cats.effect.IO
import cats.data.Chain
import cats.effect.concurrent.Ref

trait JobScheduler {

  // import Job._
  import JobScheduler._

  def state: State

  def schedule(task: IO[_]): IO[Job.Id]

}

object JobScheduler {

  case class State(
      maxRunning: Int,
      scheduled: Chain[Job.Scheduled] = Chain.empty,
      running: Map[Job.Id, Job.Running] = Map.empty,
      completed: Chain[Job.Completed] = Chain.empty
  ) {
    def enqueue(job: Job.Scheduled): State =
      copy(scheduled = scheduled :+ job)

    def dequeue: (State, Option[Job.Scheduled]) =
      if (running.size >= maxRunning) this -> None
      else
        scheduled
          .uncons
          .map { case (head, tail) =>
            copy(scheduled = tail) -> Some(head)
          }
          .getOrElse(this -> None)

    def running(job: Job.Running): State =
      copy(running = running + (job.id -> job))
  }

  def scheduler(stateRef: Ref[IO, State]): JobScheduler = new JobScheduler {

    def state = State(10)

    def schedule(task: IO[_]): IO[Job.Id] = for {
      job <- Job.create(task)
      _   <- stateRef.update(_.enqueue(job))
    } yield job.id
  }
}
