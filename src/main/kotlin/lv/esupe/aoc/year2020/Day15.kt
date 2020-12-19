package lv.esupe.aoc.year2020

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day15() }

class Day15 : Puzzle<Int, Int>(2020, 15) {
    override val input = rawInput[0].split(",").map { it.toInt() }

    override fun solvePartOne(): Int {
        return playGame(2020)
    }

    override fun solvePartTwo(): Int {
        return playGame(30_000_000)
    }

    private fun playGame(rounds: Int): Int {
        val map = input.dropLast(1).withIndex()
            .associate { (idx, value) -> value to idx + 1 }
            .toMutableMap()
        var target = input.last()
        for (turn in (map.size + 1..rounds)) {
            if (!map.keys.contains(target)) {
                map[target] = turn
                target = 0
            } else {
                val d = turn - (map[target] ?: error("Shouldn't happen"))
                map[target] = turn
                target = d
            }
        }
        return map.entries
            .firstOrNull { (_, value) -> value == rounds }
            ?.key
            ?: error("Haven't reached max")
    }
}
