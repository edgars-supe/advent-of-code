package lv.esupe.aoc.year2021

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve

fun main() = solve { Day9() }

class Day9 : Puzzle<Int, Int>(2021, 9) {
    override val input: Map<Point, Int> = rawInput
        .mapIndexed { rowIdx, line ->
            line.mapIndexed { colIdx, char -> Point(rowIdx, colIdx) to char.digitToInt() }
                .toMap()
        }
        .fold(mutableMapOf()) { acc, map -> acc.apply { putAll(map) } }

    override fun solvePartOne(): Int {
        return input.filter { (point, height) ->
            val neighbors = point.neighbors().map { neighbor -> input[neighbor] }
            neighbors.all { neighbor -> neighbor == null || neighbor > height }
        }
            .values
            .let { heights -> heights.sum() + heights.size }
    }

    override fun solvePartTwo(): Int {
        val map = input.filterValues { it != 9 }.toMutableMap()
        val basins = mutableListOf<Int>() // size of basin
        while (map.isNotEmpty()) {
            val point = map.keys.first()
            basins += countBasinPoints(map, point)
        }
        return basins.sortedDescending()
            .take(3)
            .reduce { acc, i -> acc * i }
    }

    private fun countBasinPoints(map: MutableMap<Point, Int>, point: Point): Int {
        map.remove(point)
        val neighbors = point.neighbors().filter { it in map.keys }
        neighbors.forEach { map.remove(it) }
        return 1 + neighbors.sumOf { countBasinPoints(map, it) }
    }
}
