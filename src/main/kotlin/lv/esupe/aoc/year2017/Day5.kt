package lv.esupe.aoc.year2017

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.utils.asString
import kotlin.math.absoluteValue
import kotlin.math.pow

fun main(args: Array<String>) {
    Day5Puzzle1().calculateAndPrint()
    Day5Puzzle2().calculateAndPrint()
}

class Day5Puzzle1 : Puzzle<Int>(2017, 5, 1) {
    override fun calculate(): Int =
        input.map { it.toInt() }
            .toMutableList()
            .getSteps { it + 1 }
}

class Day5Puzzle2 : Puzzle<Int>(2017, 5, 2) {
    override fun calculate(): Int =
        input.map { it.toInt() }
            .toMutableList()
            .getSteps { if (it >= 3) it - 1 else it + 1 }
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