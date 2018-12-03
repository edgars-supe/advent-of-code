package lv.esupe.aoc.year2017

import lv.esupe.aoc.Puzzle
import kotlin.math.absoluteValue
import kotlin.math.pow

fun main(args: Array<String>) {
    Day3Puzzle1().calculateAndPrint()
    Day3Puzzle2().calculateAndPrint()
}

class Day3Puzzle1 : Puzzle<Int>(2017, 3, 1) {
    override fun calculate(): Int {
        val target = input.first().toInt()
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

class Day3Puzzle2 : Puzzle<Int>(2017, 3, 2) {
    override fun calculate(): Int = 0
}