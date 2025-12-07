package lv.esupe.aoc.year2025

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Grid
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.memoize

fun main() = solve { Day7() }

class Day7 : Puzzle<Int, Long>(2025, 7) {
    override val input = Grid.from(rawInput)
    private val start = input.firstNotNullOf { it.takeIf { it.value == 'S' } }
    private val numTimelines = memoize(::_numTimelines)

    override fun solvePartOne(): Int {
        val queue = mutableSetOf(start.key)
        var splits = 0
        while (queue.isNotEmpty()) {
            val p = queue.first()
            queue.remove(p)
            val newP = p.up()
            if (input[newP] == '^') {
                queue.add(newP.left())
                queue.add(newP.right())
                splits++
            } else if (input.isInBounds(newP)) {
                queue.add(newP)
            }
        }
        return splits
    }

    override fun solvePartTwo(): Long {
        return numTimelines(start.key)
    }

    private fun _numTimelines(from: Point): Long {
        val newP = from.up()
        return when {
            input[newP] == '^' -> numTimelines(newP.left()) + numTimelines(newP.right())
            input.isInBounds(newP) -> numTimelines(newP)
            else -> 1L
        }
    }
}
