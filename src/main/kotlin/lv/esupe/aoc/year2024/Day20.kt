package lv.esupe.aoc.year2024

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Grid
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.Paths
import lv.esupe.aoc.utils.forAllPairs

fun main() = solve { Day20(picosecondsToSavePart1 = 100, picosecondsToSavePart2 = 100) }

class Day20(
    private val picosecondsToSavePart1: Int,
    private val picosecondsToSavePart2: Int
) : Puzzle<Int, Int>(2024, 20) {
    override val input = Grid.from(rawInput)

    private val start = input.firstNotNullOf { (point, char) -> point.takeIf { char == 'S' } }
    private val end = input.firstNotNullOf { (point, char) -> point.takeIf { char == 'E' } }

    private val trackPath = Paths
        .findPath(
            start = start,
            target = end,
            getValue = { it },
            getNeighbors = { point ->
                input.getNeighbors(point, ignoreDefault = false, diagonal = false)
                    .filter { (_, char) -> char != '#' }
                    .map { (point, _) -> point }
            }
        )!!
        .path
    private var part1 = 0
    private var part2 = 0

    init {
        trackPath.forAllPairs { point, point2 ->
            val dist = point.distanceTo(point2)
            if (dist in 2..20) {
                val idx1 = trackPath.indexOf(point)
                val idx2 = trackPath.indexOf(point2)
                val saved = idx2 - idx1 - dist
                if (saved >= picosecondsToSavePart1 && dist == 2) part1++
                if (saved >= picosecondsToSavePart2) part2++
            }
        }
    }

    override fun solvePartOne(): Int {
        return part1
    }

    override fun solvePartTwo(): Int {
        return part2
    }
}
