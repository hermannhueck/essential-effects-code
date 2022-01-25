package jobscheduler

import cats.effect.IO
// import cats.effect.ExitCase
import cats.effect.concurrent.Ref
import cats.effect.ContextShift

case class Reactor(stateRef: Ref[IO, JobScheduler.State]) /* (implicit cs: ContextShift[IO]) */ {

  // def whenAwake(onStart: Job.Id => IO[Unit], onComplete: (Job.Id, ExitCase[Throwable]) => IO[Unit]): IO[Unit]

  // def startNextJob: IO[Option[Job.Running]] =
  //   for {
  //     job     <- stateRef.modify(_.dequeue)
  //     running <- job.traverse(startJob)
  //   } yield running
}

object Reactor {

  def apply(stateRef: Ref[IO, JobScheduler.State])(implicit cs: ContextShift[IO]): Reactor = ???
  // new Reactor {
  //   def whenAwake(onStart: Job.Id => IO[Unit], onComplete: (Job.Id, ExitCase[Throwable]) => IO[Unit]): IO[Unit] = {
  //     startNextJob.iterateUntil(_.isEmpty).void
  //   }

  //   def startJob(scheduled: Job.Scheduled): IO[Job.Running] = for {
  //     running <- scheduled.start
  //     _       <- stateRef.update(_.running(running))
  //     _       <- registerOnComplete(running)
  //     _       <- onStart(running.id).attempt
  //   } yield running

  //   def registerOnComplete(job: Job.Running) = job
  //     .await
  //     .flatMap(jobCompleted)
  //     .start

  //   def jobCompleted(job: Job.Completed): IO[Unit] = stateRef
  //     .update(_.onComplete(job))
  //     .flatTap(_ => onComplete(job.id, job.exitCase).attempt)
  // }
}
