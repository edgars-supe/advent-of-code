package lv.esupe.aoc.year2024

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day22() }

class Day22 : Puzzle<Long, Long>(2024, 22) {
    override val input = rawInput.map { it.toLong() }

    override fun solvePartOne(): Long {
        return input.sumOf { secret ->
            sequence(times = 2000, secret)
        }
    }

    override fun solvePartTwo(): Long {
        return 0
    }

    private tailrec fun sequence(times: Int, secret: Long): Long {
        if (times == 0) return secret
        var s = secret
        s = prune(mix(s shl 6, s))
        s = prune(mix(s shr 5, s))
        s = prune(mix(s shl 11, s))
        return sequence(times - 1, s)
    }

    private fun mix(number: Long, secret: Long): Long {
        return number xor secret
    }

    private fun prune(secret: Long): Long {
        return secret and PRUNE_AND
    }

    companion object {
        private const val PRUNE_AND = 16777216 - 1L
    }
}
