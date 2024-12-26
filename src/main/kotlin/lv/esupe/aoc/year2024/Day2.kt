package lv.esupe.aoc.year2024

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.removedAt

fun main() = solve { Day2() }

class Day2 : Puzzle<Int, Int>(2024, 2) {
    override val input = rawInput.map { line -> line.split(" ").map { it.toInt() } }

    override fun solvePartOne(): Int {
        return input.count(::isSafe)
    }

    override fun solvePartTwo(): Int {
        return input.count { report ->
            isSafe(report) || report.indices.any { idx -> isSafe(report.removedAt(idx)) }
        }
    }

    private fun isSafe(report: List<Int>): Boolean {
        val zipped = report.zipWithNext()
        val (fa, fb) = zipped.first()
        return if (fa > fb) {
            zipped.all { (a, b) -> a - b in 1..3 }
        } else if (fa < fb) {
            zipped.all { (a, b) -> b - a in 1..3 }
        } else {
            false
        }
    }
}
