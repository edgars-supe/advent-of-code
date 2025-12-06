package lv.esupe.aoc.year2025

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.chunkedBy
import lv.esupe.aoc.utils.transposed

fun main() = solve { Day6() }

class Day6 : Puzzle<Long, Long>(2025, 6) {
    override val input = rawInput

    override fun solvePartOne(): Long {
        val lines = input.map { line -> line.trim().split(Regex("\\s+")) }
        val operands = (0 until lines.lastIndex)
            .map { row -> lines[row].map { it.toLong() } }
        val ops = lines.last()

        return ops.indices
            .sumOf { idx ->
                val op: Long.(Long) -> Long = if (ops[idx] == "*") Long::times else Long::plus
                operands
                    .map { it[idx] }
                    .reduce(op)
            }
    }

    override fun solvePartTwo(): Long {
        return input.transposed()
            .chunkedBy { it.isBlank() }
            .sumOf { lines ->
                val op: Long.(Long) -> Long = if (lines.first().last() == '*') Long::times else Long::plus
                lines
                    .map { line -> line.extractNumber() }
                    .reduce(op)
            }
    }

    private fun String.extractNumber(): Long {
        return fold(0) { acc, i ->
            if (i.isDigit()) acc * 10 + i.digitToInt() else acc
        }
    }
}
