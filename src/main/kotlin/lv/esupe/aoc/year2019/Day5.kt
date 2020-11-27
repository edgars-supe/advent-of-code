package lv.esupe.aoc.year2019

import kotlinx.coroutines.runBlocking
import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.year2019.model.Intcode
import lv.esupe.aoc.year2019.model.toProgram

fun main() = solve { Day5() }

class Day5 : Puzzle<Long, Long>(2019, 5) {

    override val input = rawInput[0].toProgram()

    override fun solvePartOne(): Long = runProgram(1)

    override fun solvePartTwo(): Long = runProgram(5)

    private fun runProgram(value: Long): Long = runBlocking {
        Intcode(input).execute(input = value).last()
    }
}
