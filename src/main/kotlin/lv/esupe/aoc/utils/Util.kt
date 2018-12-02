package lv.esupe.aoc.utils

import lv.esupe.aoc.Puzzle
import java.nio.file.Files
import java.nio.file.Paths


fun getInput(year: Int, day: Int, puzzle: Int): List<String> =
    Puzzle::class.java.classLoader.getResource("input/year$year/d${day}p$puzzle.in")
        .toURI()
        .let { Paths.get(it) }
        .let { Files.readAllLines(it) }

fun Char.toIntValue(): Int = toInt() - 48

inline fun String.charByChar(other: String, crossinline block: (Char?, Char?) -> Unit) {
    val max = maxOf(length, other.length)
    for (i in 0 until max) {
        val c1 = getOrNull(i)
        val c2 = other.getOrNull(i)
        block(c1, c2)
    }
}