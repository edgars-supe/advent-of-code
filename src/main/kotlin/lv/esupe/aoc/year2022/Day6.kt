package lv.esupe.aoc.year2022

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day6() }

class Day6 : Puzzle<Int, Int>(2022, 6) {
    override val input = rawInput.first()

    override fun solvePartOne(): Int {
        return findMarker(4)
    }

    override fun solvePartTwo(): Int {
        return findMarker(14)
    }

    private fun findMarker(length: Int): Int {
        return input
            .windowed(size = length, step = 1)
            .withIndex()
            .first { (_, chars) ->
                chars.toSet().size == length
            }
            .let { (idx, _) -> idx + length }
    }
}
