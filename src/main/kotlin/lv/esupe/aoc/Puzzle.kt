package lv.esupe.aoc

import lv.esupe.aoc.utils.getInput
import kotlin.system.measureTimeMillis


abstract class Puzzle<T, U>(year: Int, day: Int) {
    val identifier = "Year $year, day $day"
    val rawInput = getInput(year, day)
    abstract val input: Any

    abstract fun solvePartOne(): T

    abstract fun solvePartTwo(): U

    fun solve() {
        measureTimeMillis {
            solvePartOne()
            print("$identifier, part one result: ${solvePartOne()}")
        }.let { println(", solved in ${it}ms")}
        measureTimeMillis {
            solvePartTwo()
            print("$identifier, part two result: ${solvePartTwo()}")
        }.let { println(", solved in ${it}ms")}
    }
}