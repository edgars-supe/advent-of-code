package lv.esupe.aoc.year2025

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.modulo
import kotlin.math.absoluteValue

fun main() = solve { Day1() }

class Day1 : Puzzle<Int, Int>(2025, 1) {
    override val input = rawInput.map { line ->
        val direction = if (line.first() == 'L') -1 else 1
        val amount = line.drop(1).toInt()
        amount * direction
    }

    override fun solvePartOne(): Int {
        var result = 0
        input.fold(initial = 50) { position, rotation ->
            (position + rotation)
                .modulo(100)
                .also { if (it == 0) result++ }
        }
        return result
    }

    override fun solvePartTwo(): Int {
        var result = 0
        input.fold(initial = 50) { position, rotation ->
            val fullTurns = rotation / 100
            result += fullTurns.absoluteValue
            val extraRotation = rotation % 100
            (position + extraRotation)
                .also { if (position != 0 && it !in 0 .. 100) result++ }
                .modulo(100)
                .also { if (it == 0 && extraRotation != 0 && position != 0) result++ }
        }
        return result
    }
}
