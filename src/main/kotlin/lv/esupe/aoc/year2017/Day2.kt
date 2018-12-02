package lv.esupe.aoc.year2017

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.utils.withAllOtherElements

fun main(args: Array<String>) {
    Day2Puzzle1().calculateAndPrint()
    Day2Puzzle2().calculateAndPrint()
}

class Day2Puzzle1 : Puzzle<Int>(2017, 2, 1) {
    override fun calculate(): Int =
        input.toIntRows()
            .map { row -> row.max()!! - row.min()!! }
            .sum()
}

class Day2Puzzle2 : Puzzle<Int>(2017, 2, 2) {
    override fun calculate(): Int =
        input.toIntRows()
            .map { row ->
                row.withAllOtherElements { i, j ->
                    if (i % j == 0 && i > j) return@map i / j
                }
                return@map 0
            }
            .sum()
}

private fun List<String>.toIntRows() = map { it.split("\t") }
        .map { row -> row.map { it.toInt() } }