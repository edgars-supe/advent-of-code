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
    }

    private fun aStar(start: Point, target: Point, maxX: Int, maxY: Int): Int {
        val gScore = mutableMapOf<Point, Int>().withDefault { Int.MAX_VALUE }
        gScore[start] = 0

        val openSet = PriorityQueue<Point>(maxX * maxY, compareBy { point -> gScore.getValue(point) })
        openSet.add(start)

        while (openSet.isNotEmpty()) {
            val current = openSet.remove()
            if (current == target) return gScore.getValue(target)

            current.neighbors().filter { p -> p.x in 0..maxX && p.y in 0..maxY }
                .forEach { neighbor ->
                    val tentative = gScore.getValue(current) + getRisk(neighbor)
                    if (tentative < gScore.getValue(neighbor)) {
                        gScore[neighbor] = tentative
                        openSet.add(neighbor)
                    }
                }
        }
        error("Path not found")
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
