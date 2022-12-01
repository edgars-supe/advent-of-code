package lv.esupe.aoc.year2022

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.chunkedBy

fun main() = solve(benchmark = false) { Day1() }

class Day1 : Puzzle<Any, Any>(2022, 1) {
    override val input = rawInput
        .chunkedBy { it.isBlank() }
        .map { elfCalories ->
            elfCalories.sumOf { it.toInt() }
        }

    override fun solvePartOne(): Int {
        return input.max()
    }

    override fun solvePartTwo(): Int {
        return input.sortedDescending()
            .take(3)
            .sum()
    }
}
