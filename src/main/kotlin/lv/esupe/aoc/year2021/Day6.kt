package lv.esupe.aoc.year2021

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import kotlin.math.ceil

fun main() = solve { Day6() }

class Day6 : Puzzle<Long, Long>(2021, 6) {

    override val input = rawInput[0].split(",").map { it.toInt() }

    private val memoisation = mutableMapOf<Int, Long>()

    override fun solvePartOne(): Long {
        return input.sumOf { ageFish(it, 80) }
    }

    override fun solvePartTwo(): Long {
        return input.sumOf { ageFish(it, 256) }
    }

    private fun ageFish(initialTimer: Int, days: Int): Long {
        val offsetDays = days - initialTimer - 1
        if (offsetDays < 0) return 1
        val children = ceil((days - initialTimer) / 7.toDouble()).toInt()
        var descendants = 0L
        for (i in 0 until children) {
            val d = offsetDays - i * 7
            val memo = memoisation[d]
            if (memo != null) {
                descendants += memo
            } else {
                val desc = ageFish(8, d)
                memoisation[d] = desc
                descendants += desc
            }
        }
        return 1 + descendants
    }
}
