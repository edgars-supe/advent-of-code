package lv.esupe.aoc.year2019

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main(args: Array<String>) = solve { Day5() }

class Day5 : Puzzle<Int, Int>(2019, 5) {

    override val input = rawInput[0].split(",").map { it.toInt() }

    override fun solvePartOne(): Int = Intcode(input, 1).run()

    override fun solvePartTwo(): Int = Intcode(input, 5).run()
}
