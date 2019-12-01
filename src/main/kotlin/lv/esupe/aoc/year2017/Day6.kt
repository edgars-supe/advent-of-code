package lv.esupe.aoc.year2017

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main(args: Array<String>) = solve { Day6() }

class Day6 : Puzzle<Int, Int>(2017, 6) {
    override val input = rawInput
        .first()
        .split("\t")
        .map { it.toInt() }

    override fun solvePartOne(): Int = input.redistributePartOne()

    override fun solvePartTwo(): Int = input.redistributePartTwo()

    private fun List<Int>.redistributePartOne(): Int {
        var cycles = 0
        val bankSet = mutableSetOf<List<Int>>()
        var banks = this
        while (bankSet.add(banks)) {
            val max = banks.max()!!
            var idx = banks.indexOfFirst { it == max }
            banks = banks.toMutableList()
                .apply {
                    set(idx, 0)
                    (0 until max).forEach {
                        val i = (++idx) % banks.size
                        set(i, get(i) + 1)
                    }
                }
            cycles++
        }
        return cycles
    }

    private fun List<Int>.redistributePartTwo(): Int {
        var cycles = 0
        val bankSet = mutableMapOf<List<Int>, Int>()
        var banks = this
        while (!bankSet.contains(banks)) {
            bankSet[banks] = cycles
            val max = banks.max()!!
            var idx = banks.indexOfFirst { it == max }
            banks = banks.toMutableList()
                .apply {
                    set(idx, 0)
                    (0 until max).forEach {
                        val i = (++idx) % banks.size
                        set(i, get(i) + 1)
                    }
                }
            cycles++
        }
        return cycles - bankSet[banks]!!
    }
}
