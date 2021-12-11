package lv.esupe.aoc.year2021

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve

fun main() = solve { Day11() }

class Day11 : Puzzle<Int, Int>(2021, 11) {
    override val input: Map<Point, Int> = rawInput
        .mapIndexed { rowIdx, line ->
            line.mapIndexed { colIdx, level -> Point(rowIdx, colIdx) to level.digitToInt() }
        }
        .flatten()
        .toMap()

    override fun solvePartOne(): Int {
        val grid = input.toMutableMap()
        return (0 until 100).fold(0) { acc, _ ->
            val flashers = mutableSetOf<Point>()
            grid.forEach { (point, _) -> increaseLevel(grid, flashers, point) }
            acc + flashers.size
        }
    }

    override fun solvePartTwo(): Int {
        val grid = input.toMutableMap()
        var step = 1
        while (true) {
            val flashers = mutableSetOf<Point>()
            grid.forEach { (point, _) -> increaseLevel(grid, flashers, point) }
            if (grid.all { (_, level) -> level == 0 }) {
                return step
            }
            step++
        }
    }

    private fun increaseLevel(grid: MutableMap<Point, Int>, flashers: MutableSet<Point>, point: Point) {
        val level = grid[point] ?: return
        if (point in flashers) return

        val newLevel = level + 1
        if (newLevel > 9) {
            flashers += point
            grid[point] = 0
            point.neighbors(diagonal = true)
                .forEach { p -> increaseLevel(grid, flashers, p) }
        } else {
            grid[point] = newLevel
        }
    }
}
