package lv.esupe.aoc.year2019

import kotlin.math.pow

class Intcode(program: List<Int>, val input: MutableList<Int> = mutableListOf()) {
    companion object {
        private const val MODE_PARAMETER = 0
        private const val MODE_IMMEDIATE = 1
    }

    constructor(program: List<Int>, input: Int) : this(program, mutableListOf(input))

    private val program = program.toMutableList()
    private var inputIdx = 0

    fun run(
        noun: Int = program.getOrElse(1) { 0 },
        verb: Int = program.getOrElse(2) { 0 }
    ): Int = program.toMutableList()
        .apply {
            set(1, noun)
            set(2, verb)
        }
        .run(0)

    private tailrec fun MutableList<Int>.run(opcodeIdx: Int): Int {
        val opcode = get(opcodeIdx)
        if (Op.Halt.isOp(opcode)) return first()

        val jump = when {
            Op.Add.isOp(opcode) -> {
                val result = param1(opcode, opcodeIdx) + param2(opcode, opcodeIdx)
                set(resultIdx(opcodeIdx), result)
                4
            }
            Op.Multiply.isOp(opcode) -> {
                val result = param1(opcode, opcodeIdx) * param2(opcode, opcodeIdx)
                set(resultIdx(opcodeIdx), result)
                4
            }
            Op.Input.isOp(opcode) -> {
                set(get(opcodeIdx + 1), input[inputIdx++])
                2
            }
            Op.Output.isOp(opcode) -> {
                set(0, param1(opcode, opcodeIdx))
                2
            }
            Op.JumpIfTrue.isOp(opcode) -> {
                if (param1(opcode, opcodeIdx) != 0) param2(opcode, opcodeIdx) - opcodeIdx
                else 3
            }
            Op.JumpIfFalse.isOp(opcode) -> {
                if (param1(opcode, opcodeIdx) == 0) param2(opcode, opcodeIdx) - opcodeIdx
                else 3
            }
            Op.LessThan.isOp(opcode) -> {
                val resultIdx = resultIdx(opcodeIdx)
                if (param1(opcode, opcodeIdx) < param2(opcode, opcodeIdx)) set(resultIdx, 1)
                else set(resultIdx, 0)
                4
            }
            Op.Equals.isOp(opcode) -> {
                val resultIdx = resultIdx(opcodeIdx)
                if (param1(opcode, opcodeIdx) == param2(opcode, opcodeIdx)) set(resultIdx, 1)
                else set(resultIdx, 0)
                4
            }
            else -> throw Exception("Whoops, OP_$opcode@$opcodeIdx")
        }

        return run(opcodeIdx + jump)
    }

    private fun Int.getParameterMode(param: Int) =
        (this / 10.0.pow(param + 2.0).toInt()) and MODE_IMMEDIATE

    private fun List<Int>.param1(opcode: Int, opcodeIdx: Int): Int =
        when (opcode.getParameterMode(0)) {
            MODE_PARAMETER -> get(get(opcodeIdx + 1))
            MODE_IMMEDIATE -> get(opcodeIdx + 1)
            else -> error("Incorrect parameter mode, ${opcode.getParameterMode(1)}")
        }

    private fun List<Int>.param2(opcode: Int, opcodeIdx: Int): Int =
        when (opcode.getParameterMode(1)) {
            MODE_PARAMETER -> get(get(opcodeIdx + 2))
            MODE_IMMEDIATE -> get(opcodeIdx + 2)
            else -> error("Incorrect parameter mode, ${opcode.getParameterMode(2)}")
        }

    private fun List<Int>.resultIdx(opcodeIdx: Int) = get(opcodeIdx + 3)

    private enum class Op(val opcode: Int) {
        Add(1),
        Multiply(2),
        Input(3),
        Output(4),
        JumpIfTrue(5),
        JumpIfFalse(6),
        LessThan(7),
        Equals(8),
        Halt(99)
        ;

        fun isOp(code: Int) = code.rem(100) == opcode
    }
}
