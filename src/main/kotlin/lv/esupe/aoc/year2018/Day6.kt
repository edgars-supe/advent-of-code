package lv.esupe.aoc.year2018

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.countOf
import lv.esupe.aoc.utils.over
import kotlin.math.absoluteValue


fun main(args: Array<String>) = solve { Day6() }

class Day6 : Puzzle<Int, Int>(2018, 6) {
    override val input = rawInput
        .map { it.split(", ") }
        .map { Point(it[0].toInt(), it[1].toInt()) }
    val minX = input.minBy { it.x }!!.x
    val minY = input.minBy { it.y }!!.y
    val maxX = input.maxBy { it.x }!!.x
    val maxY = input.maxBy { it.y }!!.y

    override fun solvePartOne(): Int {
        val map = mutableMapOf<Point, Int>()
        val edges = mutableSetOf<Point>()
        (minX..maxX).over(minY..maxY) { x, y ->
            val point = input.nearestTo(x, y)
            if (point != null) {
                if (isOnEdge(x, y)) edges.add(point)
                if (point !in edges) {
                    map[point] = map.getOrDefault(point, 0) + 1
                }
            }
        }
        return map.values.max()!!
    }

    override fun solvePartTwo(): Int {
        var count = 0
        (minX..maxX).over(minY..maxY) { x, y ->
            input.fold(0) { acc, point -> acc + point.distanceTo(x, y) }
                .takeIf { it < 10000 }
                ?.let { count++ }

        }
        return count
    }

    private fun isOnEdge(x: Int, y: Int) =
        x == minX || y == minY || x == maxX || y == maxY
}

private fun List<Point>.nearestTo(x: Int, y: Int) =
    map { it.distanceTo(x, y) }
        .let { list ->
            val min = list.min()?.takeIf { list.countOf(it) == 1 }
            min?.let { list.indexOf(it) }
                ?.let { get(it) }
        }

data class Point(
    val x: Int,
    val y: Int
) {
    fun distanceTo(x: Int, y: Int): Int {
        val dx = (x - this.x).absoluteValue
        val dy = (y - this.y).absoluteValue
        return dx + dy
    }
}