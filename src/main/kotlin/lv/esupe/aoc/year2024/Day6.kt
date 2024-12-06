package lv.esupe.aoc.year2024

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Direction
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.toGrid

fun main() = solve { Day6() }

class Day6 : Puzzle<Int, Int>(2024, 6) {
    override val input = rawInput.toGrid(invertY = false)
    private val width = rawInput.first().length
    private val height = rawInput.size
    private val guardStart = input.entries
        .first { (_, value) -> value == GUARD }
        .key

    private lateinit var originalPath: Set<Point>

    override fun solvePartOne(): Int {
        originalPath = traverse(input, guardStart)
            .orEmpty()
            .mapTo(mutableSetOf()) { (point, _) -> point }
        return originalPath.size
    }

    override fun solvePartTwo(): Int {
        return (originalPath - guardStart).count { obstacle ->
            traverse(input, guardStart, obstacle) == null
        }
    }

    private fun traverse(map: Map<Point, Char>, start: Point, extraObstacle: Point? = null): Set<Pair<Point, Direction>>? {
        var curr = start
        var direction = Direction.South
        val path = mutableSetOf<Pair<Point, Direction>>()

        while (isInGrid(curr)) {
            val pair = curr to direction
            if (pair in path) return null
            path += pair
            val check = curr + direction.point
            if (map[check] == OBSTACLE || check == extraObstacle) {
                direction = direction.left
            } else {
                curr = check
            }
        }
        return path
    }

    private fun isInGrid(point: Point): Boolean {
        return point.x in 0 until width && point.y in 0 until height
    }

    companion object {
        private val GUARD = '^'
        private val OBSTACLE = '#'
    }
}
