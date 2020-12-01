package lv.esupe.aoc.year2020

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.forAllPairs

fun main() = solve { Day1() }

class Day1 : Puzzle<Int, Int>(2020, 1) {
    override val input = rawInput.map { it.toInt() }

    override fun solvePartOne(): Int {
        input.forAllPairs { i, j ->
            if (i + j == 2020) return i * j
        }
        throw IllegalStateException("Matching numbers not found")
    }

    override fun solvePartTwo(): Int {
        for (i in input.indices) {
            val x = input[i]
            if (x > 2018) continue

            for (j in (i + 1) until input.size) {
                val y = input[j]
                if (x + y > 2019) continue

                for (k in (j + 1) until input.size) {
                    val z = input[k]
                    if (x + y + z == 2020) return x * y * z
                }
            }
        }
        throw IllegalStateException("Matching numbers not found")
    }
}
