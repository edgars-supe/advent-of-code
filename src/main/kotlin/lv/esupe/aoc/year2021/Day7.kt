package lv.esupe.aoc.year2021

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

fun main() = solve { Day7() }

class Day7 : Puzzle<Int, Int>(2021, 7) {
    override val input = rawInput[0].split(",").map { it.toInt() }

    override fun solvePartOne(): Int {
        val sorted = input.sorted()
        val size = sorted.size
        val isEven = size % 2 == 0
        val median = if (isEven) {
            val p = size / 2
            (sorted[p - 1] + sorted[p]) / 2
        } else {
            sorted[(size + 1) / 2]
        }
        return sorted.sumOf { position -> (position - median).absoluteValue }
    }

    override fun solvePartTwo(): Int {
        return (input.minOf { it }..input.maxOf { it })
            .minOf { target ->
                input.sumOf { position ->
                    val moves = (position - target).absoluteValue
                    moves * (moves + 1) / 2
                }
            }
    }
}
