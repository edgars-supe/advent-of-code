package lv.esupe.aoc.year2021

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import java.util.Stack

fun main() = solve { Day10() }

class Day10 : Puzzle<Int, Long>(2021, 10) {
    override val input = rawInput.map { parseLine(it) }

    override fun solvePartOne(): Int {
        return input.filterIsInstance<Line.Corrupted>()
            .sumOf { line -> line.char.corruptPoints }
    }

    override fun solvePartTwo(): Long {
        return input.filterIsInstance<Line.Incomplete>()
            .map { line ->
                line.completion
                    .map { c -> c.autoCompletePoints }
                    .reduce { acc, i -> acc * 5 + i }
            }
            .sorted()
            .let { scores -> scores[scores.size / 2] }
    }

    private fun parseLine(line: String): Line {
        val stack = Stack<Char>() // stack hold chars we expect to see
        line.forEach { c ->
            if (c.isClose) { // if c is a closing char, check if it matches the top of the stack
                val popped = stack.pop()
                if (popped != c) { // if it doesn't, it's a corrupted line and c is the erroneous char
                    return Line.Corrupted(c)
                }
            } else {
                stack.push(c.close)
            }
        }
        return Line.Incomplete(stack.reversed()) // the rest of the stack is expected closing chars
    }

    private val Char.close: Char
        get() = when (this) {
            '(' -> ')'
            '[' -> ']'
            '{' -> '}'
            '<' -> '>'
            else -> error("Unknown char '$this'")
        }

    private val Char.isClose: Boolean
        get() = when (this) {
            ')', ']', '}', '>' -> true
            else -> false
        }

    private val Char.corruptPoints: Int
        get() = when (this) {
            ')' -> 3
            ']' -> 57
            '}' -> 1197
            '>' -> 25137
            else -> error("Unknown char '$this'")
        }

    private val Char.autoCompletePoints: Long
        get() = when (this) {
            ')' -> 1L
            ']' -> 2L
            '}' -> 3L
            '>' -> 4L
            else -> error("Unknown char '$this'")
        }

    sealed interface Line {
        data class Corrupted(val char: Char) : Line
        data class Incomplete(val completion: List<Char>) : Line
    }
}
