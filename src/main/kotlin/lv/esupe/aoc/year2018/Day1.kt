package lv.esupe.aoc.year2018

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.asInfiniteSequence


fun main() = solve { Day1() }

class Day1 : Puzzle<Int, Int>(2018, 1) {
    override val input = rawInput.map { it.toInt() }

    override fun solvePartOne(): Int = input.sum()

    override fun solvePartTwo(): Int {
        val set = HashSet<Int>()
        input.asInfiniteSequence()
            .fold(0) { acc, i ->
                if (set.contains(acc)) return acc
                set.add(acc)
                acc + i
            }
        throw Exception("No Christmas this year")
    }
}
