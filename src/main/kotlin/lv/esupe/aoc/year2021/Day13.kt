package lv.esupe.aoc.year2021

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.chunkedBy

fun main() = solve { Day13() }

class Day13 : Puzzle<Int, String>(2021, 13) {
    override val input = rawInput
    private val dots: Set<Point>
    private val folds: List<Fold>

    init {
        val (dots, folds) = input.chunkedBy { it.isBlank() }

        this.dots = dots
            .map { line ->
                val (x, y) = line.split(",").map { it.toInt() }
                Point(x, y)
            }
            .toSet()

        this.folds = folds.map { line ->
            val (instr, at) = line.split("=")
            when (instr.last()) {
                'x' -> Fold.Vertical(at.toInt())
                'y' -> Fold.Horizontal(at.toInt())
                else -> error("Unknown fold \"$instr=$at\"")
            }
        }
    }

    override fun solvePartOne(): Int {
        return dots.fold(folds.first()).count()
    }

    override fun solvePartTwo(): String {
        val result = folds.fold(dots) { dots, fold -> dots.fold(fold) }
        val maxX = result.maxOf { it.x }
        val maxY = result.maxOf { it.y }
        return (0..maxY).joinToString(prefix = "\n", separator = "\n") { y ->
            (0..maxX).joinToString(separator = "") { x ->
                if (result.any { p -> p.x == x && p.y == y}) "#" else "."
            }
        }
    }

    private fun Set<Point>.fold(fold: Fold): Set<Point> {
        return map { point ->
            when (fold) {
                is Fold.Vertical -> {
                    if (point.x > fold.at) point.copy(x = -point.x + 2 * fold.at)
                    else point
                }
                is Fold.Horizontal -> {
                    if (point.y > fold.at) point.copy(y = -point.y + 2 * fold.at)
                    else point
                }
            }
        }
            .toSet()
    }

    sealed class Fold(val at: Int) {
        class Vertical(at: Int) : Fold(at)
        class Horizontal(at: Int) : Fold(at)
    }
}
