package lv.esupe.aoc.year2021

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day1() }

class Day1 : Puzzle<Int, Int>(2021, 1) {
    override val input = rawInput.map { it.toInt() }

    // find how many times the consecutive number increases
    override fun solvePartOne(): Int {
        return input.zipWithNext() // pair with next number
            .count { (prev, next) -> next > prev }
    }

    // find how many times the sum of a sliding window of 3 numbers increases
    override fun solvePartTwo(): Int {
        return input.windowed(3) { it.sum() }
            .zipWithNext()
            .count { (prev, next) -> next > prev }
    }
}
