package lv.esupe.aoc.year2019

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.gcd
import kotlin.math.absoluteValue

fun main() = solve { Day10() }

class Day10 : Puzzle<Int, Int>(2019, 10) {
    override val input = rawInput
        .flatMapIndexed { col, line ->
            line.mapIndexedNotNull { row, char ->
                if (char == '#') Point(row, col)
                else null
            }
        }
        .toSet()

    override fun solvePartOne(): Int {
        val counts: List<Int> = input.map { point ->
            input.toMutableSet()
                .run {
                    remove(point)
                    filter { other -> isDirectLineOfSight(point, other, this) }
                }
                .count()
        }
        return counts.maxOrNull() ?: error("Can't find max")
    }

    override fun solvePartTwo(): Int {
        return -2
    }

    private fun isDirectLineOfSight(from: Point, to: Point, points: Set<Point>): Boolean {
        val dPoint = from.slope(to)
        var check = to
        while (check != from) {
            check -= dPoint
            if (check in points) return false
        }
        return true
    }
}
