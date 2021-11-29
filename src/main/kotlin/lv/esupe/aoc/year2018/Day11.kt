package lv.esupe.aoc.year2018

import kotlinx.coroutines.*
import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.over

fun main() = solve { Day11() }

class Day11 : Puzzle<String, String>(2018, 11) {
    override val input = rawInput.first().toInt()
    private val size = 300
    private val grid = Array(size) { Array(size) { null as Cell? } }

    override fun solvePartOne(): String {
        var maxArea = 0
        var topLeft: Cell? = null
        (1 until size - 1).over(1 until size - 1) { x, y ->
            var sum = 0
            (x - 1..x + 1).over(y - 1..y + 1) { x1, y1 ->
                sum += grid.getLevel(x1, y1)
            }
            if (maxArea < sum) {
                maxArea = sum
                topLeft = grid[x - 1][y - 1]
            }
        }
        return "${topLeft!!.x},${topLeft!!.y}"
    }

    override fun solvePartTwo(): String {
        return runBlocking(Dispatchers.Default) {
            val results = mutableListOf<Deferred<Result>>()
            (0 until size).forEach { i ->
                results += async { calculateAreas(i) }
            }
            results.map { it.await() }
                .maxByOrNull { it.power }!!.let {
                return@runBlocking "${it.topLeft.x},${it.topLeft.y},${it.length}"
            }
        }
    }

    private fun calculateAreas(x: Int): Result {
        var largest: Result? = null
        (0 until size).forEach { y ->
            val maxLength = minOf(size - x, size - y)
            val current = getMostPowerfulSquare(x, y, maxLength)
            largest = largest?.let {
                if (it.power < current.power) current
                else it
            } ?: current
        }
        return largest!!
    }

    private fun getMostPowerfulSquare(x: Int, y: Int, maxLength: Int): Result {
        var sum = 0
        var largest: Result? = null
        (1..maxLength).map { length ->
            forEachDepth(length) { x1, y1 ->
                sum += grid.getLevel(x + x1, y + y1)
            }
            val current = Result(grid[x][y]!!, sum, length)
            largest = largest?.let {
                if (it.power < current.power) current
                else it
            } ?: current
        }
        return largest!!
    }

    private fun Array<Array<Cell?>>.getLevel(x: Int, y: Int): Int {
        if (this[x][y] == null) this[x][y] = Cell(x + 1, y + 1)
        return this[x][y]?.level(input)!!
    }

    private fun forEachDepth(depth: Int, action: (Int, Int) -> Unit) {
        (0 until depth).forEach { y ->
            action(depth - 1, y)
        }
        (0 until depth).forEach { x ->
            if (x != depth - 1) action(x, depth - 1)
        }
    }
}

data class Result(val topLeft: Cell, val power: Int, val length: Int)

data class Cell(val x: Int, val y: Int) {
    fun level(serialNumber: Int): Int = (x + 10)
        .let { rackId ->
            rackId.times(y)
                .plus(serialNumber)
                .times(rackId)
                .div(100)
                .rem(10)
                .minus(5)
        }
}
