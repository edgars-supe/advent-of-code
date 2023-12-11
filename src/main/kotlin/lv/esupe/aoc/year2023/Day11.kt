package lv.esupe.aoc.year2023

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.PointL
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.mapAllPairs

fun main() = solve { Day11(expansionRate = 1_000_000) }

class Day11(private val expansionRate: Long) : Puzzle<Long, Long>(2023, 11) {
    override val input = rawInput
        .flatMapIndexed { y, line ->
            line.mapIndexedNotNull { x, char ->
                if (char != '.') PointL(x.toLong(), y.toLong())
                else null
            }
        }
    private val emptyRows = rawInput
        .mapIndexedNotNull { idx, line -> idx.takeIf { line.all { it == '.' } } }
    private val emptyCols = (0 until rawInput[0].length)
        .filter { col -> rawInput.all { line -> line[col] == '.' } }

    override fun solvePartOne(): Long {
        return solve(expansionRate = 1)
    }

    override fun solvePartTwo(): Long {
        return solve(expansionRate - 1)
    }

    private fun solve(expansionRate: Long): Long {
        val adjustedGalaxies = input.map { point ->
            val plusX = emptyCols.takeWhile { it < point.x }.count() * expansionRate
            val plusY = emptyRows.takeWhile { it < point.y }.count() * expansionRate
            point.move(plusX, plusY)
        }
        return adjustedGalaxies
            .mapAllPairs { p1, p2 -> p1.distanceTo(p2) }
            .sum()
    }
}
