package lv.esupe.aoc

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.system.measureNanoTime

object Solver {
    var suffix: String = ""
}

fun <T : Any, R : Any> solve(block: () -> Puzzle<T, R>) {
    printResult(block)
    println()
    benchmark(block)
}

fun getInput(year: Int, day: Int): List<String> =
    Puzzle::class.java.classLoader.getResource("input/year$year/day$day${Solver.suffix}.in")
        .toURI()
        .let { Paths.get(it) }
        .let { Files.readAllLines(it) }

private fun <T : Any, R : Any> printResult(block: () -> Puzzle<T, R>) {
    val puzzle = block()
    val partOneResult = puzzle.solvePartOne().bold("33")
    val partTwoResult = puzzle.solvePartTwo().bold("33")
    println("Year ${puzzle.year}, Day ${puzzle.day}".bold())
    println("Part 1: $partOneResult")
    println("Part 2: $partTwoResult")
}

private fun benchmark(block: () -> Puzzle<*, *>) {
    println("Benchmarking...")
    var initTime = 0L
    var partOneTime = 0L
    var partTwoTime = 0L
    repeat(5) {
        lateinit var p: Puzzle<*, *>
        initTime += measureNanoTime { p = block() }
        partOneTime += measureNanoTime { p.solvePartOne() }
        partTwoTime += measureNanoTime { p.solvePartTwo() }
    }
    initTime /= 5
    partOneTime /= 5
    partTwoTime /= 5

    printBenchmark(initTime.toMillis(), partOneTime.toMillis(), partTwoTime.toMillis())
}

private fun printBenchmark(initTime: Float, partOneTime: Float, partTwoTime: Float) {
    println("Initialization took " + "%.3fms".format(initTime).bold())
    printPartBenchmark(1, partOneTime, initTime)
    printPartBenchmark(2, partTwoTime, initTime)
    println("Total: " + "%.3fms".format(initTime + partOneTime + partTwoTime).bold())
}

private fun printPartBenchmark(part: Int, time: Float, initTime: Float) {
    println(
        "Part $part took " +
            "%.3fms".format(time).bold() +
            ", including init: " +
            "%.3fms".format((initTime + time)).bold()
    )
}

private fun Any.bold(color: String = ""): String = style("$color;1")

private fun Any.style(color: String): String {
    return "\u001B[${color}m$this\u001B[0m"
}

private fun Long.toMillis() = this / 1000000f