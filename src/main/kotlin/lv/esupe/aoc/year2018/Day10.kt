package lv.esupe.aoc.year2018

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.pmapIndexed
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) = solve { Day10() }

class Day10 : Puzzle<String, Int>(2018, 10) {
    override val input = rawInput.mapNotNull {
        Regex("""position=<\s*([\d\-]+),\s*([\d\-]+)> velocity=<\s*([\d\-]+),\s*([\d\-]+)>""")
            .find(it)
            ?.groupValues
            ?.let {
                val x = it[1].toInt()
                val y = it[2].toInt()
                val velX = it[3].toInt()
                val velY = it[4].toInt()
                Point(x, y) to Point(velX, velY)
            }
    }
    val points = input.toMutableList()
    var count: Int = -1
    val minX get() = points.minBy { it.first.x }!!.first.x
    val minY get() = points.minBy { it.first.y }!!.first.y
    val maxX get() = points.maxBy { it.first.x }!!.first.x
    val maxY get() = points.maxBy { it.first.y }!!.first.y

    override fun solvePartOne(): String {
        val sb = StringBuilder("\n")
        with (points.map { it.first }) {
            (minY..maxY).forEach { y ->
                (minX..maxX).forEach { x ->
                    if (!contains(Point(x, y))) sb.append('.')
                    else sb.append('#')
                }
                sb.append("\n")
            }
        }
        return sb.toString()
    }

    override fun solvePartTwo(): Int = count

    private fun step() {
        var prevDiff = Int.MAX_VALUE
        while (maxY - minY <= prevDiff) {
            prevDiff = maxY - minY
            count++
            points.pmapIndexed { idx, pair ->
                points[idx] = pair.first + pair.second to pair.second
            }
        }
        points.pmapIndexed { i, pair ->
            points[i] = pair.first - pair.second to pair.second
        }
    }
}