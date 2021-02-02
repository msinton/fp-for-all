# Using the fp-ts library - a jump-start guide

Functional programming is an amazingly rich and fascinating subject. But you can find out more about that later!

For now, we dive into a more or less cheat-sheet guide to getting started.

# Core stuff

## pipe

Use pipe to do one thing after another

instead of

```ts
const x = 1;
const y = add(x, 2);
const z = multiply(y, 5); // z = 15
```

Use `pipe` like so:

```ts
const z = pipe(1, add(2), multiply(5)); // z = 15
```

✨ we gain readability

✨ reduce "leaky scope"

✨ type inference for generic functions (not demonstrated here)

## flow

Use flow in a similar way to pipe, except forget about that initial data - we'll deal with it later!

given

```ts
const add = (x: number) => (y: number) => {
  return x + y;
};
const multiply = (x: number) => (y: number) => {
  return x * y;
};
const subtract = (x: number) => (y: number) => {
  return x - y;
};
```

instead of

```ts
// ((1 + 2) * 3) - 5)
subtract(multiply(add(1)(2), 3), 5);

// ((1 + 123) * 3) - 5)
subtract(multiply(add(1)(123), 3), 5);
```

Use `flow` like so:

```ts
const baseFormula = flow(add(1), multiply(3), subtract(5));

// ((1 + 2) * 3) - 5)
baseFormula(2);
// ((1 + 123) * 3) - 5)
baseFormula(123);
```

✨ we gain reusablilty and readability

## map

An abstraction for transforming the contents.

You will likely know this for arrays/lists

```ts
[1, 2, 3].map((x) => x * x); // [1, 4, 9]

// equivalently
const square = (x: number) => x * x;

pipe([1, 2, 3], map(square));
```

The same applies to other "containers"

`some(2).map(x => x * x) // some(4)`

Think of `some` as a list of 1

But don't worry what the containers are for just yet, here are more examples:

```ts
// Either
pipe(Either.right(2), map(square)); // right(4)
// Task
pipe(Task.of(3), map(square)); // task(9)
// Record
pipe({ value: 4 }, map(square)); // {value: 16}
```

📝 In each example, each container requires a different `map` function that is imported from the corresponding container lib.

```ts
import * as E from 'fp-ts/lib/Either';
import * as R from 'fp-ts/lib/Record';

pipe(Either.right(2), E.map(square));
pipe({ value: 4 }, R.map(square));
```

## chain

This is similar to `map`, the difference is that the function you supply must return a value that is also wrapped by a container.

This avoids nesting, take array as an example.

```ts
pipe(
  [1, 2, 3],
  map((x) => [x, x * x])
) >
  [
    [1, 1],
    [2, 4],
    [3, 9],
  ];
```

but we want a flat list, so use chain:

```ts
pipe(
  [1, 2, 3],
  chain((x) => [x, x * x])
) > [1, 1, 2, 4, 3, 9];
```

Again, this applies to any container (that obeys certain rules)

```ts
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

## sequence

For taking multiple "containers" and combining them together into a single "container".

In other words, transform `[container<A>, container<B>, container<C>]` to `container<[A, B, C]>`

Runs the "effect" of the containers one by one (**sequentially**).

Comes in 2 flavours `sequenceT` and `sequenceS`.

### sequenceT

```ts
// Option
pipe(
  sequenceT(option)([some(1), some(2)]),
  map(([value1, value2]) => value1 + value2)
); // some(3)

pipe(
  sequenceT(option)([some(1), none, some(3)]),
  map(([value1, value2, value3]) => value1 + value2 + value3)
); // none
```

```ts
// Either
pipe(
  sequenceT(either)([right(1), right(2)]),
  map(([value1, value2]) => value1 + value2)
); // right(3)

pipe(
  sequenceT(either)([right(1), left('not a number'), right(3)]),
  map(([value1, value2, value3]) => value1 + value2 + value3)
); // left('not a number')
```

### sequenceS

`sequenceS` is similar to `sequenceT` except that we can declare our containers within a **structure**.

```ts
// Option
pipe(
  sequenceS(option)({ x: some(1), y: some(2) }),
  map(({ x, y }) => x + y)
); // some(3)
```

```ts
// Either
pipe(
  sequenceS(either)({ x: right(1), y: right(2), z: right(3) }),
  map(({x, y, z}) => x + y + z)
) // right(6)
```

## traverse

Like `sequence`, traverse also runs the effect of multiple containers together, sequentially.

Traverse also allows you to transform each value with a function.

Therefore `traverse` is equivalent to `sequence` followed by `map`.

given

```ts
const multiplyBy2ThenAddThree = (x: Number) => 2 * x + 3;
```

```ts
// Option
pipe([some(1), some(2)], traverse(option)(multiplyBy2ThenAddThree)); // some([5, 7])

pipe([some(1), none, some(3)], traverse(option)(multiplyBy2ThenAddThree)); // none
```

```ts
// Either
pipe([right(1), right(2)], traverse(either)(multiplyBy2ThenAddThree)); // right([5, 7])

pipe(
  [some(1), left('not a number'), some(3)],
  traverse(either)(multiplyBy2ThenAddThree)
); // left('not a number')
```
