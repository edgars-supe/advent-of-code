package lv.esupe.aoc.year2019

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.model.asString
import lv.esupe.aoc.solve
import lv.esupe.aoc.year2019.model.Intcode
import lv.esupe.aoc.year2019.model.toProgram
import kotlin.math.abs

@ExperimentalCoroutinesApi
fun main(args: Array<String>) = solve { Day17() }

@ExperimentalCoroutinesApi
class Day17 : Puzzle<Int, Int>(2019, 17) {
    override val input = rawInput[0].toProgram()
    private val grid = mutableMapOf<Point, Char>()

    override fun solvePartOne(): Int = runBlocking {
        val intcode = Intcode(input)
        coroutineScope {
            launch { intcode.execute() }
            launch {
                var x = 0
                var y = 0
                intcode.output.consumeEach { out ->
                    if (out == 10L) {
                        x = 0
                        y--
                    } else {
                        grid[Point(x++, y)] = out.toChar()
                    }
                }
            }
        }

        println(grid.asString { c -> "$c" })

        grid.filter { (point, char) ->
            if (char == '#') {
                listOf(
                    point.up(),
                    point.left(),
                    point.right(),
                    point.down()
                )
                    .all { grid[it] == '#' }
            } else {
                false
            }
        }
            .map { (point, _) -> point.x * abs(point.y) }
            .sum()
    }

    override fun solvePartTwo(): Int = 0

}