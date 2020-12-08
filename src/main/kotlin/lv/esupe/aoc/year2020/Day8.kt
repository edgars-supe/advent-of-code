package lv.esupe.aoc.year2020

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day8() }

class Day8 : Puzzle<Int, Int>(2020, 8) {
    override val input = rawInput.map { line ->
        val (op, value) = line.split(' ')
        Instruction(Op.valueOf(op), value.toInt())
    }

    override fun solvePartOne(): Int {
        val code = Code(input)
        val visited = mutableSetOf<Int>()
        while (visited.add(code.pointer)) {
            code.next()
        }
        return code.accumulator
    }

    override fun solvePartTwo(): Int {
        input.forEachIndexed { idx, instr ->
            val code = when (instr.op) {
                Op.jmp -> Code(input.replaceOp(idx, Op.nop))
                Op.nop -> Code(input.replaceOp(idx, Op.jmp))
                else -> null
            }
            if (code?.hasLoop() == false) return code.accumulator
        }
        error("Can't fix loop")
    }

    private fun List<Instruction>.replaceOp(index: Int, op: Op): List<Instruction> = toMutableList()
        .apply { set(index, Instruction(op, get(index).arg)) }

    private fun Code.hasLoop(): Boolean {
        val visited = mutableSetOf<Int>()
        do {
            if (!visited.add(pointer)) return true
        } while (next())
        return false
    }
}

class Code(private val instructions: List<Instruction>) {
    var pointer: Int = 0
        private set
    var accumulator: Int = 0
        private set

    fun next(): Boolean {
        if (pointer !in instructions.indices) return false

        val instruction = instructions[pointer]
        when (instruction.op) {
            Op.nop -> pointer++
            Op.acc -> {
                accumulator += instruction.arg
                pointer++
            }
            Op.jmp -> pointer += instruction.arg
        }
        return true
    }
}

data class Instruction(val op: Op, val arg: Int)

enum class Op {
    nop,
    acc,
    jmp
}
