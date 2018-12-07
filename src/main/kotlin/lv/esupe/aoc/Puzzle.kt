package lv.esupe.aoc

import lv.esupe.aoc.utils.getInput
import kotlin.system.measureNanoTime


abstract class Puzzle<T, U>(year: Int, day: Int) {
    val identifier = "y${year}d$day"
    val rawInput = getInput(year, day)
    abstract val input: Any

    abstract fun solvePartOne(): T

    abstract fun solvePartTwo(): U

    fun solve(initTime: Long) {
        solvePart(1, initTime) { solvePartOne() }
        solvePart(2, initTime) { solvePartTwo() }
    }

    private fun <A> solvePart(part: Int, initTime: Long, block: () -> A) {
        val result = block()
        var time = 0L
        repeat(5) { time += measureNanoTime{ block() } }
        val timeAvgNs = time / 5
        val timeAvgMs = time / 5 / 1000000f
        val initTimeMs = initTime / 1000000f
        println(String.format(
            "${identifier}p$part: $result, took %.3fms (${timeAvgNs}ns), incl. init: %.3f (${initTime}ns)",
            timeAvgMs,
            timeAvgMs + initTimeMs
        ))
    }
}

fun solve(block: () -> Puzzle<*, *>) {
    val puzzle = block()
    var time = 0L
    repeat(5) { time += measureNanoTime{ block() } }
    val timeAvgNs = time / 5
    val timeAvgMs = time / 5 / 1000000f
    println(String.format("${puzzle.identifier}init: %.3fms (${timeAvgNs}ns)", timeAvgMs))
    puzzle.solve(timeAvgNs)
}