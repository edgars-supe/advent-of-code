package lv.esupe.aoc.year2019

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.over

fun main() = solve { Day2() }

class Day2 : Puzzle<Int, Int>(2019, 2) {
    companion object {
        private const val OPCODE_ADD = 1
        private const val OPCODE_MULTIPLY = 2
        private const val OPCODE_HALT = 99
    }

    override val input = rawInput[0].split(",").map { it.toInt() }

    override fun solvePartOne(): Int = runProgram(12, 2)

    override fun solvePartTwo(): Int {
        (0..99).over(0..99) { noun, verb ->
            val result = runProgram(noun, verb)
            if (result == 19690720) return 100 * noun + verb
        }
        return 0
    }

    private fun runProgram(noun: Int, verb: Int): Int {
        val program = input.toMutableList().apply {
            set(1, noun)
            set(2, verb)
        }
        return program.run(0)[0]
    }

    private tailrec fun MutableList<Int>.run(opcodeIdx: Int): List<Int> {
        val opcode = get(opcodeIdx)
        if (opcode == OPCODE_HALT) return this

        when (opcode) {
            OPCODE_ADD -> operandA(opcodeIdx) + operandB(opcodeIdx)
            OPCODE_MULTIPLY -> operandA(opcodeIdx) * operandB(opcodeIdx)
            else -> throw Exception("Whoops")
        }.let { set(resultIdx(opcodeIdx), it) }

        return run(opcodeIdx + 4)
    }

    private fun List<Int>.operandA(opcodeIdx: Int) = get(get(opcodeIdx + 1))

    private fun List<Int>.operandB(opcodeIdx: Int) = get(get(opcodeIdx + 2))

    private fun List<Int>.resultIdx(opcodeIdx: Int) = get(opcodeIdx + 3)
}