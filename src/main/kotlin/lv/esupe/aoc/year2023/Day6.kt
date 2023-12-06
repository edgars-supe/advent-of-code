package lv.esupe.aoc.year2023

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day6() }

class Day6 : Puzzle<Int, Int>(2023, 6) {
    override val input = rawInput

    override fun solvePartOne(): Int {
        val times = rawInput[0].substringAfter("Time: ").split(" ").mapNotNull { it.toLongOrNull() }
        val distances = rawInput[1].substringAfter("Distance: ").split(" ").mapNotNull { it.toLongOrNull() }
        val races = times.zip(distances) { t, d -> Race(t, d) }
        return countWaysToWin(races)
    }

    override fun solvePartTwo(): Int {
        val time = rawInput[0].filter { it.isDigit() }.toLong()
        val distance = rawInput[1].filter { it.isDigit() }.toLong()
        val race = Race(time, distance)
        return countWaysToWin(listOf(race))
    }

    private fun countWaysToWin(races: List<Race>): Int {
        return races
            .map { race ->
                (1 until race.time)
                    .count { held ->
                        val remainingTime = race.time - held
                        val speed = held
                        (remainingTime * speed) > race.distance
                    }
            }
            .reduce { acc, n -> acc * n }
    }

    data class Race(val time: Long, val distance: Long)
}
