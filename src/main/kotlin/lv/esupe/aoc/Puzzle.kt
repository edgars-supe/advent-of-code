package lv.esupe.aoc

import java.nio.file.Files
import java.nio.file.Paths


abstract class Puzzle<T, U>(val year: Int, val day: Int) {
    val rawInput = getInput(year, day)
    abstract val input: Any

    abstract fun solvePartOne(): T

    abstract fun solvePartTwo(): U
}