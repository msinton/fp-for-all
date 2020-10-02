# Using the fp-ts library - a jump-start guide

Functional programming is an amazingly rich and fascinating subject. But you can find out more about that later!

For now, we dive into a more or less cheat-sheet guide to getting started.

## Core stuff

### pipe

Use pipe to do one thing after another

instead of

```
const x = 1
const y = add(x, 2)
const z = multiply(y, 5) // z = 15
```

Use `pipe` like so:

```
const z = pipe(
  1,
  add(2),
  multiply(5)
) // z = 15
```

✨ we gain readability

✨ reduce "leaky scope"

✨ type inference for generic functions (not demonstrated here)

### flow

Use flow in a similar way to pipe, except forget about that initial data - we'll deal with it later!

given

```
const add = (x: number) => (y: number) => {
  return x + y
}
const multiply = (x: number) => (y: number) => {
  return x * y
}
const subtract = (x: number) => (y: number) => {
  return x - y
}
```

instead of

```
// ((1 + 2) * 3) - 5)
subtract(multiply(add(1)(2), 3), 5)

// ((1 + 123) * 3) - 5)
subtract(multiply(add(1)(123), 3), 5)
```

Use `flow` like so:

```
const baseFormula = flow(add(1), multiply(3), subtract(5))

// ((1 + 2) * 3) - 5)
baseFormula(2)
// ((1 + 123) * 3) - 5)
baseFormula(123)
```

✨ we gain re-usablilty and readability

### map

An abstraction for transforming the contents.

You will likely know this for arrays/lists

```
[1, 2, 3].map(x => x * x) // [1, 4, 9]

// equivalently
const square = (x: number) => x * x

pipe([1,2,3], map(square))
```

The same applies to other "containers"

`some(2).map(x => x * x) // some(4)`

Think of `some` as a list of 1

But don't worry what the containers are for just yet, here are more examples:

```
pipe(Either.right(2), map(square)) // right(4)

pipe(Task.of(3), map(square)) // task(9)

pipe({'value': 4}, map(square)) // {'value': 16}
```

📝 In each example, each container requires a different `map` function that is imported from the corresponding container lib.

```
import * as E from 'fp-ts/lib/Either'
import * as R from 'fp-ts/lib/Record'

pipe(Either.right(2), E.map(square))
pipe({'value': 4}, R.map(square))
```

### chain

This is similar to `map`, the difference is that the function you supply must return a value that is also wrapped by a container.

This avoids nesting, take array as an example.

```
pipe([1, 2, 3], map(x => [x, x * x]))
> [[1, 1], [2, 4], [3, 9]]
```

but we want a flat list, so use chain:

```
pipe([1, 2, 3], chain(x => [x, x * x]))
> [1, 1, 2, 4, 3, 9]
```

Again, this applies to any container (that obeys certain rules)

```
// Option
const reciprocal = (x: number) => x === 0 ? none : some(1 / x)

pipe(some(2), chain(reciprocal)) // some(0.5)
pipe(some(0), chain(reciprocal)) // none

// Either
const reciprocal = (x: number) =>
  x === 0 ? left('can't divide by zero 🤦‍♀️') : right(1 / x)

pipe(right(2), chain(reciprocal)) // right(0.5)
pipe(right(0), chain(reciprocal)) // left('can't divide by zero 🤦‍♀️')
```

### sequence

TODO
