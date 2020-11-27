package lv.esupe.aoc.year2019

import kotlinx.coroutines.runBlocking
import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.over
import lv.esupe.aoc.year2019.model.Intcode
import lv.esupe.aoc.year2019.model.toProgram

fun main() = solve { Day19() }

class Day19 : Puzzle<Int, Int>(2019, 19) {
    override val input = rawInput[0].toProgram()

    override fun solvePartOne(): Int = runBlocking {
        val grid = mutableMapOf<Point, Boolean>()
        (0 until 50).over(0 until 50) { x, y ->
            grid[Point(x, y)] = Point(x, y).isPulled()
        }
        grid.count { (_, value) -> value }
    }

    override fun solvePartTwo(): Int = runBlocking {
        var y = 700
        while (true) {
            val bottomLeft = findFirstValidX(y)
            val topRight = Point(x = bottomLeft.x + 99, y = bottomLeft.y - 99)
            if (topRight.isPulled()) {
                return@runBlocking bottomLeft.x * 10000 + topRight.y
            } else {
                y++
            }
        }
        -1
    }

    private suspend fun findFirstValidX(y: Int): Point {
        var x = 0
        while (true) {
            val point = Point(x, y)
            if (point.isPulled()) {
                return point
            }
            else x++
            if (x > 10000) break
        }
        error("Couldn't find valid x")
    }

    private suspend fun findLastValidX(from: Point): Point {
        return if (from.right().isPulled()) findLastValidX(from.right())
        else from
    }

    private suspend fun Point.isPulled(): Boolean =
        Intcode(input).execute(inputs = listOf(x.toLong(), y.toLong())).last() == 1L
}
