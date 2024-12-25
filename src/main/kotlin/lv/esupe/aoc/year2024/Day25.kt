package lv.esupe.aoc.year2024

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.chunkedBy
import lv.esupe.aoc.utils.pairWith

fun main() = solve { Day25() }

class Day25 : Puzzle<Int, Int>(2024, 25) {
    override val input = rawInput

    private val locks: List<List<Int>>
    private val keys: List<List<Int>>

    init {
        val items = rawInput.chunkedBy { it.isBlank() }
        val (locks, keys) = items.partition { lines -> lines.first().all { it == '#' } }
        this.locks = locks.map { it.toHeights() }
        this.keys = keys.map { it.toHeights() }
    }

    override fun solvePartOne(): Int {
        return keys.asSequence()
            .pairWith(locks.asSequence())
            .filter { (key, lock) ->
                key.zip(lock) { k, l -> k + l }
                    .all { it <= 7 }
            }
            .count()
    }

    override fun solvePartTwo(): Int {
        return 0
    }

    private fun List<String>.toHeights(): List<Int> {
        return (0..first().lastIndex)
            .map { col ->
                count { line -> line[col] == '#' }
            }
    }
}
