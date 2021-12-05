package lv.esupe.aoc.year2021

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.model.asString
import lv.esupe.aoc.solve

fun main() = solve { Day5() }

class Day5 : Puzzle<Int, Int>(2021, 5) {
    // x1,y1 -> x2,y2
    override val input = rawInput
        .map { line -> Line.parse(line) }
        .partition { line -> line.from.x == line.to.x || line.from.y == line.to.y }
    private val straightLines = input.first
    private val diagonalLines = input.second
    private val grid = mutableMapOf<Point, Int>()

    override fun solvePartOne(): Int {
        straightLines.forEach { line -> mapLine(grid, line) }
        return grid.count { (_, times) -> times >= 2 }
    }

    override fun solvePartTwo(): Int {
        diagonalLines.forEach { line -> mapLine(grid, line) }
        return grid.count { (_, times) -> times >= 2 }
    }

    private fun mapLine(grid: MutableMap<Point, Int>, line: Line) {
        line.linePoints.forEach { point ->
            grid[point] = grid[point]?.plus(1) ?: 1
        }
    }

    data class Line(
        val from: Point,
        val to: Point
    ) {
        companion object {
            fun parse(s: String): Line {
                return s.split(" -> ")
                    .map { p -> p.split(",").let { (x, y) -> Point(x.toInt(), y.toInt()) } }
                    .let { (from, to) -> Line(from, to) }
            }
        }

        val linePoints = from.pointsTo(to)
    }
}
