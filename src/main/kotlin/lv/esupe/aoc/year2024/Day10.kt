package lv.esupe.aoc.year2024

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Grid
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.Paths

fun main() = solve { Day10() }

class Day10 : Puzzle<Int, Int>(2024, 10) {
    override val input = Grid.from(rawInput, default = null) { c -> c.digitToInt() }
    private val trailheads = input.filterValues { it == 0 }
    private val niners = input.filterValues { it == 9 }
    private val trails: List<Trail>

    init {
        trails = trailheads.entries
            .flatMap { (trailhead, _) ->
                niners.mapNotNull { (niner, _) ->
                    findPaths(trailhead, niner)
                        .takeIf { it.isNotEmpty() }
                        ?.let { paths -> Trail(trailhead, niner, paths) }
                }
            }
    }

    override fun solvePartOne(): Int = trails.count()

    override fun solvePartTwo(): Int = trails.sumOf { it.paths.size }

    private fun findPaths(trailhead: Point, niner: Point): List<List<Point>> {
        return Paths.findAllPaths(trailhead, niner) { point ->
            point.neighbors(diagonal = false)
                .filter { p ->
                    val value = input[p]
                    value != null && value == input[point]?.plus(1)
                }
        }
    }

    data class Trail(
         val trailhead: Point,
         val niner: Point,
         val paths: List<List<Point>>
    )
}
