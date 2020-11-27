package lv.esupe.aoc.year2019

import kotlinx.coroutines.runBlocking
import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.over
import lv.esupe.aoc.year2019.model.Intcode
import lv.esupe.aoc.year2019.model.toProgram

fun main() = solve { Day2() }

class Day2 : Puzzle<Long, Long>(2019, 2) {
    override val input = rawInput[0].toProgram()

    override fun solvePartOne(): Long = runProgram(12, 2)

    override fun solvePartTwo(): Long {
        (0L..99L).over(0L..99L) { noun, verb ->
            val result = runProgram(noun, verb)
            if (result == 19690720L) return 100 * noun + verb
        }
        return 0
    }

    private fun runProgram(noun: Long, verb: Long): Long = runBlocking {
        Intcode(input).execute(noun, verb)
    }
}
