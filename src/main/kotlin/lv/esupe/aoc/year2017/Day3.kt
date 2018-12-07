package lv.esupe.aoc.year2017

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import kotlin.math.absoluteValue
import kotlin.math.pow

fun main(args: Array<String>) = solve { Day3() }

class Day3 : Puzzle<Int, Int>(2017, 3) {
    override val input = rawInput.first().toInt()

    override fun solvePartOne(): Int {
        val target = input
        val level = level(target)
        val floor = (level.max - 2).toFloat().pow(2).toInt()
        val ceiling = level.max.toFloat().pow(2).toInt()
        val diff = ceiling - floor
        val quarter = diff / 4
        val half = diff / 2
        return listOf(
            floor until floor + quarter,
            floor + quarter until floor + half,
            ceiling - half until ceiling - quarter,
            ceiling - quarter .. ceiling
        ).first { target in it }
            .let {
                val middle = it.last - (it.last - it.first) / 2
                (target - middle).absoluteValue
            }
            .let {
                it + level.level
            }
    }

    override fun solvePartTwo(): Int = 0

    private fun level(target: Int): Level {
        var level = 0
        var incr = 1f
        while (incr.pow(2) < target) {
            level++
            incr += 2
        }
        return Level(level, incr.toInt())
    }

    data class Level(val level: Int, val max: Int)
}