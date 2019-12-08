package lv.esupe.aoc.year2019

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.permute

fun main(args: Array<String>) = solve { Day7() }

class Day7 : Puzzle<Int, Int>(2019, 7) {

    override val input = rawInput[0].split(",").map { it.toInt() }

    override fun solvePartOne(): Int = permute(listOf(0, 1, 2, 3, 4))
        .map { perm ->
            (0..4).fold(0) { inp, i ->
                Intcode(input, mutableListOf(perm[i], inp)).run()
            }
        }
        .max()!!

    override fun solvePartTwo(): Int = 0
}
