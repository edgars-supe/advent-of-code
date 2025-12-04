package lv.esupe.aoc.year2025

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Grid
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve

fun main() = solve { Day4() }

class Day4 : Puzzle<Int, Int>(2025, 4) {
    override val input = Grid.from(rawInput)

    override fun solvePartOne(): Int {
        return input.count { (p, _) -> p.canAccess() }
    }

    override fun solvePartTwo(): Int {
        var canRemove = 0
        var canAccess = input.filter { (p, _) -> p.canAccess() }
        while (canAccess.isNotEmpty()) {
            canRemove += canAccess.size
            canAccess.forEach { (p, _) -> input.remove(p) }
            canAccess = input.filter { (p, _) -> p.canAccess() }
        }
        return canRemove
    }

    private fun Point.canAccess(): Boolean {
        return neighbors(diagonal = true).count { n -> input[n] == '@' } < 4
    }
}
