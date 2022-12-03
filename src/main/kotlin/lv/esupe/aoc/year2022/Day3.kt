package lv.esupe.aoc.year2022

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day3() }

class Day3 : Puzzle<Int, Int>(2022, 3) {
    override val input = rawInput

    override fun solvePartOne(): Int {
        return input.sumOf { line ->
            val (a, b) = line
                .chunked(line.length / 2)
            a.first { it in b }
                .priority()
        }
    }

    override fun solvePartTwo(): Int {
        return input.chunked(3)
            .sumOf { (a, b, c) ->
                a
                    .first { it in b && it in c }
                    .priority()
            }
    }

    private fun Char.priority(): Int {
        return if (isUpperCase()) {
            this - 'A' + 27
        } else {
            this - 'a' + 1
        }
    }
}
