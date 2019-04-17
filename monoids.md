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
