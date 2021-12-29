package com.innerproduct.ee.ch01effects

case class MyIO_0[A](unsafeRun: () => A) // <1>

object MyIO_0 {
  def putStr(s: => String): MyIO_0[Unit] =
    MyIO_0(() => println(s)) // <2>
}

object Printing_0 extends App { // <3>
  val hello = MyIO_0.putStr("hello!")

  hello.unsafeRun()
}
