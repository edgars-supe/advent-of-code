package lv.esupe.aoc.year2022

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve

fun main() = solve { Day8() }

class Day8 : Puzzle<Int, Int>(2022, 8) {
    override val input: Map<Point, Int> = rawInput
        .flatMapIndexed { y, line ->
            line.mapIndexed { x, height ->
                Point(x, y) to height.digitToInt()
            }
        }
        .toMap()

    private val width = rawInput.first().length
    private val height = rawInput.size

    override fun solvePartOne(): Int {
        val edges = input.keys
            .filter { (x, y) -> x == 0 || x == width - 1 || y == 0 || y == height - 1 }
            .toSet()
        return input.keys
            .minus(edges)
            .count { isTreeVisible(it) }
            .plus(edges.count())
    }

    override fun solvePartTwo(): Int {
        return input
            .filterKeys { (x, y) -> x != 0 && x != width - 1 && y != 0 && y != height - 1 }
            .maxOf { (point, treeHeight) -> getScenicScore(point, treeHeight) }
    }

    private fun isTreeVisible(point: Point): Boolean {
        val treeHeight = input.getValue(point)
        val isInvisibleFromLeft = (point.x - 1 downTo 0).any { x ->
            input.getValue(Point(x, point.y)) >= treeHeight
        }
        val isInvisibleFromTop = (point.y - 1 downTo 0).any { y ->
            input.getValue(Point(point.x, y)) >= treeHeight
        }
        val isInvisibleFromRight = (point.x + 1 until width).any { x ->
            input.getValue(Point(x, point.y)) >= treeHeight
        }
        val isInvisibleFromBottom = (point.y + 1 until height).any { y ->
            input.getValue(Point(point.x, y)) >= treeHeight
        }
        return !isInvisibleFromLeft || !isInvisibleFromTop || !isInvisibleFromRight || !isInvisibleFromBottom
    }

    private fun getScenicScore(point: Point, treeHeight: Int): Int {
        val scoreFromLeft = (point.x - 1 downTo 0)
            .indexOfFirst { x ->
                input.getValue(Point(x, point.y)) >= treeHeight
            }
            .takeIf { it != -1 }
            ?.plus(1)
            ?: (point.x)
        val scoreFromTop = (point.y - 1 downTo 0)
            .indexOfFirst { y ->
                input.getValue(Point(point.x, y)) >= treeHeight
            }
            .takeIf { it != -1 }
            ?.plus(1)
            ?: (point.y)
        val scoreFromRight = (point.x + 1 until width)
            .indexOfFirst { x ->
                input.getValue(Point(x, point.y)) >= treeHeight
            }
            .takeIf { it != -1 }
            ?.plus(1)
            ?: (width - (point.x + 1))
        val scoreFromBottom = (point.y + 1 until height)
            .indexOfFirst { y ->
                input.getValue(Point(point.x, y)) >= treeHeight
            }
            .takeIf { it != -1 }
            ?.plus(1)
            ?: (height - (point.y + 1))
        return scoreFromLeft * scoreFromTop * scoreFromRight * scoreFromBottom
    }
}
