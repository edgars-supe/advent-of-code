package lv.esupe.aoc.year2017

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.asString

fun main() = solve { Day4() }

class Day4 : Puzzle<Int, Int>(2017, 4) {
    override val input = rawInput.map { it.split(" ") }

    override fun solvePartOne(): Int = input.count { it.size == it.distinct().size }

    override fun solvePartTwo(): Int =
        input.map { list ->
            list.map { it.toCharArray().apply { sort() } }
                .map { it.asString() }
        }.count { it.size == it.distinct().size }
}
