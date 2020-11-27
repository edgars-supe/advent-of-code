package lv.esupe.aoc.year2018

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.pmap


fun main() = solve { Day5() }

class Day5 : Puzzle<Int, Int>(2018, 5) {
    override val input = rawInput.first()

    override fun solvePartOne(): Int =
        input.destroyUnits().length

    override fun solvePartTwo(): Int =
        ('a'..'z').pmap {
            input.filterNot { c -> c.toLowerCase() == it }
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
