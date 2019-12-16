package lv.esupe.aoc.year2019

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.repeat
import lv.esupe.aoc.utils.toIntValue
import kotlin.math.absoluteValue

fun main(args: Array<String>) = solve { Day16() }

class Day16 : Puzzle<Int, Int>(2019, 16) {
    override val input = rawInput[0].map { it.toIntValue() }
    private val pattern = listOf(0, 1, 0, -1)

    override fun solvePartOne(): Int = input.runPhases(100)
        .take(8)
        .joinToString("")
        .toInt()

    override fun solvePartTwo(): Int = input.repeat(10000)
        .run {
            val offset = input.take(7).joinToString("").toInt()
            drop(offset)
        }
        .runPhasesPart2(100)
        .take(8)
        .joinToString("")
        .toInt()

    private fun List<Int>.runPhases(n: Int): List<Int> = (1..n)
        .fold(this) { acc, _ ->
            acc.mapIndexed { phase, _ ->
                acc.subphase(phase + 1)
            }
        }

    private fun List<Int>.runPhasesPart2(n: Int): List<Int> = (1..n)
        .fold(this) { acc, _ ->
            val result = MutableList(acc.size) { 0 }
            acc.indices.reversed().forEach { idx ->
                result[idx] = (acc.getOrElse(idx) { 0 } + result.getOrElse(idx + 1) { 0 }).rem(10)
            }
            result
        }

    private fun List<Int>.subphase(n: Int): Int {
        var sum = 0
        indices.forEach { index ->
            val multiplier = getMultiplier(n, index)
            if (multiplier != 0) sum += get(index) * multiplier
        }
        return sum.absoluteValue.rem(10)
    }

    private fun getMultiplier(phase: Int, index: Int): Int {
        val i = ((index + 1) / phase) % pattern.size
        return if (i == 0) pattern[0]
        else pattern[pattern.size - i]
    }
}
