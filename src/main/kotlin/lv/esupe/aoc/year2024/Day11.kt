package lv.esupe.aoc.year2024

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.memoize

fun main() = solve { Day11() }

class Day11 : Puzzle<Long, Long>(2024, 11) {
    override val input = rawInput.first()
        .split(" ")
        .map(String::toLong)

    private val blink: (Long, Int) -> Long = memoize(::_blink)

    override fun solvePartOne(): Long = countStones(blinks = 25)

    override fun solvePartTwo(): Long = countStones(blinks = 75)

    private fun countStones(blinks: Int): Long = input.sumOf { number -> blink(number, blinks) }

    private fun _blink(number: Long, times: Int): Long {
        return when {
            times == 0 -> 1
            number == 0L -> blink(1, times - 1)
            number.toString().length % 2 == 0 -> {
                val (first, second) = split(number)
                blink(first, times - 1) + blink(second, times - 1)
            }
            else -> blink(number * 2024, times - 1)
        }
    }

    private fun split(number: Long): Pair<Long, Long> {
        val ns = number.toString()
        val middle = ns.length / 2
        val first = ns.substring(0 until middle).toLong()
        val second = ns.substring(middle..ns.lastIndex).toLong()
        return first to second
    }
}
