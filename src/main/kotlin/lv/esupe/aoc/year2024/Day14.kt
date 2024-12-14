package lv.esupe.aoc.year2024

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.model.Quadrant
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.findAllInts
import lv.esupe.aoc.utils.modulo

fun main() = solve { Day14(width = 101, height = 103) }

class Day14(val width: Int, val height: Int) : Puzzle<Int, Int>(2024, 14) {
    override val input = rawInput.map(Robot.Companion::fromLine)

    override fun solvePartOne(): Int {
        val locations = input.map { robot -> robot.advance(times = 100, width, height) }
        return getSafetyFactor(locations)
    }

    override fun solvePartTwo(): Int {
        return generateSequence(1) { it + 1 }
            .first { seconds ->
                val locations = input.map { it.advance(times = seconds, width, height) }
                locations.toSet().size == input.size
            }
    }

    private fun getSafetyFactor(locations: List<Point>): Int {
        val quadrantFinder = Quadrant.Finder(width, height)
        return locations.mapNotNull { quadrantFinder.find(it) }
            .groupBy { it }
            .entries
            .fold(1) { factor, (_, locations) -> factor * locations.size }
    }

    data class Robot(
        val location: Point,
        val velocity: Point
    ) {
        fun advance(times: Int = 1, width: Int, height: Int): Point {
            val location = location + velocity.times(times)
            return Point(
                    x = location.x modulo width,
                    y = location.y modulo height
                )
        }

        companion object {
            fun fromLine(line: String): Robot {
                val (lx, ly, vx, vy) = line.findAllInts()
                return Robot(
                    location = Point(lx, ly),
                    velocity = Point(vx, vy)
                )
            }
        }
    }
}
