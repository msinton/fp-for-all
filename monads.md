## Monads

### Definition using flatMap
In simple terms a monad must have `flatMap` (aka `bind`) and `unit` (aka `pure`), where unit puts a value into the context of a Monad 
and flatMap is a way to transform a value in the monad context by returning another Monad (of the same type).

    def flatMap(m: F[A], f: A => F[B]): F[B]

#### laws
flatMap must be associative. (requires some explanation)
unit and flatMap must obey left and right identity 

    left identity for some value a and fn f: A => F[B]
    e.g. flatMap(unit(a), f) == f(a)  


    associativity
    flatMap(flatMap(m, f), g) == flatMap(m, { x => flatMap(f(x), g) })

#### Definition using map and flatten
Alternatively, you can say that a Monad must have `unit`, `map` and `flatten`, where flatten removes a layer from a nested context:

    def flatten(fa: F[F[A]]): F[A]

### Examples of Monads:

- Option aka Maybe
- List
- ID
- Either
- IO
- Promise/Future (sometimes...)

The scala Future looks like a monad but it doesn't obey the laws! 
It is not referentially transparent so when side effects are involved the associativity breaks

https://stackoverflow.com/questions/27454798/is-future-in-scala-a-monad

[Watch Fabio explain Referential Transparency and the IO Monad - from 5:35](https://www.youtube.com/watch?v=x3GLwl1FxcA&feature=youtu.be&t=3m9s)

[Test your understanding of Monads (using the scala FP library cats)](https://www.scala-exercises.org/cats/monad)
