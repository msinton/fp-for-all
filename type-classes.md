# Type Class

Consider the problem where you want multiple classes with related behaviour. 

e.g.

    Cat - makeNoise
    Dog - makeNoise
    Person - makeNoise
    Piano - makeNoise

usage example

    def squeeze(thing: MakesNoise) { 
      thing.makeNoise()
    }

How would you solve this?

Now Imagine another requirement comes in - eatFood

Solved it, then everyone is happy and your code is so useful that you Open Source™.

But your library users really want a new function - defecate.

So what do they do? Submit a pull request... This could go on an on and you can 
see how it is not sustainable, maintainable... workable.

If only there was a better way.


#### What is Type and what is Class?

Class:

        A specification (blueprint) for an instance (object)
        
Type: 

        Information about what behaviour (capabilities that something has)

Illustration of cause of confusion

    class A { ... }        (1)
    val a: A = new A()     (2)
    
(1) There is the definition of `class` A

(2) We create an a of `type` A

A is both a name of the class and of the type. The class is a template for an 
object but concrete objects have a type. Actually, every expression has a type! 

A type summarizes the common features of a set of objects with the same characteristics. We may say that a type is an abstract interface that specifies how an object can be used.

A class represents an implementation of the type. It is a concrete data structure and collection of methods.

### What are type classes for?

Main benefits:
 - extensible
 - preserve type 
 
1. They enable behaviour to be expressed independently from a class.

(aka Ad-hoc polymorphism)


2. Preserve type

Think of our example:

    def squeeze(thing: MakesNoise): MakesNoise { 
      thing.makeNoise()
    }
 
Usage

    def sayHi(p: Person) = "Hello " + p.name

    val familiarGreeting: Person => String = {
      squeeze.andThen(sayHi)
    }
    
Why will this fail?

#### What is wrong with mixing data and type?

As in the previous example, it is impractical/impossible to add functionality for 
everything that your data might need. 

### What is a type class?

Examples...

Equality (Eq) 

most data we want to be able to compare, but equality does not make sense for functions

~ list uses equality in `contains`

---

Ordering ~ requires Eq

    <
    >
    
Then we can `sort` !!
We write sort **once** for a List of things that have Ordering
    
### constraints

Type classes allow you to ask for the minimum functionality required by a method

In the example `contains` only `Eq` is required.
In our silly example you might want something that can defecate and makeNoise,
this can be required by you function:

def goToTheToilet[A: MakeNoise with Defecate](a: A) = {
  Par(a.defecate, a.makeNoise)
}


### Links

Examples/Explanations: 

[Scala](https://scalac.io/typeclasses-in-scala/)

[Haskell](https://www.haskell.org/tutorial/classes.html)

[Scala easy to read example](https://alvinalexander.com/scala/fp-book/type-classes-101-introduction)

[Typescript](https://medium.com/@kaw2k/the-intuition-for-type-classes-cbec9eb0537c)

[Scala Json example](https://www.riccardosirigu.com/programming/2017/06/02/type-classes-in-scala-a-practical-example/)
