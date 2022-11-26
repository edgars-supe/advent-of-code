# Advent Of Code
These are my entries for [Advent of Code](https://adventofcode.com), written in Kotlin. 

## Structure

Puzzle solutions are located in `lv.esupe.aoc.year$year`. A solution class must extend
[`Puzzle`](src/main/kotlin/lv/esupe/aoc/Puzzle.kt). Generic classes and utilities that could be used for any solution
are located in `lv.esupe.aoc.model` and `lv.esupe.aoc.util` packages respectively.

Input files for puzzles are located in `src/main/resources/input/year$year/`. Input files must be named in the format
`day$day.in`.

### `Puzzle`

An empty solution looks something like this:
```kotlin
class Day1 : Puzzle<Int, Int>(2020, 1) {
    override val input = rawInput

    override fun solvePartOne(): Int = TODO()

    override fun solvePartTwo(): Int = TODO()
}
```

`rawInput` is a `List<String>`, each element being a row of the input file. E.g., if the input was a single line of
comma-separated integers, you could define the input value as
```kotlin
override val input: List<Int> = rawInput[0].split(",").map { it.toInt() }
```

Functions `solvePartOne()` and `solvePartTwo()` should contain the solution for the respective part of the puzzle. By
default, the [`Solver`](src/main/kotlin/lv/esupe/aoc/Solver.kt) will run the solution repeatedly for a minute to
benchmark it. To disable this while working on the solution, call `solve(benchmark = false)`. The solve functions will
be run sequentially and a new instance of the solution class will be made for each benchmark run, so it is safe to add
your own fields to the class, fill them in part 1 and use them in part 2.

## Running

Each file containing a puzzle solution also has a main entry function which calls the `solve` method.
```kotlin
fun main() = solve { Day1() }
```
This method will run the provided puzzle, output the solutions and then benchmark the solution repeatedly for 1 minute
to measure the average execution time for the initialization of the class and computation time for both solutions.

Neatly grouped run configuration files for IntelliJ IDEA are provided.

## Creating a new solution

The easiest way is to run the `puzzle` script from the command line in the project root folder, pass the necessary
arguments and paste the puzzle input when prompted. E.g., `./puzzle 2020 1 Int Int`. The last two parameters denote the
return types for the solution of the first and second parts, respectively, and are optional (default to `Any`).
 
Alternatively, you can do it manually:
* Create a class which extends [`Puzzle`](src/main/kotlin/lv/esupe/aoc/Puzzle.kt) in the appropriate package, e.g.
`src/main/kotlin/lv/esupe/aoc/year2020/Day1.kt`.
* Add a main method which calls `solve`, so you can run the puzzle:
```kotlin
fun main() = solve { Day1() }
```
* Create the puzzle input file, e.g., `src/main/resources/year2020/day1.in`. 

## Testing

There's a small test framework class `DayTest`. Puzzle tests extend this class; you must provide a function to
instantiate a new solution class, and define tests which call one or more test methods.

### Testing both parts:
* `fun runTest(suffix: String, part1: T, part2: R)` uses puzzle input files from 
`src/test/resources/input/year$year/day$day_$suffix.in`; `part1` and `part2` are the expected results for the respective
parts of the puzzle.
* `fun test(input: String, part1: T, part2: R)` uses `input` as the single-line puzzle input.
* `fun test(input: List<String>, part1: T, part2: R)` uses `input` as the lines of the input file.

### Testing Part 1:
* `fun testPartOne(input: String, expectedResult: T)`
* `fun testPartOne(input: List<String>, expectedResult: T)`

### Testing Part 2:
Part 1 is run before evaluating the result of part 2 to ensure computations from part 1 are used in part 2, if
necessary.
* `fun testPartTwo(input: String, expectedResult: R)`
* `fun testPartTwo(input: List<String>, expectedResult: R)`