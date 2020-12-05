package lv.esupe.aoc.year2020

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day5() }

class Day5 : Puzzle<Int, Int>(2020, 5) {

    override val input = rawInput
        .map { line ->
            line.map { char -> if (char == 'F' || char == 'L') '0' else '1' }
                .joinToString("")
                .toInt(2)
        }
        .sorted()

    override fun solvePartOne(): Int = input.last()

    override fun solvePartTwo(): Int = input
        .zipWithNext()
        .first { (a, b) -> a + 1 != b }
        .let { (a, _) -> a + 1 }
}
