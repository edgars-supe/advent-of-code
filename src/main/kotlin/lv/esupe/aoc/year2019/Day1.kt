package lv.esupe.aoc.year2019

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main(args: Array<String>) = solve { Day1() }

class Day1 : Puzzle<Int, Int>(2019, 1) {
    override val input = rawInput.map { it.toInt() }

    override fun solvePartOne(): Int = input.fold(0) { acc, mass -> acc + mass.fuelReqs() }

    override fun solvePartTwo(): Int = input.fold(0) { acc, mass -> acc + mass.metaReqs() }

    private fun Int.metaReqs(): Int {
        val reqs = fuelReqs()
        return if (reqs <= 0) 0 else reqs + reqs.metaReqs()
    }

    private fun Int.fuelReqs() = this / 3 - 2
}