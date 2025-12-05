package lv.esupe.aoc.year2025

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.chunkedBy
import lv.esupe.aoc.utils.mergeWith
import lv.esupe.aoc.utils.overlaps

fun main() = solve { Day5() }

class Day5 : Puzzle<Int, Long>(2025, 5) {
    override val input = rawInput

    private val ranges: List<LongRange>
    private val ids: List<Long>

    init {
        val (ranges, ids) = input.chunkedBy { it.isBlank() }
        this.ranges = ranges
            .map { line ->
                val (a, b) = line.split("-")
                    .map { it.toLong() }
                a..b
            }
            .sortedBy { it.first }
        this.ids = ids.map { it.toLong() }
    }

    override fun solvePartOne(): Int {
        return ids.count { id -> ranges.any { range -> id in range } }
    }

    override fun solvePartTwo(): Long {
        val queue = ranges.toMutableList()
        val condensed = mutableListOf<LongRange>()
        while (queue.isNotEmpty()) {
            val rangeA = queue.removeAt(0)
            val rangeB = queue.firstOrNull { rangeB -> rangeA overlaps rangeB }
            if (rangeB != null) {
                queue.remove(rangeB)
                queue.add(rangeA.mergeWith(rangeB))
            } else {
                condensed.add(rangeA)
            }
        }
        return condensed.sumOf { range -> range.last - range.first + 1 }
    }
}
