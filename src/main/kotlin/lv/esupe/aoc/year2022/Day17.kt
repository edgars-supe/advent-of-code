package lv.esupe.aoc.year2022

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Direction
import lv.esupe.aoc.model.GridOld
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.repeat

fun main() = solve { Day17() }

class Day17 : Puzzle<Int, Long>(2022, 17) {
    override val input = rawInput.first()
        .asSequence()
        .repeat()
        .map { char ->
            when (char) {
                '>' -> Direction.East
                '<' -> Direction.West
                else -> error("Unknown direction $char")
            }
        }

    private val shapes = Shape.values().asSequence().repeat()

    private val width = 7
    private val startFromLeft = 2
    private val startAboveLast = 3

    override fun solvePartOne(): Int {
        val grid = GridOld('.')
        val jeterator = input.iterator()
        val shaperator = shapes.take(2022).iterator()
        while (shaperator.hasNext()) {
            val protoShape = shaperator.next()
            val startY = grid.height + startAboveLast
            val startPoint = Point(startFromLeft, startY)
            var shapePoints = protoShape.points.map { p -> p + startPoint }
            do {
                val jetDirection = jeterator.next()
                val jettedPoints = shapePoints.map { it.move(jetDirection) }
                val hasHitSomething = jettedPoints.any { it.x < 0 || it.x >= width || it in grid }
                val downablePoints = if (hasHitSomething) shapePoints else jettedPoints

                val downedPoints = downablePoints.map { it.move(Direction.South) }
                if (downedPoints.any { it in grid || it.y < 0 }) { // hit rock/floor, stop
                    grid.insert(downablePoints, '#')
                    break
                } else {
                    shapePoints = downedPoints
                }
            } while(true)
        }
        return grid.height
    }

    override fun solvePartTwo(): Long {
        return 0
    }

    enum class Shape(val points: List<Point>) {
        HLine(listOf(Point(0, 0), Point(1, 0), Point(2, 0), Point(3, 0))),
        Plus(listOf(Point(1, 0), Point(0, 1), Point(1, 1), Point(2, 1), Point(1, 2))),
        L(listOf(Point(0, 0), Point(1, 0), Point(2, 0), Point(2, 1), Point(2, 2))),
        VLine(listOf(Point(0, 0), Point(0, 1), Point(0, 2), Point(0, 3))),
        Square(listOf(Point(0, 0), Point(1, 0), Point(0, 1), Point(1, 1)))
    }
}
