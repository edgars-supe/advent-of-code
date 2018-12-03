package lv.esupe.aoc.year2017

import lv.esupe.aoc.Puzzle

fun main(args: Array<String>) {
    Day6Puzzle1().calculateAndPrint()
    Day6Puzzle2().calculateAndPrint()
}

class Day6Puzzle1 : Puzzle<Int>(2017, 6, 1) {
    override fun calculate(): Int = input.first()
        .split("\t")
        .map { it.toInt() }
        .redistribute()

    private fun List<Int>.redistribute(): Int {
        var cycles = 0
        val bankSet = mutableSetOf<List<Int>>()
        var banks = this
        while (bankSet.add(banks)) {
            var max = banks.max()!!
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
}

class Day6Puzzle2 : Puzzle<Int>(2017, 6, 2) {
    override fun calculate(): Int = input.first()
        .split("\t")
        .map { it.toInt() }
        .redistribute()

    private fun List<Int>.redistribute(): Int {
        var cycles = 0
        val bankSet = mutableMapOf<List<Int>, Int>()
        var banks = this
        while (!bankSet.contains(banks)) {
            bankSet[banks] = cycles
            var max = banks.max()!!
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

