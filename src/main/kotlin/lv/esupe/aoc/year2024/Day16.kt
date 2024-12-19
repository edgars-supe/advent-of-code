package lv.esupe.aoc.year2024

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Direction
import lv.esupe.aoc.model.Grid
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.Paths
import lv.esupe.aoc.utils.at

fun main() = solve { Day16() }

class Day16 : Puzzle<Int, Int>(2024, 16) {
    override val input = Grid.from(rawInput, insertDefault = false)
    private val start = input.entries.first { (_, char) -> char == 'S' }.key
    private val end = input.entries.first { (_, char) -> char == 'E' }.key

    override fun solvePartOne(): Int {
        return Paths.weightedDijkstra(
            start = start,
            target = end,
            getValue = { it },
            getNeighbors = { point ->
                        input.getNeighbors(point, ignoreDefault = false, diagonal = false)
                            .filter { (_, char) -> char != '#' }
                            .map { (point, _) -> point }
            },
            cost = { path, node ->
                when (path.size) {
                    0 -> 0
                    1 -> if ((node - start) == Direction.East.point) 1 else 1001
                    else -> {
                        val dir = path.at(-2) - path.at(-1)
                        if (path.at(-1) - node == dir) 1 else 1001
                    }
                }
            }
        )
            .distances[end] ?: error("Distance not found")
    }

    override fun solvePartTwo(): Int {
        return 0
    }
}
