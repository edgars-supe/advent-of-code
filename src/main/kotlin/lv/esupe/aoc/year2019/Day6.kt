package lv.esupe.aoc.year2019

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main(args: Array<String>) = solve { Day6() }

class Day6 : Puzzle<Int, Int>(2019, 6) {

    override val input = rawInput.map { it.split(")") }.associate { it.last() to it.first() }

    override fun solvePartOne(): Int = input.map { (k, _) -> k.countOrbits() }.sum()

    override fun solvePartTwo(): Int {
        val fromYou = pathToCom("YOU")
        val fromSanta = pathToCom("SAN")
        val common = fromYou.first { it in fromSanta }
        return fromYou.indexOf(common) + fromSanta.indexOf(common)
    }

    private fun String.countOrbits(): Int {
        val next = input[this]
        return if (next == null) 0
        else 1 + next.countOrbits()
    }

    private fun pathToCom(from: String): List<String> {
        val next = input[from]
        return if (next == null) emptyList()
        else listOf(next) + pathToCom(next)
    }
}
