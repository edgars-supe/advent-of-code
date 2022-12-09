package lv.esupe.aoc.year2022

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Direction
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve

fun main() = solve { Day9() }

class Day9 : Puzzle<Int, Int>(2022, 9) {
    override val input = rawInput.flatMap { line ->
        val (d, s) = line.split(' ')
        val direction = when (d) {
            "L" -> Direction.West
            "U" -> Direction.North
            "R" -> Direction.East
            "D" -> Direction.South
            else -> error("Unknown direction $d")
        }
        List(s.toInt()) { direction }
    }

    override fun solvePartOne(): Int {
        var head = Point(0, 0)
        var tail = Point(0, 0)
        val tailVisits = hashSetOf(tail)
        input.forEach { direction ->
            head = head.move(direction)
            tail = follow(tail, head)
            tailVisits += tail
        }
        return tailVisits.count()
    }

    override fun solvePartTwo(): Int {
        val points = MutableList(10) { Point(0, 0) }
        val tailVisits = hashSetOf(points.last())
        input.forEach { direction ->
            points[0] = points[0].move(direction)
            points.indices.zipWithNext { targetIdx, pointIdx ->
                points[pointIdx] = follow(points[pointIdx], points[targetIdx])
            }
            tailVisits += points.last()
        }
        return tailVisits.count()
    }

    private fun follow(point: Point, target: Point): Point {
        if (point != target && !point.isNeighbor(target, diagonal = true)) {
            val (dx, dy) = point.slope(target)
            return point.move(dx.coerceIn(-1..1), dy.coerceIn(-1..1))
        }
        return point
    }
}
