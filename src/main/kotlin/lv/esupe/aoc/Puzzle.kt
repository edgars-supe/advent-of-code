package lv.esupe.aoc

import lv.esupe.aoc.utils.getInput


abstract class Puzzle<T>(year: Int, day: Int, puzzle: Int) {
    val identifier = "Year $year, day $day, puzzle $puzzle"
    val input = getInput(year, day, puzzle)

    abstract fun calculate(): T

    fun calculateAndPrint() = println("$identifier: ${calculate()}")
}