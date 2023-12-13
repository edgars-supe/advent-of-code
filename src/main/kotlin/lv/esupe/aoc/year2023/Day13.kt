package lv.esupe.aoc.year2023

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.chunkedBy
import kotlin.math.min

fun main() = solve { Day13() }

class Day13 : Puzzle<Int, Int>(2023, 13) {
    override val input = rawInput.chunkedBy { it.isBlank() }

    override fun solvePartOne(): Int {
        val verticalReflections = input.mapNotNull { pattern -> findVerticalReflection(pattern) }
            .sumOf { (a, _) -> a + 1}
        val horizontalReflections = input.mapNotNull { pattern -> findHorizontalReflection(pattern) }
            .sumOf { (a, _) -> (a + 1) * 100 }
        return verticalReflections + horizontalReflections
    }

    override fun solvePartTwo(): Int {
        return 0
    }

    private fun findHorizontalReflection(pattern: List<String>): Pair<Int, Int>? {
        return pattern.indices
            .zipWithNext()
            .filter { (a, b) -> pattern[a] == pattern[b] }
            .firstOrNull { checkHorizontalReflection(pattern, it) }
    }

    private fun checkHorizontalReflection(pattern: List<String>, rows: Pair<Int, Int>): Boolean {
        val (a, b) = rows
        val dCheck = min(a, pattern.lastIndex - b)
        return (1..dCheck).all { d ->
            pattern[a - d] == pattern[b + d]
        }
    }

    private fun findVerticalReflection(pattern: List<String>): Pair<Int, Int>? {
        return pattern[0].indices
            .zipWithNext()
            .filter { (a, b) -> pattern.all { p -> p[a] == p[b] } }
            .firstOrNull { checkVerticalReflection(pattern, it) }
    }

    private fun checkVerticalReflection(pattern: List<String>, columns: Pair<Int, Int>): Boolean {
        val (a, b) = columns
        val dCheck = min(a, pattern[0].lastIndex - b)
        return (1..dCheck).all { d ->
            pattern.all { p -> p[a - d] == p[b + d] }
        }
    }
}
