package lv.esupe.aoc.year2022

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.Djikstra

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

    private fun djikstra(start: Point, target: Char, checkElevation: (current: Int, adjacent: Int) -> Boolean): Int {
        return Djikstra.findShortestDistance(
            start = start,
            target = target,
            getValue = { input.getValue(it) },
            getNeighbors = { point ->
                point.neighbors(diagonal = false)
                    .filter { input.containsKey(it) }
                    .map { p1 -> p1 to input.getValue(p1) }
                    .filter { (_, char) ->
                        val currentElevation = input.getValue(point).elevation()
                        val adjacentElevation = char.elevation()
                        checkElevation(currentElevation, adjacentElevation)
                    }
                    .map { (point, _) -> point }
            }
        )
    }

    private fun Char.elevation() = when (this) {
        'S' -> 'a'.code
        'E' -> 'z'.code
        else -> code
    }
}
