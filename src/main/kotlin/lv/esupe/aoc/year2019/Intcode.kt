package lv.esupe.aoc.year2019

import kotlin.math.pow

class Intcode(private val program: List<Int>, val input: Int = 0) {
    companion object {
        private const val OPCODE_ADD = 1
        private const val OPCODE_MULTIPLY = 2
        private const val OPCODE_INPUT = 3
        private const val OPCODE_OUTPUT = 4
        private const val OPCODE_JUMP_IF_TRUE = 5
        private const val OPCODE_JUMP_IF_FALSE = 6
        private const val OPCODE_LESS_THAN = 7
        private const val OPCODE_EQUALS = 8
        private const val OPCODE_HALT = 99
        private const val MODE_PARAMETER = 0
        private const val MODE_IMMEDIATE = 1
    }

    fun run(
        noun: Int = program.getOrElse(1) { 0 },
        verb: Int = program.getOrElse(2) { 0 }
    ): Int = program.toMutableList()
        .apply {
            set(1, noun)
            set(2, verb)
        }
        .run(0)[0]

    private tailrec fun MutableList<Int>.run(opcodeIdx: Int): List<Int> {
        val opcode = get(opcodeIdx)
        if (opcode.isHalt()) return this

        val jump = when {
            opcode.isAdd() -> {
                val result = operandA(opcode, opcodeIdx) + operandB(opcode, opcodeIdx)
                val resultIdx = resultIdx(opcodeIdx)
                set(resultIdx, result)
                4
            }
            opcode.isMultiply() -> (operandA(opcode, opcodeIdx) * operandB(opcode, opcodeIdx)).let { set(resultIdx(opcodeIdx), it) }.let { 4 }
            opcode.isInput() -> set(get(opcodeIdx + 1), input).let { 2 }
            opcode.isOutput() -> get(get(opcodeIdx + 1)).let { set(0, it) }.let { 2 }
            opcode.isJumpIfTrue() -> {
                if (operandA(opcode, opcodeIdx) != 0) {
                    val jumpTo = operandB(opcode, opcodeIdx)
                    //set(opcodeIdx, operandB(opcode, opcodeIdx))
                    jumpTo - opcodeIdx
                } else {
                    4
                }
            }
            opcode.isJumpIfFalse() -> {
                if (operandA(opcode, opcodeIdx) == 0) {
                    operandB(opcode, opcodeIdx) - opcodeIdx
                } else {
                    4
                }
            }
            opcode.isLessThan() -> {
                val resultIdx = resultIdx(opcodeIdx)
                if (operandA(opcode, opcodeIdx) < operandB(opcode, opcodeIdx)) set(resultIdx, 1)
                else set(resultIdx, 0)

                if (resultIdx == opcodeIdx) get(opcodeIdx) else 4
            }
            opcode.isEquals() -> {
                val resultIdx = resultIdx(opcodeIdx)
                if (operandA(opcode, opcodeIdx) == operandB(opcode, opcodeIdx)) set(resultIdx, 1)
                else set(resultIdx, 0)
                if (resultIdx == opcodeIdx) get(opcodeIdx) else 4
            }
            else -> throw Exception("Whoops, OP_$opcode@$opcodeIdx")
        }

        return run(opcodeIdx + jump)
    }

    private fun Int.getParameterMode(param: Int) =
        (this / 10.0.pow(param + 2.0).toInt()) and MODE_IMMEDIATE

    private fun Int.isAdd() = this.rem(100) == OPCODE_ADD

    private fun Int.isMultiply() = this.rem(100) == OPCODE_MULTIPLY

    private fun Int.isInput() = this.rem(100) == OPCODE_INPUT

    private fun Int.isOutput() = this.rem(100) == OPCODE_OUTPUT

    private fun Int.isJumpIfTrue() = this.rem(100) == OPCODE_JUMP_IF_TRUE

    private fun Int.isJumpIfFalse() = this.rem(100) == OPCODE_JUMP_IF_FALSE

    private fun Int.isLessThan() = this.rem(100) == OPCODE_LESS_THAN

    private fun Int.isEquals() = this.rem(100) == OPCODE_EQUALS

    private fun Int.isHalt() = this.rem(100) == OPCODE_HALT

    private fun List<Int>.operandA(opcode: Int, opcodeIdx: Int): Int =
        when (opcode.getParameterMode(0)) {
            MODE_PARAMETER -> get(get(opcodeIdx + 1))
            MODE_IMMEDIATE -> get(opcodeIdx + 1)
            else -> error("Incorrect parameter mode, ${opcode.getParameterMode(1)}")
        }

    private fun List<Int>.operandB(opcode: Int, opcodeIdx: Int): Int =
        when (opcode.getParameterMode(1)) {
            MODE_PARAMETER -> get(get(opcodeIdx + 2))
            MODE_IMMEDIATE -> get(opcodeIdx + 2)
            else -> error("Incorrect parameter mode, ${opcode.getParameterMode(2)}")
        }

    private fun List<Int>.resultIdx(opcodeIdx: Int) = get(opcodeIdx + 3)
}