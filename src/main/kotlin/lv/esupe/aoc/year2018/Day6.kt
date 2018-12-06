package lv.esupe.aoc.year2018

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.utils.countOf
import lv.esupe.aoc.utils.forEachIndexed
import kotlin.math.absoluteValue


fun main(args: Array<String>) {
    Day6().solve()
}

class Day6 : Puzzle<Int, Int>(2018, 6) {
    override val input = rawInput
        .map { it.split(", ") }
        .map { Point(it[0].toInt(), it[1].toInt()) }
    val size = input.map { maxOf(it.x, it.y) }.max()!!

    override fun solvePartOne(): Int {
        val grid = Array(size) { Array<Point?>(size) { null } }
        val edges = mutableSetOf<Point>()
        grid.forEachIndexed { i, j, _ ->
            val point = input.nearestTo(Point(i, j))
            if (point != null) {
                if (i == 0 || j == 0 || i == size - 1 || j == size - 1) edges.add(point)
            }
            if (point !in edges) grid[i][j] = point
        }

        val map = mutableMapOf<Point, Int>()
        grid.forEachIndexed { i, j, point ->
            point?.let {
                map[point] = map.getOrDefault(point, 0) + 1
            }
        }
        return map.values.max()!!
    }

    override fun solvePartTwo(): Int {
        var count = 0
        for (i in 0 until size) {
            for (j in 0 until size) {
                val p = Point(i, j)
                input.fold(0) { acc, point -> acc + point.distanceTo(p) }
                    .takeIf { it < 10000}
                    ?.let { count++ }
            }
        }
        return count
    }
}

private fun List<Point>.nearestTo(point: Point) =
    map { point.distanceTo(it) }
        .let { list ->
            val min = list.min()?.takeIf { list.countOf(it) == 1 }
            min?.let { list.indexOf(it) }
                ?.let { get(it) }
        }


data class Point(
    val x: Int,
    val y: Int
) {
    fun distanceTo(point: Point): Int {
        val dx = (x - point.x).absoluteValue
        val dy = (y - point.y).absoluteValue
        return dx + dy
    }
}