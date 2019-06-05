https://deque.blog/2017/09/13/monoids-what-they-are-why-they-are-useful-and-what-they-teach-us-about-software/

In short, you have a Monoid if you have an Associative binary operation (+) and a zero/identity element (0)

### Common Monoids
---

#### Integer addition:

  Associative: 
    2 + (3 + 4) = (2 + 3) + 4

  Identity (0): 2 + 0 = 2
  
  
#### Integer multiplication:

  Associative: 
    2 * (3 * 4) = (2 * 3) * 4

  Identity (1): 2 * 1 = 2


### Semigroup

A semigroup is a monoid but without requiring the Identity element.

---

[Cats - scala lib that provides some default Monoids, combineAll, foldMap etc](https://typelevel.org/cats/typeclasses/monoid.html)

> There are some types that can form a Semigroup but not a Monoid
> ... For such types that only have a Semigroup we can lift into Option to get a Monoid

### Folds

[Beaultiful folds (Haskell) - Influential talk on folding with Monoids](https://www.youtube.com/watch?v=6a5Ti0r8Q2s)
In this talk he calls `fold` what we call `foldMap`

[<- The content from the talk above translated to Scala](https://softwaremill.com/beautiful-folds-in-scala/)

[Algebird (scala) - Open Source twitter library that uses Monoids to powerful effect](https://twitter.github.io/algebird/)

[Spire (scala) - numeric library using Monoids](https://typelevel.org/spire/)

[foldMap (scala) - cats Foldable includes foldMap](https://typelevel.org/cats/typeclasses/foldable.html)