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
        return simulateRope(2)
    }

    override fun solvePartTwo(): Int {
        return simulateRope(10)
    }

    private fun simulateRope(knotCount: Int): Int {
        val knots = MutableList(knotCount) { Point(0, 0) }
        val tailVisits = hashSetOf(knots.last())
        input.forEach { direction ->
            knots[0] = knots[0].move(direction)
            for (i in 1 until knotCount) {
                knots[i] = follow(knots[i], knots[i - 1])
            }
            tailVisits += knots.last()
        }
        return tailVisits.count()
    }

    private fun follow(point: Point, target: Point): Point {
        if (point != target && !point.isNeighbor(target, diagonal = true)) {
            val (dx, dy) = target.minus(point)
            return point.move(dx.coerceIn(-1..1), dy.coerceIn(-1..1))
        }
        return point
    }
}
