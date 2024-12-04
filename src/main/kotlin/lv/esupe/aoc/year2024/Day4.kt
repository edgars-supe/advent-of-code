package lv.esupe.aoc.year2024

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.toGrid

fun main() = solve { Day4() }

class Day4 : Puzzle<Int, Int>(2024, 4) {
    override val input = rawInput.toGrid()

    override fun solvePartOne(): Int {
        return input.asSequence()
            .filter { (_, value) -> value == 'X' }
            .sumOf { (point, _) -> checkXmas(point) }
    }

    override fun solvePartTwo(): Int {
        return input.count { (point, _) -> checkMas(point) }
    }

    private fun checkXmas(point: Point): Int {
        // assumes `point` is checked to be 'X' already
        return NEIGHBOR_DIRECTIONS.count { dir -> checkXmas(point, dir) }
    }

    private fun checkXmas(point: Point, direction: Point): Boolean {
        // assumes `point` is checked to be 'X' already
        val mPoint = point + direction
        val aPoint = mPoint + direction
        val sPoint = aPoint + direction
        return input[mPoint] == 'M' && input[aPoint] == 'A' && input[sPoint] == 'S'
    }

    private fun checkMas(point: Point): Boolean {
        if (input[point] != 'A') return false
        val ul = point + UL
        val ur = point + UR
        val ll = point + LL
        val lr = point + LR
        return (input[ul] == 'M' && input[lr] == 'S' || input[ul] == 'S' && input[lr] == 'M') &&
            (input[ur] == 'M' && input[ll] == 'S' || input[ur] == 'S' && input[ll] == 'M')
    }

    companion object {
        private val UL = Point(-1, 1)
        private val UR = Point(1, 1)
        private val LL = Point(-1, -1)
        private val LR = Point(1, -1)
        private val NEIGHBOR_DIRECTIONS = Point(0, 0).neighbors(diagonal = true)
    }
}
