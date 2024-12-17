package lv.esupe.aoc.year2024

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day17() }

class Day17 : Puzzle<String, Long>(2024, 17) {
    override val input = rawInput
    private val program = rawInput.last().substringAfter(": ").split(",").map { it.toInt() }
    private val registerA = rawInput[0].substringAfter(": ").toLong()
    private val registerB = rawInput[1].substringAfter(": ").toLong()
    private val registerC = rawInput[2].substringAfter(": ").toLong()

    override fun solvePartOne(): String {
        val computer = Computer(registerA, registerB, registerC, program)
        computer.run()
        return computer.output.joinToString(separator = ",")
    }

    override fun solvePartTwo(): Long {
        fun solve(a: Long): List<Int> {
            return Computer(a, registerB, registerC, program)
                .also(Computer::run)
                .output
        }

        var result = 0L
        for (drop in (program.size - 1) downTo 0) {
            result = result shl 3
            val target = program.drop(drop)
            while (solve(result) != target) {
                result++
            }
        }
        return result
    }

    private class Computer(
        private var a: Long,
        private var b: Long,
        private var c: Long,
        private var program: List<Int>
    ) {
        private var ip = 0
        var output = mutableListOf<Int>()
            private set

        fun run() {
            while (ip < program.size) {
                val operand = program[ip + 1]
                when (program[ip]) {
                    ADV -> { adv(combo(operand)); incp() }
                    BXL -> { bxl(operand); incp() }
                    BST -> { bst(combo(operand)); incp() }
                    JNZ -> { jnz(operand) }
                    BXC -> { bxc(); incp() }
                    OUT -> { out(combo(operand)); incp() }
                    BDV -> { bdv(combo(operand)); incp() }
                    CDV -> { cdv(combo(operand)); incp() }
                }
            }
        }

        private fun combo(operand: Int): Long {
            return when (operand) {
                in 0..3 -> operand.toLong()
                4 -> a
                5 -> b
                6 -> c
                else -> error("Invalid combo operand")
            }
        }

        private fun incp() {
            ip += 2
        }

        private fun adv(operand: Long) {
            a = a shr operand.toInt()
        }

        private fun bxl(operand: Int) {
            b = b xor operand.toLong()
        }

        private fun bst(operand: Long) {
            b = operand % 8
        }

        private fun jnz(operand: Int) {
            if (a == 0L) {
                incp()
            } else {
                ip = operand
            }
        }

        private fun bxc() {
            b = b xor c
        }

        private fun out(operand: Long) {
            output += (operand % 8).toInt()
        }

        private fun bdv(operand: Long) {
            b = a shr operand.toInt()
        }

        private fun cdv(operand: Long) {
            c = a shr operand.toInt()
        }

        companion object {
            private const val ADV = 0
            private const val BXL = 1
            private const val BST = 2
            private const val JNZ = 3
            private const val BXC = 4
            private const val OUT = 5
            private const val BDV = 6
            private const val CDV = 7
        }
    }
}
