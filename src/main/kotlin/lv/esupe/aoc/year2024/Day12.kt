package lv.esupe.aoc.year2024

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Grid
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve

fun main() = solve { Day12() }

class Day12 : Puzzle<Int, Int>(2024, 12) {
    override val input = Grid.from(rawInput)

    override fun solvePartOne(): Int {
        val visited = mutableSetOf<Point>()
        return input
            .mapNotNull { (point, region) ->
                if (point in visited) return@mapNotNull null
                val (area, perimeter) = checkRegion(region, point, 0, 0, visited)
                Region(region, area, perimeter)
            }
            .sumOf { region -> region.area * region.perimeter }
    }

    override fun solvePartTwo(): Int {
        return 0
    }

    private fun checkRegion(
        region: Char,
        point: Point,
        area: Int,
        perimeter: Int,
        visited: MutableSet<Point>
    ): Pair<Int, Int> {
        visited += point
        val neighbors = input.getNeighbors(point, ignoreDefault = false, diagonal = false)
        val boundaryPerimeter = 4 - neighbors.size
        return neighbors
            .fold(area + 1 to perimeter + boundaryPerimeter) { (a, p), (np, nr) ->
                when {
                    nr != region -> a to p + 1
                    np !in visited -> checkRegion(region, np, a, p, visited)
                    else -> a to p
                }
            }
    }

    data class Region(
        val region: Char,
        val area: Int,
        val perimeter: Int
    )
}
