package lv.esupe.aoc.year2025

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day3() }

class Day3 : Puzzle<Long, Long>(2025, 3) {
    override val input = rawInput.map { line -> line.map { it.digitToInt() } }

    override fun solvePartOne(): Long {
        return solve(n = 2)
    }

    override fun solvePartTwo(): Long {
        return solve(n = 12)
    }

    private fun solve(n: Int): Long {
        return input.sumOf { bank ->
            findMaxJoltage(bank, n).toLong()
        }
    }

    private fun findMaxJoltage(bank: List<Int>, n: Int): String {
        if (bank.isEmpty() || n == 0) {
            return ""
        }

        val max = bank.dropLast(n - 1).withIndex().maxBy { it.value }
        return max.value.toString() + findMaxJoltage(bank.drop(max.index + 1), n - 1)
    }
}
