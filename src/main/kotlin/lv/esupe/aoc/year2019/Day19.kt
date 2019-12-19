package lv.esupe.aoc.year2019

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.over
import lv.esupe.aoc.year2019.model.Intcode
import lv.esupe.aoc.year2019.model.toProgram

@ExperimentalCoroutinesApi
fun main(args: Array<String>) = solve { Day19() }

@ExperimentalCoroutinesApi
class Day19 : Puzzle<Int, Int>(2019, 19) {
    override val input = rawInput[0].toProgram()
    private val grid = mutableMapOf<Point, Long>()

    override fun solvePartOne(): Int = runBlocking {
        (0 until 50).over(0 until 50) { x, y ->
            val intcode = Intcode(input)
            coroutineScope {
                launch { intcode.execute() }
                launch {
                    intcode.input.send(x.toLong())
                    intcode.input.send(y.toLong())
                    if (!intcode.output.isClosedForReceive) {
                        val result = intcode.output.receive()
                        grid[Point(x, y)] = result
                    }
                }
            }
        }
        grid.count { (_, value) -> value == 1L }
    }

    override fun solvePartTwo(): Int = 0

}