package lv.esupe.aoc.year2024

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.memoize

fun main() = solve { Day19() }

class Day19 : Puzzle<Int, Long>(2024, 19) {
    override val input = rawInput

    private val patternMap = input.first()
        .split(", ")
        .groupBy { it.first() }
    private val towels = input.drop(2)

    private val canCreate: (String) -> Boolean = memoize(::_canCreate)
    private val waysToCreate: (String) -> Long = memoize(::_waysToCreate)

    override fun solvePartOne(): Int {
        return towels.count(canCreate)
    }

    override fun solvePartTwo(): Long {
        return towels.sumOf(waysToCreate)
    }

    private fun _canCreate(design: String): Boolean {
        if (design.isEmpty()) return true
        return patternMap[design.first()]
            ?.any { pattern ->
                design.startsWith(pattern) && canCreate(design.drop(pattern.length))
            }
            ?: false
    }

    private fun _waysToCreate(design: String): Long {
        if (design.isEmpty()) return 1L
        return patternMap[design.first()]
            ?.filter { design.startsWith(it) }
            ?.sumOf { pattern -> waysToCreate(design.drop(pattern.length)) }
            ?: 0L
    }
}
