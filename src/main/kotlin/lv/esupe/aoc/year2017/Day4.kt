package lv.esupe.aoc.year2017

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.utils.asString
import kotlin.math.absoluteValue
import kotlin.math.pow

fun main(args: Array<String>) {
    Day4Puzzle1().calculateAndPrint()
    Day4Puzzle2().calculateAndPrint()
}

class Day4Puzzle1 : Puzzle<Int>(2017, 4, 1) {
    override fun calculate(): Int =
        input.map { it.split(" ") }
            .count { it.size == it.distinct().size }

}

class Day4Puzzle2 : Puzzle<Int>(2017, 4, 2) {
    override fun calculate(): Int = input.map { it.split(" ") }
        .map { list ->
            list.map { it.toCharArray().apply { sort() } }
                .map { it.asString() }
        }
        .count { it.size == it.distinct().size }
}