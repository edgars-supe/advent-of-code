package lv.esupe.aoc

import lv.esupe.aoc.solver.InputProvider


abstract class Puzzle<T, U>(val year: Int, val day: Int) {

    val rawInput = InputProvider.installedInputProvider.provideInput(year, day)

    abstract val input: Any

    abstract fun solvePartOne(): T

    abstract fun solvePartTwo(): U
}
