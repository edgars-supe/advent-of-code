package lv.esupe.aoc.year2017

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.utils.asString
import kotlin.math.absoluteValue
import kotlin.math.pow

fun main(args: Array<String>) = Day4().solve()

class Day4 : Puzzle<Int, Int>(2017, 4) {
    override val input = rawInput.map { it.split(" ") }

    override fun solvePartOne(): Int = input.count { it.size == it.distinct().size }

    override fun solvePartTwo(): Int =
        input.map { list ->
            list.map { it.toCharArray().apply { sort() } }
                .map { it.asString() }
        }.count { it.size == it.distinct().size }
}