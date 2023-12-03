package lv.esupe.aoc

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import lv.esupe.aoc.utils.bold
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.system.measureNanoTime

object Solver {
    var suffix: String = ""

    val defaultInputProvider: (year: Int, day: Int) -> List<String> = { year, day ->
        Puzzle::class.java.classLoader
            .getResource("input/year$year/day$day$suffix.in")
            .toURI()
            .let { Paths.get(it) }
            .let { Files.readAllLines(it) }
    }

    var inputProvider: (year: Int, day: Int) -> List<String> = defaultInputProvider
}

private const val BENCH_DURATION = 60_000L

fun <T : Any, R : Any> solve(benchmark: Boolean = true, block: () -> Puzzle<T, R>) {
    Solver.inputProvider = Solver.defaultInputProvider
    printResult(block)
    if (benchmark) {
        println()
        benchmark(block)
    }
}

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
    var initTime = 0L
    var partOneTime = 0L
    var partTwoTime = 0L
    var timesRun = 0
    runBlocking {
        val runner = launch(Dispatchers.IO) {
            while (true) {
                lateinit var p: Puzzle<*, *>
                initTime += measureNanoTime { p = block() }
                partOneTime += measureNanoTime { p.solvePartOne() }
                partTwoTime += measureNanoTime { p.solvePartTwo() }
                timesRun++
                yield()
            }
        }
        var elapsed = 0L
        val counter = launch {
            while (true) {
                printBenchmarkProgress(elapsed, timesRun)
                elapsed += 1000L
                delay(1000L)
            }
        }
        delay(BENCH_DURATION)
        runner.cancel()
        counter.cancel()
        print("\r")
    }
    initTime /= timesRun
    partOneTime /= timesRun
    partTwoTime /= timesRun
    printBenchmark(timesRun, initTime.toMillis(), partOneTime.toMillis(), partTwoTime.toMillis())
}

private fun printBenchmarkProgress(elapsedTime: Long, timesRun: Int) {
    val percentDone = elapsedTime.toFloat() / BENCH_DURATION * 100
    print("\rBenchmarking... (${"%.0f%%".format(percentDone)}, $timesRun runs)")
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

private fun Long.toMillis() = this / 1000000f
