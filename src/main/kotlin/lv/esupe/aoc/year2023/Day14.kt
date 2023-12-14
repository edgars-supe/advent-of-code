package lv.esupe.aoc.year2023

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.toGrid

fun main() = solve { Day14() }

class Day14 : Puzzle<Int, Int>(2023, 14) {
    override val input = rawInput.toGrid()
    private val height: Int = rawInput.size
    private val width: Int = rawInput[0].length
    private val cubes = input.filter { (_, c) -> c ==  '#' }.keys

    override fun solvePartOne(): Int {
        val rounds = input.filter { (_, c) -> c == 'O' }.keys.toMutableSet()
        tiltNorth(rounds)
        return rounds.sumOf { it.weight() }
    }

    override fun solvePartTwo(): Int {
        val rounds = input.filter { (_, c) -> c == 'O' }.keys.toMutableSet()
        val cache = mutableMapOf(rounds.toSet() to 0)
        for (cycle in 1..1_000_000_000) {
            tiltNorth(rounds)
            tiltWest(rounds)
            tiltSouth(rounds)
            tiltEast(rounds)
            val cycleForState = cache.getOrPut(rounds.toSet()) { cycle }
            if (cycleForState != cycle) {
                return cache.keys
                    .elementAt(cycleForState + (1_000_000_000 - cycle) % (cycle - cycleForState))
                    .sumOf { it.weight() }
            }

        }
        error("Not found")
    }

    private fun tiltNorth(rounds: MutableSet<Point>) {
        (0 until width).forEach { col ->
            var lastOccupiedRow = -1
            (0 until height).forEach { row ->
                when (val point = Point(col, row)) {
                    in rounds -> {
                        val newRow = ++lastOccupiedRow
                        rounds.remove(point)
                        rounds.add(Point(point.x, newRow))
                    }
                    in cubes -> lastOccupiedRow = row
                }
            }
        }
    }

    private fun tiltWest(rounds: MutableSet<Point>): MutableSet<Point> {
        (0 until height).forEach { row ->
            var lastOccupiedCol = -1
            (0 until width).forEach { col ->
                when (val point = Point(col, row)) {
                    in rounds -> {
                        val newCol = ++lastOccupiedCol
                        rounds.remove(point)
                        rounds.add(Point(newCol, point.y))
                    }
                    in cubes -> lastOccupiedCol = col
                }
            }
        }
        return rounds
    }

    private fun tiltSouth(rounds: MutableSet<Point>): MutableSet<Point> {
        (0 until width).reversed().forEach { col ->
            var lastOccupiedRow = height
            (0 until height).reversed().forEach { row ->
                when (val point = Point(col, row)) {
                    in rounds -> {
                        val newRow = --lastOccupiedRow
                        rounds.remove(point)
                        rounds.add(Point(point.x, newRow))
                    }
                    in cubes -> lastOccupiedRow = row
                }
            }
        }
        return rounds
    }

    private fun tiltEast(rounds: MutableSet<Point>): MutableSet<Point> {
        (0 until height).forEach { row ->
            var lastOccupiedCol = width
            (0 until width).reversed().forEach { col ->
                when (val point = Point(col, row)) {
                    in rounds -> {
                        val newCol = --lastOccupiedCol
                        rounds.remove(point)
                        rounds.add(Point(newCol, point.y))
                    }
                    in cubes -> lastOccupiedCol = col
                }
            }
        }
        return rounds
    }

    private fun Point.weight(): Int {
        return height - y
    }

    private fun print(rounds: Set<Point>) {
        (0 until height).forEach { y ->
            (0 until width).forEach { x ->
                when (Point(x, y)) {
                    in rounds -> print('O')
                    in cubes -> print('#')
                    else -> print('.')
                }
            }
            println()
        }
        println()
    }
}
