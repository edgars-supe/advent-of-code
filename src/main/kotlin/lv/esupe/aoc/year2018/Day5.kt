package lv.esupe.aoc.year2018

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.utils.benchmark
import lv.esupe.aoc.utils.pmap


fun main(args: Array<String>) {
    Day5Puzzle1().calculateAndPrint()
    benchmark {
        Day5Puzzle2().calculateAndPrint()
    }
}

class Day5Puzzle1 : Puzzle<Int>(2018, 5, 1) {
    override fun calculate(): Int =
        input.first().destroyUnits().length
}

class Day5Puzzle2 : Puzzle<Int>(2018, 5, 2) {
    override fun calculate(): Int =
        ('a'..'z').pmap {
            input.first()
                .filterNot { c -> c.toLowerCase() == it }
                .destroyUnits()
                .length
        }.minBy { it }!!
}

private fun String.destroyUnits(): String {
    var polymer = this
    var destroyed = polymer.destroyUnitsSinglePass()
    while (polymer.length > destroyed.length) {
        polymer = destroyed
        destroyed = destroyed.destroyUnitsSinglePass()
    }
    return destroyed
}

private fun String.destroyUnitsSinglePass(): String {
    val sb = StringBuilder()
    var idx = 0
    while (idx < length) {
        if (get(idx).isReverseCase(getOrNull(idx + 1))) {
            idx += 2
        } else {
            sb.append(get(idx))
            idx++
        }
    }
    return sb.toString()
}

private fun Char.isReverseCase(other: Char?): Boolean = other == reverseCase()

private fun Char.reverseCase(): Char =
    if (isLowerCase()) toUpperCase()
    else toLowerCase()