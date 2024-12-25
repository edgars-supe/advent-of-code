package lv.esupe.aoc.year2024

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Direction
import lv.esupe.aoc.model.Grid
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve

fun main() = solve { Day12() }

class Day12 : Puzzle<Int, Int>(2024, 12) {
    override val input = Grid.from(rawInput)

    private val regions: List<Region>

    init {
        val visited = mutableSetOf<Point>()
        regions = input
            .mapNotNull { (point, region) ->
                if (point in visited) return@mapNotNull null
                val regionPoints = mutableListOf<Point>()
                val (area, perimeter) = checkRegion(region, point, 0, 0, visited, regionPoints)
                Region(region, regionPoints, area, perimeter)
            }
    }

    override fun solvePartOne(): Int {
        return regions.sumOf { region -> region.area * region.perimeter }
    }

    override fun solvePartTwo(): Int {
        return regions.sumOf { it.area * it.sides() }
    }

    private fun checkRegion(
        region: Char,
        point: Point,
        area: Int,
        perimeter: Int,
        visited: MutableSet<Point>,
        regionPoints: MutableList<Point>
    ): Pair<Int, Int> {
        visited += point
        regionPoints += point
        val neighbors = input.getNeighbors(point, ignoreDefault = false, diagonal = false)
        val boundaryPerimeter = 4 - neighbors.size
        return neighbors
            .fold(area + 1 to perimeter + boundaryPerimeter) { (a, p), (np, nr) ->
                when {
                    nr != region -> a to p + 1
                    np !in visited -> checkRegion(region, np, a, p, visited, regionPoints)
                    else -> a to p
                }
            }
    }

    data class Region(
        val region: Char,
        val points: List<Point>,
        val area: Int,
        val perimeter: Int
    ) {
        fun sides(): Int {
            val perimeter = points
                .flatMap { point ->
                    Direction.entries
                        .map { dir -> point to dir }
                        .filterNot { (p, d) -> p.move(d) in points }
                }
            return perimeter.count { (p, d) -> p.move(d.right) to d !in perimeter }
        }
    }
}
