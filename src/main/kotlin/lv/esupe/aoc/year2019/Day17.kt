package lv.esupe.aoc.year2019

import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve
import lv.esupe.aoc.year2019.model.Intcode
import lv.esupe.aoc.year2019.model.toProgram
import kotlin.math.abs

fun main() = solve { Day17() }

class Day17 : Puzzle<Int, Long>(2019, 17) {
    companion object {
        private const val ROUTINE = "A,B,A,C,B,C,B,A,C,B"
        private const val A = "L,10,L,6,R,10"
        private const val B = "R,6,R,8,R,8,L,6,R,8"
        private const val C = "L,10,R,8,R,8,L,10"
    }

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

    override fun solvePartTwo(): Long = runBlocking {
        val program = input.toMutableList().apply { set(0, 2L) }
        val intcode = Intcode(program)
        coroutineScope {
            launch { intcode.execute() }
            launch {
                intcode.sendLine(ROUTINE)
                intcode.sendLine(A)
                intcode.sendLine(B)
                intcode.sendLine(C)
                intcode.sendLine("n")
            }
        }
        intcode.output.consumeAsFlow().last()
    }

    private suspend fun Intcode.sendLine(line: String) {
        line.map { it.toInt() }
            .forEach { input.send(it.toLong()) }
            .also { input.send(10) }
    }
}
