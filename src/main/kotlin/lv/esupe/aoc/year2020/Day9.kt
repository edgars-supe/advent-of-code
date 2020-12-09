package lv.esupe.aoc.year2020

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day9() }

class Day9 : Puzzle<Long, Long>(2020, 9) {
    override val input = rawInput.map { it.toLong() }

    private var invalid: Long = -1

    override fun solvePartOne(): Long = input.windowed(26, 1)
        .first { !it.isLastValid() }
        .last()
        .also { invalid = it }

    override fun solvePartTwo(): Long {
        var start = 0
        var size = 2
        var sum = 0L
        while (sum != invalid) {
            sum = input.subList(start, start + size).sum()
            size++
            if (sum > invalid || start + size > input.lastIndex) {
                start++
                size = 2
            }
        }
        return input.subList(start, start + size)
            .sorted()
            .let { it.first() + it.last() }
    }

    private fun List<Long>.isLastValid(): Boolean =
        any { other -> last() - other in this && last() != other && last() - other != other }
}
