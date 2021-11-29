package lv.esupe.aoc.year2017

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.forAllPairs

fun main() = solve { Day2() }

class Day2 : Puzzle<Int, Int>(2017, 2) {
    override val input = rawInput.toIntRows()

    override fun solvePartOne(): Int = input.sumOf { row -> (row.maxOrNull() ?: 0) - (row.minOrNull() ?: 0) }

    override fun solvePartTwo(): Int =
        input.map { row ->
            row.forAllPairs { i, j ->
                val max = maxOf(i, j)
                val min = minOf(i, j)
                if (max % min == 0) return@map max / min
            }
            return@map 0
        }.sum()
}

private fun List<String>.toIntRows() = map { it.split("\t") }
    .map { row -> row.map { it.toInt() } }
