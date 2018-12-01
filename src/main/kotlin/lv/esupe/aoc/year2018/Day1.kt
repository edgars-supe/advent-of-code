package lv.esupe.aoc.year2018

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.utils.asInfiniteSequence


fun main(args: Array<String>) {
    Day1Puzzle1().calculateAndPrint()
    Day1Puzzle2().calculateAndPrint()
}

class Day1Puzzle1 : Puzzle<Int>(2018, 1, 1) {
    override fun calculate(): Int = input.map { it.toInt() }.sum()
}

class Day1Puzzle2 : Puzzle<Int>(2018, 1, 2) {
    override fun calculate(): Int {
        val set = HashSet<Int>()
        input.map { it.toInt() }
            .asInfiniteSequence()
            .fold(0) { acc, i ->
                if (set.contains(acc)) return acc
                set.add(acc)
                acc + i
            }
        throw Exception("No Christmas this year")
    }
}