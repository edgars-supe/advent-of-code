package lv.esupe.aoc.year2024

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Grid
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.Paths

fun main() = solve { Day18(endX = 70, endY = 70, simulateBits = 1024) }

class Day18(
    val endX: Int,
    val endY: Int,
    val simulateBits: Int
) : Puzzle<Int, String>(2024, 18) {
    override val input = rawInput.map { line ->
        val (x, y) = line.split(",").map { it.toInt() }
        Point(x, y)
    }
    private val start = Point(0, 0)
    private val end = Point(endX, endY)
    private val grid = Grid.blank(default = '.', invertY = false, insertDefault = false)

    private lateinit var firstPartPath: Paths.TargetResult<Point>

    init {
        grid[start] = 'S'
        grid[end] = 'E'
    }

    override fun solvePartOne(): Int {
        input.take(simulateBits)
            .forEach { grid[it] = '#' }
        firstPartPath = findPath() ?: error("No path found")
        return firstPartPath.steps
    }

    override fun solvePartTwo(): String {
        var path = firstPartPath
        input.drop(simulateBits)
            .forEach { byte ->
                grid[byte] = '#'
                if (byte in path.path) {
                    path = findPath() ?: return "${byte.x},${byte.y}"
                }
            }
        error("Solution not found")
    }

    private fun findPath(): Paths.TargetResult<Point>? = Paths.findPath(
        start = start,
        target = end,
        getValue = { it },
        getNeighbors = { point ->
            grid.getNeighbors(point, ignoreDefault = false, diagonal = false)
                .filter { (_, value) -> value != '#' }
                .map { it.first }
        }
    )
}
