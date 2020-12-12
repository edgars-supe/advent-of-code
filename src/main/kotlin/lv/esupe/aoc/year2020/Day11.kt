package lv.esupe.aoc.year2020

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.toGrid

fun main() = solve { Day11() }

class Day11 : Puzzle<Int, Int>(2020, 11) {
    companion object {
        private const val EMPTY = 'L'
        private const val TAKEN = '#'
        private const val FLOOR = '.'
    }

    override val input = rawInput.toGrid()

    override fun solvePartOne(): Int {
        val layouts = mutableSetOf<Map<Point, Char>>()
        var layout = input
        while (layouts.add(layout)) {
            layout = layout.advance()
        }
        return layout.count { (_, char) -> char == TAKEN }
    }

    override fun solvePartTwo(): Int {
        val layouts = mutableSetOf<Map<Point, Char>>()
        var layout = input
        while (layouts.add(layout)) {
            layout = layout.advance2()
        }
        return layout.count { (_, char) -> char == TAKEN }
    }

    private fun Map<Point, Char>.advance(): Map<Point, Char> {
        return mapValues { (point, char) ->
            when (char) {
                EMPTY -> if (point.neighbors(diagonal = true).none { getOrDefault(it, FLOOR) == TAKEN }) TAKEN else EMPTY
                TAKEN -> if (point.neighbors(diagonal = true).count { getOrDefault(it, FLOOR) == TAKEN } >= 4) EMPTY else TAKEN
                else -> char
            }
        }
    }

    private fun Map<Point, Char>.advance2(): Map<Point, Char> {
        return mapValues { (point, char) ->
            when (char) {
                EMPTY -> {
                    val n = point.neighbors(diagonal = true).map { find(point, point.slope(it)) }
                    if (n.none { it == TAKEN }) TAKEN else EMPTY
                }
                TAKEN -> {
                    val n = point.neighbors(diagonal = true).map { find(point, point.slope(it)) }
                    if (n.count { it == TAKEN } >= 5) EMPTY else TAKEN
                }
                else -> char
            }
        }
    }

    private fun Map<Point, Char>.find(from: Point, slope: Point): Char {
        var current = from + slope
        var c = get(current)
        while (c != null) {
            if (c != FLOOR) return c
            else current += slope
            c = get(current)
        }
        return FLOOR
    }
}
