package lv.esupe.aoc.year2020

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.chunkedBy

fun main() = solve { Day6() }

class Day6 : Puzzle<Int, Int>(2020, 6) {
    override val input: List<List<Set<Char>>> = rawInput.chunkedBy { it.isBlank() }
        .map { it.map(String::toSet) }

    override fun solvePartOne(): Int = input.sumOf { it.reduceAndCount { acc, set -> acc union set } }

    override fun solvePartTwo(): Int = input.sumOf { it.reduceAndCount { acc, set -> acc intersect set } }

    private fun List<Set<Char>>.reduceAndCount(op: (acc: Set<Char>, set: Set<Char>) -> Set<Char>): Int =
        reduce(op).count()
}
