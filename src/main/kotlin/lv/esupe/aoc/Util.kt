package lv.esupe.aoc

import java.nio.file.Files
import java.nio.file.Paths


fun getInput(year: Int, day: Int, puzzle: Int): List<String> =
    Puzzle::class.java.classLoader.getResource("input/year$year/d${day}p$puzzle.in")
        .toURI()
        .let { Paths.get(it) }
        .let { Files.readAllLines(it) }

fun <T : Any> List<T>.asInfiniteSequence(): Sequence<T> {
    var index = 0
    return generateSequence(get(index)) { get(++index % size) }
}

fun Char.toIntValue(): Int = toInt() - 48