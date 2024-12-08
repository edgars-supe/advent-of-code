package lv.esupe.aoc.year2024

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Grid
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.getAllPairs

fun main() = solve { Day8() }

class Day8 : Puzzle<Int, Int>(2024, 8) {
    override val input = Grid.from(rawInput, insertDefault = true)

    private val frequencies = input
        .filter { (_, value) -> value != '.' }
        .entries
        .groupBy({ it.value }, { it.key })

    private val antennaPairs = frequencies.flatMap { (_, antennas) -> antennas.getAllPairs() }

    override fun solvePartOne(): Int = countAntinodes(infinite = false)

    override fun solvePartTwo(): Int = countAntinodes(infinite = true)

    private fun countAntinodes(infinite: Boolean): Int {
        val antinodes = mutableSetOf<Point>()
        antennaPairs.forEach { (a, b) ->
            findAntinodes(a, a - b, input, antinodes, infinite)
            findAntinodes(b, b - a, input, antinodes, infinite)
        }
        return antinodes.count()
    }

    private fun findAntinodes(
        start: Point,
        diff: Point,
        grid: Grid<*>,
        antinodes: MutableSet<Point>,
        infinite: Boolean
    ) {
        var curr = if (infinite) start else start + diff
        while (grid.isInBounds(curr)) {
            antinodes += curr
            if (!infinite) return
            curr += diff
        }
    }
}
