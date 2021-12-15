package lv.esupe.aoc.year2021

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve
import java.util.PriorityQueue

fun main() = solve { Day15() }

class Day15 : Puzzle<Int, Int>(2021, 15) {
    private val width = rawInput[0].length
    private val height = rawInput.size
    private val risks: List<List<Int>> = rawInput
        .map { line ->
            line.map { risk ->
                risk.digitToInt()
            }
        }
    override val input = rawInput

    override fun solvePartOne(): Int {
        return getLowestRisk(width, height)
    }

    override fun solvePartTwo(): Int {
        return getLowestRisk(width * 5, height * 5)
    }

    private fun getLowestRisk(width: Int, height: Int): Int {
        val start = Point(0, 0)
        val target = Point(width - 1, height - 1)
        return aStar(start, target, width - 1, height - 1)
            .drop(1)
            .sumOf { getRisk(it) }
    }

    private fun aStar(start: Point, target: Point, maxX: Int, maxY: Int): List<Point> {
        val h = { n: Point -> n.distanceTo(target) }

        val fScore = mutableMapOf<Point, Int>()
        fScore[start] = h(start)

        val openSet = PriorityQueue<Point>(maxX * maxY, compareBy { point -> fScore[point] })
        openSet.add(start)

        val cameFrom = mutableMapOf<Point, Point>()
        val gScore = mutableMapOf<Point, Int>().withDefault { Int.MAX_VALUE }
        gScore[start] = 0

        while (openSet.isNotEmpty()) {
            val current = openSet.remove()
            if (current == target) return reconstructPath(cameFrom, current)

            current.neighbors().filter { p -> p.x in 0..maxX && p.y in 0..maxY }
                .forEach { neighbor ->
                    val tentative = (gScore[current] ?: Int.MAX_VALUE) + getRisk(neighbor)
                    if (tentative < (gScore[neighbor] ?: Int.MAX_VALUE)) {
                        cameFrom[neighbor] = current
                        gScore[neighbor] = tentative
                        fScore[neighbor] = tentative + h(neighbor)
                        if (neighbor !in openSet) {
                            openSet.add(neighbor)
                        }
                    }
                }
        }
        error("Path not found")
    }

    private fun reconstructPath(cameFrom: Map<Point, Point>, goal: Point): List<Point> {
        val totalPath = mutableListOf(goal)
        var current = goal
        while (current in cameFrom.keys) {
            current = cameFrom[current]!!
            totalPath.add(0, current)
        }
        return totalPath
    }

    private val riskCache: MutableMap<Point, Int> = mutableMapOf()

    private fun getRisk(point: Point): Int {
        riskCache[point]?.let { return it }

        if (point.y in risks.indices && point.x in risks[point.y].indices) {
            return risks[point.y][point.x].also { riskCache[point] = it }
        }

        val baseRisk = risks[point.y % height][point.x % width]
        val riskIncrease = point.x / width + point.y / height
        val risk = baseRisk + riskIncrease
        return (if (risk > 9) risk % 10 + 1 else risk).also { riskCache[point] = it }
    }
}
