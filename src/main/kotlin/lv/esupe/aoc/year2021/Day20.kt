package lv.esupe.aoc.year2021

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve

fun main() = solve { Day20() }

class Day20 : Puzzle<Int, Int>(2021, 20) {
    override val input = rawInput
    private val algorithm: List<Boolean> = rawInput[0].map { c -> c.toBoolean() }
    private val image: Set<Point> = rawInput.drop(2)
        .flatMapIndexed { y, line ->
            line.mapIndexedNotNull { x, c ->
                if (c == '#') Point(x, y) else null
            }
        }
        .toSet()
    private val cache: MutableMap<Int, MutableMap<Point, Boolean>> = mutableMapOf()

    private val minX = 0
    private val maxX = rawInput[2].length
    private val minY = 0
    private val maxY = rawInput.drop(2).size

    override fun solvePartOne(): Int {
        return countLitPixels(steps = 2)
    }

    override fun solvePartTwo(): Int {
        return countLitPixels(steps = 50)
    }

    private fun countLitPixels(steps: Int): Int {
        val delta = steps * 2
        return ((minX - delta)..(maxX + delta)).sumOf { x ->
            ((minY - delta)..(maxY + delta)).count { y ->
                isLit(step = steps, point = Point(x, y))
            }
        }
    }

    private fun isLit(step: Int, point: Point): Boolean {
        return when {
            step == 0 -> point in image
            step in cache && cache[step]?.containsKey(point) == true -> cache[step]?.get(point) ?: error("Bad cache hit")
            else -> {
                listOf(
                    point.move(-1, -1), point.move(0, -1), point.move(1, -1),
                    point.move(-1, 0), point, point.move(1, 0),
                    point.move(-1, 1), point.move(0, 1), point.move(1, 1),
                )
                    .map { p -> if (isLit(step - 1, p)) 1 else 0 }
                    .fold(0) { acc, i -> acc shl 1 or i }
                    .let { index -> algorithm[index] }
                    .also { isLit -> cache.getOrPut(step) { mutableMapOf() }[point] = isLit }
            }
        }
    }

    private fun Char.toBoolean(): Boolean {
        return when (this) {
            '.' -> false
            '#' -> true
            else -> error("Unknown char '$this'")
        }
    }
}
