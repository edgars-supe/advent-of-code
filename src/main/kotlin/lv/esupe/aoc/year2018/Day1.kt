package lv.esupe.aoc.year2018

import lv.esupe.aoc.Puzzle


fun main(args: Array<String>) {
    Day1Puzzle1().calculate().let { println("Day 1, Puzzle 1: $it") }
}

class Day1Puzzle1 : Puzzle<Int>(2018, 1, 1) {
    override fun calculate(): Int =
        input.map { it.toInt() }
            .reduce { acc, i -> acc + i }
}