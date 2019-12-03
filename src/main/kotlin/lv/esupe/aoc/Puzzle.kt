package lv.esupe.aoc

import java.nio.file.Files
import java.nio.file.Paths


abstract class Puzzle<T, U>(val year: Int, val day: Int) {
    val rawInput = getInput(year, day)
    abstract val input: Any

    abstract fun solvePartOne(): T

    abstract fun solvePartTwo(): U

    private fun getInput(year: Int, day: Int): List<String> =
        Puzzle::class.java.classLoader.getResource("input/year$year/day$day.in")
            .toURI()
            .let { Paths.get(it) }
            .let { Files.readAllLines(it) }
}