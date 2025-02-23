package lv.esupe.aoc.year2020

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day13() }

class Day13 : Puzzle<Int, Long>(2020, 13) {
    override val input = rawInput

    override fun solvePartOne(): Int {
        val leaveAt = input[0].toInt()
        return input[1].split(",")
            .filter { it != "x" }
            .map { it.toInt() }
            .map { it to ((leaveAt / it) + 1) * it - leaveAt }
            .minByOrNull { (_, time) -> time }
            ?.let { (id, time) -> id * time }
            ?: error("No bus found")
    }

    override fun solvePartTwo(): Long {
        return 0
    }
}
