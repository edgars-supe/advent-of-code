package lv.esupe.aoc.year2020

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.forAllPairs

fun main() = solve { Day1() }

class Day1 : Puzzle<Int, Int>(2020, 1) {
    companion object {
        private const val TARGET = 2020
    }

    override val input = rawInput.map { it.toInt() }

    override fun solvePartOne(): Int {
        input.forAllPairs { i, j ->
            if (i + j == TARGET) return i * j
        }
        error("Matching numbers not found")
    }

    override fun solvePartTwo(): Int {
        for (i in input.indices) {
            val x = input[i]
            if (x > TARGET - 2) continue

            for (j in (i + 1) until input.size) {
                val y = input[j]
                if (x + y > TARGET - 1) continue

                for (k in (j + 1) until input.size) {
                    val z = input[k]
                    if (x + y + z == TARGET) return x * y * z
                }
            }
        }
        error("Matching numbers not found")
    }
}
