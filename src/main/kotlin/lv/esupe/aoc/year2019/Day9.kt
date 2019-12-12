package lv.esupe.aoc.year2019

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.last
import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.year2019.model.Intcode

@ExperimentalCoroutinesApi
fun main(args: Array<String>) = solve { Day9() }

@ExperimentalCoroutinesApi
class Day9 : Puzzle<Long, Long>(2019, 9) {

    override val input = rawInput[0].split(",").map { it.toLong() }

    override fun solvePartOne(): Long = runProgram(1)

    override fun solvePartTwo(): Long = runProgram(2)

    private fun runProgram(value: Long): Long = runBlocking {
        Intcode(input).run {
            input(value)
            execute()
            output.last()
        }
    }
}
