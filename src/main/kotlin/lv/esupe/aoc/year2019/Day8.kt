package lv.esupe.aoc.year2019

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.asString
import lv.esupe.aoc.utils.count

fun main(args: Array<String>) = solve { Day8() }

class Day8 : Puzzle<Int, String>(2019, 8) {

    override val input = rawInput[0].chunked(25 * 6)

    override fun solvePartOne(): Int = input
        .minBy { it.count('0') }
        ?.let { it.count('1') * it.count('2') }
        ?: -1

    override fun solvePartTwo(): String = input
        .reduce { acc, layer ->
            acc.mapIndexed { idx, char ->
                if (char == '2') layer[idx]
                else char
            }
                .asString()
        }
        .map { if (it == '1') '@' else ' ' }
        .asString()
        .chunked(25)
        .fold(StringBuilder("\n")) { sb, row -> sb.appendln(row) }
        .toString()
}
