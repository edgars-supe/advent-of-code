package lv.esupe.aoc.year2015

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day1() }

class Day1 : Puzzle<Int, Int>(2015, 1) {
    override val input = rawInput[0]
        .map { c ->
            when (c) {
                '(' -> 1
                ')' -> -1
                else -> error("Unknown char '$c'")
            }
        }

    override fun solvePartOne(): Int {
        return input.sum()
    }

    override fun solvePartTwo(): Int {
        input.reduceIndexed { idx, acc, i ->
            val result = acc + i
            if (result < 0) return idx + 1
            result
        }
        error("Basement never hit")
    }
}
