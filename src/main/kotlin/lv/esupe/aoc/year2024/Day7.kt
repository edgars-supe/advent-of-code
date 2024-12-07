package lv.esupe.aoc.year2024

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.prependTo

fun main() = solve { Day7() }

class Day7 : Puzzle<Long, Long>(2024, 7) {
    override val input = rawInput.map { line ->
        val test = line.substringBefore(":").toLong()
        val numbers = line.substringAfter(": ").split(" ").map(String::toLong)
        Eq(test, numbers)
    }

    override fun solvePartOne(): Long {
        return calibrationResult(listOf(Long::plus, Long::times))
    }

    override fun solvePartTwo(): Long {
        return calibrationResult(listOf(Long::plus, Long::times, ::concat))
    }
    
    private fun calibrationResult(operations: List<(Long, Long) -> Long>): Long {
        return input
            .filter { eq -> checkEquation(eq.test, eq.numbers, operations) }
            .sumOf { it.test }
    }

    private fun checkEquation(expected: Long, numbers: List<Long>, operations: List<(Long, Long) -> Long>): Boolean {
        if (numbers.isEmpty()) return false
        if (numbers.size == 1) return expected == numbers.first()
        val (a, b) = numbers.take(2)
        val rem = numbers.drop(2)
        return operations.any { op -> checkEquation(expected, op(a, b) prependTo rem, operations) }
    }

    private fun concat(a: Long, b: Long): Long = "$a$b".toLong()

    data class Eq(val test: Long, val numbers: List<Long>)
}
