package lv.esupe.aoc.year2019

import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.year2019.model.Intcode
import lv.esupe.aoc.year2019.model.toProgram
import java.awt.Point
import kotlin.math.sign

fun main() = solve { Day13() }

class Day13 : Puzzle<Int, Long>(2019, 13) {

    override val input = rawInput[0].toProgram()
    private val screen: MutableMap<Point, Long> = mutableMapOf()

    override fun solvePartOne(): Int = runBlocking {
        playGame(input)
        screen.values.count { it == 2L }
    }

    override fun solvePartTwo(): Long = runBlocking {
        val program = input.toMutableList().apply { set(0, 2L) }
        playGame(program) {
            val paddleX = screen.entries.find { (_, value) -> value == 3L }?.key?.x ?: 0
            val ballX = screen.entries.find { (_, value) -> value == 4L }?.key?.x ?: 0
            (ballX - paddleX).sign.toLong()
        }
        screen[Point(-1, 0)] ?: -1L
    }

    private suspend fun playGame(program: List<Long>, movePaddle: (suspend () -> Long)? = null) = coroutineScope {
        val intcode = Intcode(program)
        intcode.onInput = { movePaddle?.invoke()?.let { send(it) } }
        launch { intcode.execute() }
        launch {
            var phase = 0
            var x = 0
            var y = 0
            intcode.output.consumeEach { out ->
                when (phase) {
                    0 -> x = out.toInt()
                    1 -> y = out.toInt()
                    2 -> screen[Point(x, y)] = out
                }
                phase = (phase + 1) % 3
            }
        }
    }
}
