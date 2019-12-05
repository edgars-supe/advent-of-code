package lv.esupe.aoc.year2019

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main(args: Array<String>) = solve { Day4() }

class Day4 : Puzzle<Int, Int>(2019, 4) {

    override val input = rawInput[0].split("-").map { it.toInt() }

    private var part1Count = 0
    private var part2Count = 0

    init {
        (input[0]..input[1]).forEach { number ->
            if (!number.isAscending()) return@forEach
            if (number.hasDoubleDigits()) part1Count++
            if (number.hasDoubleDigitsStrict()) part2Count++
        }
    }

    override fun solvePartOne(): Int = part1Count

    override fun solvePartTwo(): Int = part2Count

    private fun Int.isAscending() = toString().zipWithNext().all { (a, b) -> a <= b }

    private fun Int.hasDoubleDigits() = toString().zipWithNext().any { (a, b) -> a == b }

    private fun Int.hasDoubleDigitsStrict() = toString().groupRepeating().any { it.size == 2 }

    private fun String.groupRepeating() = fold(mutableListOf<List<Char>>()) { acc, char ->
        val last = acc.lastOrNull() ?: return@fold mutableListOf(listOf(char))
        if (char in last) acc.apply { set(acc.lastIndex, last + char) }
        else acc.apply { add(listOf(char)) }
    }
}
