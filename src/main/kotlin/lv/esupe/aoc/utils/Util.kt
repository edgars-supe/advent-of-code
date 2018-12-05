package lv.esupe.aoc.utils

import lv.esupe.aoc.Puzzle
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis


fun getInput(year: Int, day: Int, puzzle: Int): List<String> =
    Puzzle::class.java.classLoader.getResource("input/year$year/d${day}p$puzzle.in")
        .toURI()
        .let { Paths.get(it) }
        .let { Files.readAllLines(it) }

fun Char.toIntValue(): Int = toInt() - 48

fun List<Char>.asString(): String = joinToString(separator = "")

fun CharArray.asString(): String = String(this)

inline fun String.charByChar(other: String, crossinline block: (Char?, Char?) -> Unit) {
    val max = maxOf(length, other.length)
    for (i in 0 until max) {
        val c1 = getOrNull(i)
        val c2 = other.getOrNull(i)
        block(c1, c2)
    }
}

fun benchmark(block: () -> Unit) = measureTimeMillis { block() }.let { println(it) }
fun benchmarkNano(block: () -> Unit) = measureNanoTime { block() }.let { println(it) }