package lv.esupe.aoc.year2022

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day4() }

class Day4 : Puzzle<Int, Int>(2022, 4) {
    override val input = rawInput.map { line ->
        val (aFrom, aTo, bFrom, bTo) = LINE_PATTERN.find(line)!!.destructured
        aFrom.toInt()..aTo.toInt() to bFrom.toInt()..bTo.toInt()
    }

    override fun solvePartOne(): Int {
        return input.count { (a, b) ->
            val bInA = a.first <= b.first && a.last >= b.last
            val aInB = b.first <= a.first && b.last >= a.last
            bInA || aInB
        }
    }

    override fun solvePartTwo(): Int {
        return input.count { (a, b) ->
            a.first <= b.last && a.last >= b.first
        }
    }

    companion object {
        val LINE_PATTERN = Regex("""(\d+)-(\d+),(\d+)-(\d+)""")
    }
}
