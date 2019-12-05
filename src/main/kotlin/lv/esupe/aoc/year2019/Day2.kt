package lv.esupe.aoc.year2019

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.over

fun main(args: Array<String>) = solve { Day2() }

class Day2 : Puzzle<Int, Int>(2019, 2) {
    override val input = rawInput[0].split(",").map { it.toInt() }

    override fun solvePartOne(): Int = Intcode(input).run(12, 2)

    override fun solvePartTwo(): Int {
        (0..99).over(0..99) { noun, verb ->
            val result = Intcode(input).run(noun, verb)
            if (result == 19690720) return 100 * noun + verb
        }
        return 0
    }
}