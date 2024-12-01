package lv.esupe.aoc.year2024

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import kotlin.math.absoluteValue

fun main() = solve { Day1() }

class Day1 : Puzzle<Int, Int>(2024, 1) {
    override val input = rawInput.map { line ->
        val (left, right) = line
            .split("   ")
            .map { it.toInt() }
        left to right
    }
    private val left = input.map { line -> line.first }
    private val right = input.map { line -> line.second }

    override fun solvePartOne(): Int {
        val ls = left.sorted()
        val rs = right.sorted()
        return ls.foldIndexed(0) { idx, acc, i ->
            acc + (i - rs[idx]).absoluteValue
        }
    }

    override fun solvePartTwo(): Int {
        return left.fold(0) { acc, i ->
            acc + i * right.count { it == i }
        }
    }
}
