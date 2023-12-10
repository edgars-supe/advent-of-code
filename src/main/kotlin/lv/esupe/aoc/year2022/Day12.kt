package lv.esupe.aoc.year2022

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.Dijkstra

fun main() = solve { Day12() }

class Day12 : Puzzle<Int, Int>(2022, 12) {
    override val input = rawInput
        .flatMapIndexed { y, line ->
            line.mapIndexed { x, char -> Point(x, y) to char }
        }
        .toMap()

    override fun solvePartOne(): Int {
        val start = input.entries.first { (_, char) -> char == 'S' }.key
        return djikstra(start, 'E') { current, adjacent -> adjacent - current <= 1 }
    }

    override fun solvePartTwo(): Int {
        val end = input.entries.first { (_, char) -> char == 'E' }.key
        return djikstra(end, 'a') { current, adjacent -> adjacent - current >= -1 }
    }

    private fun djikstra(start: Point, target: Char, checkHeight: (current: Int, adjacent: Int) -> Boolean): Int {
        return Dijkstra
            .findPath(
                start = start,
                target = target,
                getValue = { input.getValue(it) },
                getNeighbors = { point ->
                    val currentHeight = input.getValue(point).height()
                    point.neighbors(diagonal = false)
                        .mapNotNull { n ->
                            val adjacentHeight = input[n]?.height()
                            n.takeIf { adjacentHeight != null && checkHeight(currentHeight, adjacentHeight) }
                        }
                }
            )
            .steps
    }

    private fun Char.height() = when (this) {
        'S' -> 'a'.code
        'E' -> 'z'.code
        else -> code
    }
}
