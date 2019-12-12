package lv.esupe.aoc.year2019

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.over
import lv.esupe.aoc.year2019.model.Intcode

@ExperimentalCoroutinesApi
fun main(args: Array<String>) = solve { Day2() }

@ExperimentalCoroutinesApi
class Day2 : Puzzle<Long, Long>(2019, 2) {
    override val input = rawInput[0].split(",").map { it.toLong() }

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