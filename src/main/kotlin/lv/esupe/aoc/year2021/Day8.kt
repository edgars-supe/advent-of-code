package lv.esupe.aoc.year2021

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day8() }

class Day8 : Puzzle<Int, Int>(2021, 8) {
    override val input: List<Entry> = rawInput.map { line ->
        val (patterns, output) = line.split(" | ")
        Entry(
            patterns.split(" ").map { it.toSet() },
            output.split(" ").map { it.toSet() }
        )
    }

    override fun solvePartOne(): Int {
        val validLengths = listOf(2, 4, 3, 7)
        return input.map { it.output }
            .flatten()
            .count { output -> output.size in validLengths }
    }

    override fun solvePartTwo(): Int {
        return input
            .map { entry ->
                fun getPattern(length: Int, predicate: (Set<Char>) -> Boolean = { true }): Set<Char> {
                    return entry.patterns.first { it.size == length && predicate(it) }
                }

                val one = getPattern(length = 2)
                val four = getPattern(length = 4)
                val seven = getPattern(length = 3)
                val eight = getPattern(length = 7)

                val nine = getPattern(length = 6) { it intersect four == four }
                val zero = getPattern(length = 6) { it != nine && it intersect seven == seven }
                val six = getPattern(length = 6) { it != zero && it != nine }

                val three = getPattern(length = 5) { it intersect seven == seven }
                val five = getPattern(length = 5) { it != three && nine intersect it == it }
                val two = getPattern(length = 5) { it != three && it != five }

                val digits = listOf(zero, one, two, three, four, five, six, seven, eight, nine)
                entry.output.fold(0) { acc, pattern -> acc * 10 + digits.indexOf(pattern) }
            }
            .sum()
    }


    data class Entry(
        val patterns: List<Set<Char>>,
        val output: List<Set<Char>>
    )
}
