package lv.esupe.aoc.year2023

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.Paths
import lv.esupe.aoc.utils.toGrid

fun main() = solve { Day10() }

class Day10 : Puzzle<Int, Int>(2023, 10) {
    override val input = rawInput.toGrid()

    override fun solvePartOne(): Int {
        val start = input.entries.first { (_, c) -> c == 'S' }.key
        val result = Paths.findShortestPaths(start) { p -> getNeighbors(p) }
        return result.distances.maxOf { (_, dist) -> dist }
    }

    override fun solvePartTwo(): Int {
        return 0
    }

    private fun getNeighbors(point: Point): Collection<Point> {
        val char = input.getValue(point)
        return if (char == 'S') {
            getNeighborsForStart(point)
        } else {
            val pipe = Pipe.fromChar(char)
            listOf(point + pipe.a, point + pipe.b)
        }
    }

    private fun getNeighborsForStart(point: Point): Collection<Point> {
        val neighbors = point.neighbors(diagonal = false)
        return neighbors
            .asSequence()
            .mapNotNull { p -> input[p]?.let { p to it } }
            .filter { (_, c) -> c != '.' }
            .filter { (p, c) ->
                val pipe = Pipe.fromChar(c)
                point == p + pipe.a || point == p + pipe.b
            }
            .map { (p, _) -> p }
            .toList()
    }

    enum class Pipe(val char: Char, val a: Point, val b: Point) {
        Vertical('|', Point(0, -1), Point(0, 1)),
        Horizontal('-', Point(1, 0), Point(-1, 0)),
        NE('L', Point(0, -1), Point(1, 0)),
        NW('J', Point(0, -1), Point(-1, 0)),
        SW('7', Point(0, 1), Point(-1, 0)),
        SE('F', Point(0, 1), Point(1, 0));

        companion object {
            fun fromChar(char: Char) = entries.first { it.char == char }
        }
    }
}
