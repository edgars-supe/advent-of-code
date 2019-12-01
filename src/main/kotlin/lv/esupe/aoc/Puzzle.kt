package lv.esupe.aoc

import lv.esupe.aoc.utils.getInput
import kotlin.system.measureNanoTime


abstract class Puzzle<T, U>(year: Int, day: Int) {
    val identifier = "y${year}d$day"
    val rawInput = getInput(year, day)
    abstract val input: Any

    abstract fun solvePartOne(): T

    abstract fun solvePartTwo(): U
}

fun solve(block: () -> Puzzle<*, *>) {
    val puzzle = block()
    val partOneResult = puzzle.solvePartOne()
    val partTwoResult = puzzle.solvePartTwo()
    var initTime = 0L
    var partOneTime = 0L
    var partTwoTime = 0L
    repeat(5) {
        lateinit var p: Puzzle<*, *>
        initTime += measureNanoTime{ p = block() }
        partOneTime += measureNanoTime { p.solvePartOne() }
        partTwoTime += measureNanoTime { p.solvePartTwo() }
    }
    initTime /= 5
    partOneTime /= 5
    partTwoTime /= 5
    println(String.format("${puzzle.identifier}init: %.3fms (${initTime}ns)", initTime.toMillis()))
    printPartResult(puzzle, 1, partOneResult, partOneTime, initTime)
    printPartResult(puzzle, 2, partTwoResult, partTwoTime, initTime)
}

private fun printPartResult(puzzle: Puzzle<*, *>, part: Int, result: Any?, time: Long, initTime: Long) {
    println(String.format(
        "${puzzle.identifier}p$part: $result, took %.3fms (${time}ns), incl. init: %.3fms (${initTime}ns)",
        time.toMillis(),
        time.toMillis() + initTime.toMillis()
    ))
}


private fun Long.toMillis() = this / 1000000f