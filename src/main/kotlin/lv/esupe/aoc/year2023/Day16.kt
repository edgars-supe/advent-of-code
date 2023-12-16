package lv.esupe.aoc.year2023

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve

fun main() = solve { Day16() }

class Day16 : Puzzle<Int, Int>(2023, 16) {
    override val input = rawInput
    private val width = input[0].length
    private val height = input.size

    override fun solvePartOne(): Int {
        return countEnergizedTiles(Point(0, 0), EAST)
    }

    private fun countEnergizedTiles(start: Point, direction: Point): Int {
        val visited = mutableSetOf<Pair<Point, Point>>()
        val queue = ArrayDeque<Pair<Point, Point>>()
        queue.add(start to direction)
        traverse(queue, visited)
        return visited
            .map { (p, _) -> p }
            .toSet()
            .size
    }

    override fun solvePartTwo(): Int {
        return buildList {
            (0 until width).forEach { x -> add(Point(x, 0) to SOUTH) }
            (0 until height).forEach { y ->
                add(Point(0, y) to EAST)
                add(Point(width - 1, y) to WEST)
            }
            (0 until width).forEach { x -> add(Point(x, height - 1) to NORTH) }
        }
            .maxOf { (start, direction) -> countEnergizedTiles(start, direction) }
    }

    private fun traverse(queue: ArrayDeque<Pair<Point, Point>>, visited: MutableSet<Pair<Point, Point>>) {
        while (queue.isNotEmpty()) {
            val (point, direction) = queue.removeFirst()
            if (!checkBounds(point)) continue
            if (!visited.add(point to direction)) continue

            when (input[point.y][point.x]) {
                '.' -> queue.add(point + direction to direction)
                '/' -> {
                    val newDirection = when (direction) {
                        NORTH -> EAST
                        EAST -> NORTH
                        SOUTH -> WEST
                        WEST -> SOUTH
                        else -> error("")
                    }
                    queue.add(point + newDirection to newDirection)
                }
                '\\' -> {
                    val newDirection = when (direction) {
                        NORTH -> WEST
                        EAST -> SOUTH
                        WEST -> NORTH
                        SOUTH -> EAST
                        else -> error("")
                    }
                    queue.add(point + newDirection to newDirection)
                }
                '|' -> {
                    if (direction == NORTH || direction == SOUTH) {
                        queue.add(point + direction to direction)
                    } else {
                        queue.add(point + NORTH to NORTH)
                        queue.add(point + SOUTH to SOUTH)
                    }
                }
                '-' -> {
                    if (direction == EAST || direction == WEST) {
                        queue.add(point + direction to direction)
                    } else {
                        queue.add(point + EAST to EAST)
                        queue.add(point + WEST to WEST)
                    }
                }
            }
        }
    }

    private fun checkBounds(point: Point): Boolean {
        return point.x in 0 until width && point.y in 0 until height
    }

    companion object {
        val NORTH = Point(0, -1)
        val EAST = Point(1, 0)
        val WEST = Point(-1, 0)
        val SOUTH = Point(0, 1)
    }
}
