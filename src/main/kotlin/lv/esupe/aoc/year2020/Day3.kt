package lv.esupe.aoc.year2020

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.model.asString
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.toGrid

fun main() = solve { Day3() }

class Day3 : Puzzle<Long, Long>(2020, 3) {
    override val input = rawInput.toGrid()
    private val width = input.keys.maxOf { it.x } + 1
    private val height = input.keys.maxOf { it.y } + 1

    override fun solvePartOne(): Long = getTreesForSlope(Point(3, 1))

    override fun solvePartTwo(): Long = listOf(Point(1, 1), Point(3, 1), Point(5, 1), Point(7, 1), Point(1, 2))
        .map { getTreesForSlope(it) }
        .reduce { acc, i -> acc * i }

    private fun getTreesForSlope(slope: Point): Long {
        var trees = 0L
        var curr = Point(0, 0)
        while (curr.y < height) {
            curr += slope
            if (curr.x >= width) {
                curr = curr.copy(x = curr.x % width)
            }
            if (input[curr] == '#') trees++
        }
        return trees
    }
}
