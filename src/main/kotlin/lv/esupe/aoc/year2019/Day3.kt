package lv.esupe.aoc.year2019

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Direction
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve

fun main(args: Array<String>) = solve { Day3() }

class Day3 : Puzzle<Int, Int>(2019, 3) {

    override val input = rawInput.map { it.split(",").map { path -> Path(path) } }

    private val wire1 = input.first().traverse()
    private val wire2 = input.last().traverse()
    private val crossingPoints = wire1.intersect(wire2)

    override fun solvePartOne(): Int = crossingPoints.map { it.distanceTo(0, 0) }.min()!!

    override fun solvePartTwo(): Int = crossingPoints
        .map { wire1.indexOf(it) + wire2.indexOf(it) + 2}
        .min()!!

    private fun List<Path>.traverse(): List<Point> {
        var current = Point(0, 0)
        return flatMap { path ->
            path.getTraversedPoints(current).also { current = it.last() }
        }
    }

    private fun Path.getTraversedPoints(from: Point): List<Point> =
        (1..length).map { i -> from.moveBy(direction, i) }

    data class Path(
        val direction: Direction,
        val length: Int
    ) {
        constructor(path: String) : this(path[0].direction(), path.drop(1).toInt())
    }
}

private fun Char.direction() = when (this) {
    'R' -> Direction.West
    'U' -> Direction.North
    'L' -> Direction.East
    'D' -> Direction.South
    else -> error("Invalid direction")
}
