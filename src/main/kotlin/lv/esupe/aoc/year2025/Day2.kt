package lv.esupe.aoc.year2025

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.allEqual

fun main() = solve { Day2() }

class Day2 : Puzzle<Long, Long>(2025, 2) {
    override val input = rawInput[0]
        .split(",")
        .map { range ->
            range.substringBefore('-').toLong() .. range.substringAfter('-').toLong()
        }

    override fun solvePartOne(): Long {
        return solve { isValidPartOne() }
    }

    override fun solvePartTwo(): Long {
        return solve { isValidPartTwo() }
    }

    private fun solve(isValid: Long.() -> Boolean): Long {
        return input
            .flatMap { range -> range.filter { id -> !id.isValid() } }
            .sum()
    }

    private fun Long.isValidPartOne(): Boolean {
        val str = toString()
        if (str.length % 2 != 0) return true

        val middle = str.length / 2
        return str.take(middle) != str.takeLast(middle)
    }

    private fun Long.isValidPartTwo(): Boolean {
        val str = toString()
        val middle = str.length / 2
        (middle downTo 1).forEach { size ->
            if (str.chunked(size).allEqual()) return false
        }
        return true
    }
}
