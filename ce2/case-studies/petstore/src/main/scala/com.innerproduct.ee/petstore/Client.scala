package com.innerproduct.ee.petstore

import cats.effect._
import com.innerproduct.ee.debug._
import org.http4s.implicits._
import org.http4s.blaze.client.BlazeClientBuilder
import scala.concurrent.ExecutionContext

object Client extends IOApp {
  val scruffles = Pet("Scruffles", "dog")

  def run(args: List[String]): IO[ExitCode] =
    pets[IO].use { pets =>
      for {
        id  <- pets.give(scruffles)
        pet <- pets.find(id)
        _   <- IO(pet == Some(scruffles)).debug()
      } yield ExitCode.Success
    }

  def pets[F[_]: ConcurrentEffect]: Resource[F, PetService[F]] =
    for {
      client <- BlazeClientBuilder(ExecutionContext.global).resource
    } yield ClientResources.pets(client, uri"http://localhost:8080")
}
