package lv.esupe.aoc.year2019

import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Direction
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.model.asString
import lv.esupe.aoc.solve
import lv.esupe.aoc.year2019.model.Intcode

fun main() = solve { Day11() }

class Day11 : Puzzle<Int, String>(2019, 11) {
    companion object {
        private const val BLACK = 0L
        private const val WHITE = 1L
    }

    override val input = rawInput[0].split(",").map { it.toLong() }

    override fun solvePartOne(): Int = runBlocking {
        mutableMapOf(Point(0, 0) to BLACK).run {
            paintHull(this)
            size
        }
    }

    override fun solvePartTwo(): String = runBlocking {
        mutableMapOf(Point(0, 0) to WHITE).run {
            paintHull(this)
            printHull(this)
        }
    }

    private suspend fun paintHull(hull: MutableMap<Point, Long>) {
        val intcode = Intcode(input)
        var direction = Direction.North
        var position = hull.keys.first()
        intcode.input.send(hull.getPanel(position))
        coroutineScope {
            launch {
                var color = true
                intcode.output.consumeEach { out ->
                    if (color) {
                        hull[position] = out
                        color = false
                    } else {
                        direction = if (out == 0L) direction.left else direction.right
                        position = position.move(direction)
                        color = true
                        intcode.input.send(hull.getPanel(position))
                    }
                }
            }
            launch { intcode.execute() }
        }
    }

    private fun printHull(hull: MutableMap<Point, Long>): String =
        hull.asString() { x, y -> hull.getPanel(Point(x, y)).toString() }
            .replace('0', ' ')
            .replace('1', '@')

    private fun MutableMap<Point, Long>.getPanel(position: Point) = getOrPut(position) { BLACK }
}
