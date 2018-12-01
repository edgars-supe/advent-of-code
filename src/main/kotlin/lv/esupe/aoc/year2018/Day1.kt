package lv.esupe.aoc.year2018

import lv.esupe.aoc.Puzzle


fun main(args: Array<String>) {
    Day1Puzzle1().calculate().let { println("Day 1, Puzzle 1: $it") }
    Day1Puzzle2().calculate().let { println("Day 1, Puzzle 2: $it") }
}

class Day1Puzzle1 : Puzzle<Int>(2018, 1, 1) {
    override fun calculate(): Int =
        input.map { it.toInt() }
            .reduce { acc, i -> acc + i }
}

class Day1Puzzle2 : Puzzle<Int>(2018, 1, 2) {
    override fun calculate(): Int {
        var index = 0
        val set = HashSet<Int>()
        val list = input.map { it.toInt() }
        generateSequence(list[index]) { list[(++index) % list.size] }
            .fold(0) { acc, i ->
                if (set.contains(acc)) return acc
                set.add(acc)
                acc + i
            }
        throw Exception("No Christmas this year")
    }
}