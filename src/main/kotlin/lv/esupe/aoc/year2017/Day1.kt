package lv.esupe.aoc.year2017

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.utils.toIntValue

fun main(args: Array<String>) {
    Day1Puzzle1().calculateAndPrint()
    Day1Puzzle2().calculateAndPrint()
}

class Day1Puzzle1 : Puzzle<Int>(2017, 1, 1) {
    override fun calculate(): Int {
        val list = input.first().map { it.toIntValue() }
        return list.filterIndexed { index, int ->
            list[(index + 1) % list.size] == int
        }.sum()
    }
}

class Day1Puzzle2 : Puzzle<Int>(2017, 1, 2) {
    override fun calculate(): Int {
        val list = input.first().map { it.toIntValue() }
        return list.filterIndexed { index, int ->
            list[(index + (list.size / 2)) % list.size] == int
        }.sum()
    }
}