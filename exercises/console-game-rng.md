## Console game with Random Number Generator

Functional programming is all about programming with functions.

    Functions are:
    1. Total. For every input, they return an output
    2. Deterministic. For the same input, they return the same value
    3. Pure. Their only effect is computing the return value

    These properties help us:

    1. Reason about our programs using equational reasoning
    2. Refactor our programs without changing their meaning
    3. Test our programs more easily
    4. Invert control so caller always has control over callee
    … and more


-----
### Problem

    Write a console application (game) that fulfils the following requirements:

    1. Ask for user's name
    2. Say hello with name
    3. Ask for user to guess number between 1 and 5
    4. "Randomly" generate number
    5. Tell user by name if correct or not
    6. Ask if usr wants to play again
    7. Loop 3-6 until user indicates no. 

----

### Resources

There is a video you can watch where John De Goes tackles this problem. He uses an approach we have not looked at in these sessions called Tagless Final.

https://www.youtube.com/watch?v=sxudIMiOo68