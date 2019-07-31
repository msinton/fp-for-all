println("--- map2 exercises ---")

import $ivy.`org.typelevel::cats-core:2.0.0-M4`
import cats.implicits._

/*
Ex 1. Combine options with map2

given a name and an age return:

    Some("<name> is <age> years old")

cats gives Option[A] the Typeclass Applicative, with map2:
    def map2[B,C](that: Option[B])(f: (A, B) => C)
 */
val name = Option("Bob")
val age = Option(44)

val result_1 = name.map2(age)((n, a) => s"My name is $n $a")
println(result_1)

/*
Ex 1.b  do the same, but this time we don't know age
 */

val age2 = Option.empty[Int]

val result_1_b = name.map2(age2)((n, a) => s"My name is $n $a")
println(result_1_b)

val result_2 = ???
println(result_2)

val result_3 = ???
println(result_3)
