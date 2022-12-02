package lv.esupe.aoc.year2022

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.chunkedBy

fun main() = solve { Day1() }

class Day1 : Puzzle<Int, Int>(2022, 1) {
    override val input = rawInput
        .chunkedBy { it.isBlank() }
        .map { elfCalories ->
            elfCalories.sumOf { it.toInt() }
        }
        .sortedDescending()

    override fun solvePartOne(): Int {
        return input.first()
    }

    override fun solvePartTwo(): Int {
        return input
            .take(3)
            .sum()
    }
}
