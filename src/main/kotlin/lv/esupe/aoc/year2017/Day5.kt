package lv.esupe.aoc.year2017

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.asString
import kotlin.math.absoluteValue
import kotlin.math.pow

fun main(args: Array<String>) = solve { Day5() }

class Day5 : Puzzle<Int, Int>(2017, 5) {
    override val input = rawInput.map { it.toInt() }.toMutableList()

    override fun solvePartOne(): Int =
        input.getSteps { it + 1 }

    override fun solvePartTwo(): Int =
        input.getSteps { if (it >= 3) it - 1 else it + 1 }
}

private fun MutableList<Int>.getSteps(transformInstruction: (Int) -> Int): Int {
    var idx = 0
    var steps = 0
    while (idx in 0 until size) {
        steps++
        val step = get(idx)
        set(idx, transformInstruction(step))
        idx += step
    }
    return steps
}