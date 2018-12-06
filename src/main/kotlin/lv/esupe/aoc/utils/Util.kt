package lv.esupe.aoc.utils

import lv.esupe.aoc.Puzzle
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis


fun getInput(year: Int, day: Int): List<String> =
    Puzzle::class.java.classLoader.getResource("input/year$year/day$day.in")
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

fun <T> Array<Array<T>>.forEachIndexed(action: (Int, Int, T) -> Unit) {
    forEachIndexed { i: Int, array: Array<T> ->
        array.forEachIndexed { j: Int, item: T ->
            action(i, j, item)
        }
    }
}