package lv.esupe.aoc.year2020

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day10() }

class Day10 : Puzzle<Int, Long>(2020, 10) {
    override val input = rawInput.map { it.toInt() }.sorted()
        .let { listOf(0) + it + (it.last() + 3) }

    override fun solvePartOne(): Int = input
        .zipWithNext()
        .map { (a, b) -> b - a }
        .let { joltages ->
            joltages.count { it == 1 } * joltages.count { it == 3 }
        }

    override fun solvePartTwo(): Long {
        val paths = mutableListOf<Long>()
        input.map { i -> input.count { j -> j in (i + 1)..(i + 3) } }
            .reversed()
            .mapTo(paths) { i ->
                when (i) {
                    0 -> 1
                    1 -> paths.last()
                    else -> paths.takeLast(i).sum()
                }
            }
        return paths.last()
    }
}
