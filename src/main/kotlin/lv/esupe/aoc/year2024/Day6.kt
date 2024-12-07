package lv.esupe.aoc.year2024

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Direction
import lv.esupe.aoc.model.Grid
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve

fun main() = solve { Day6() }

class Day6 : Puzzle<Int, Int>(2024, 6) {
    override val input = Grid.from(rawInput, invertY = true, insertDefault = false)
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
        var direction = Direction.North
        val path = mutableSetOf<Pair<Point, Direction>>()

        while (input.isInBounds(curr)) {
            val pair = curr to direction
            if (pair in path) return null
            path += pair
            val check = curr.move(direction)
            if (map[check] == OBSTACLE || check == extraObstacle) {
                direction = direction.right
            } else {
                curr = check
            }
        }
        return path
    }

    companion object {
        private val GUARD = '^'
        private val OBSTACLE = '#'
    }
}
