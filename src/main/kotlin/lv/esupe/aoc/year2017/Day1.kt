package lv.esupe.aoc.year2017

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.toIntValue

fun main(args: Array<String>) = solve { Day1() }

class Day1 : Puzzle<Int, Int>(2017, 1) {
    override val input = rawInput.first().map { it.toIntValue() }

    override fun solvePartOne(): Int =
        input.filterIndexed { index, int ->
            input[(index + 1) % input.size] == int
        }.sum()

    override fun solvePartTwo(): Int =
        input.filterIndexed { index, int ->
            input[(index + (input.size / 2)) % input.size] == int
        }.sum()
}