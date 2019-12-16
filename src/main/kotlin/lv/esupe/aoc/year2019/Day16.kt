package lv.esupe.aoc.year2019

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.asInfiniteSequence
import lv.esupe.aoc.utils.toIntValue
import lv.esupe.aoc.utils.trim
import kotlin.math.absoluteValue

fun main(args: Array<String>) = solve { Day16() }

class Day16 : Puzzle<Int, Int>(2019, 16) {
    override val input = rawInput[0].map { it.toIntValue() }

    override fun solvePartOne(): Int = input.runPhases(100)
        .take(8)
        .joinToString("")
        .toInt()

    override fun solvePartTwo(): Int = 0 /*input.repeat(10000).runPhases(100)
        .run {
            val offset = take(7).joinToString("").toInt()
            subList(offset, offset + 8)
        }
        .joinToString("")
        .toInt()*/


    private fun List<Int>.runPhases(n: Int): List<Int> = (1..n)
        .fold(this) { acc, _ ->
            acc.mapIndexed { phase, _ -> acc.phase(phase + 1) }
        }

    private fun List<Int>.phase(n: Int): Int {
        val phase = generatePhase(n).take(size).toList()
        return zip(phase) { a, b -> a * b }.sum().absoluteValue.trim(1)
    }

    private fun generatePhase(n: Int): Sequence<Int> = listOf(0, 1, 0, -1)
        .flatMap { value -> List(n) { value } }
        .asInfiniteSequence()
        .drop(1)
}