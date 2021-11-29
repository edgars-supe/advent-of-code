package lv.esupe.aoc

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.system.measureNanoTime

object Solver {
    var suffix: String = ""
}

fun <T : Any, R : Any> solve(benchmark: Boolean = true, block: () -> Puzzle<T, R>) {
    printResult(block)
    if (benchmark) {
        println()
        benchmark(block)
    }
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
    print("Benchmarking...")
    val startTime = System.currentTimeMillis()
    var initTime = 0L
    var partOneTime = 0L
    var partTwoTime = 0L
    var times = 0
    while (System.currentTimeMillis() - startTime < 60000) {
        times++
        if (times % 100 == 0) print("\rBenchmarking... ($times runs)")
        lateinit var p: Puzzle<*, *>
        initTime += measureNanoTime { p = block() }
        partOneTime += measureNanoTime { p.solvePartOne() }
        partTwoTime += measureNanoTime { p.solvePartTwo() }
    }
    initTime /= times
    partOneTime /= times
    partTwoTime /= times

    print("\n")
    printBenchmark(times, initTime.toMillis(), partOneTime.toMillis(), partTwoTime.toMillis())
}

private fun printBenchmark(times: Int, initTime: Float, partOneTime: Float, partTwoTime: Float) {
    println("Initialization took " + "%.3fms".format(initTime).bold())
    printPartBenchmark(1, partOneTime, initTime)
    printPartBenchmark(2, partTwoTime, initTime)
    println("Total: " + "%.3fms".format(initTime + partOneTime + partTwoTime).bold() + " (avg. of $times runs)")
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
