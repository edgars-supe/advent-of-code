package lv.esupe.aoc.year2021

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve
import kotlin.math.floor
import kotlin.math.sqrt

fun main() = solve { Day17() }

class Day17 : Puzzle<Int, Int>(2021, 17) {
    // target area: x=128..160, y=-142..-88
    override val input = rawInput
    private val xRange: IntRange
    private val yRange: IntRange

    init {
        val regex = Regex("target area: x=(-?\\d+)..(-?\\d+), y=(-?\\d+)..(-?\\d+)")
        val (minX, maxX, minY, maxY) = regex.matchEntire(rawInput[0])
            ?.groupValues
            ?.drop(1)
            ?.map { it.toInt() }
            ?: error("Can't parse line \"${rawInput[0]}\"")
        xRange = minX..maxX
        yRange = minY..maxY
    }

    override fun solvePartOne(): Int {
        return generateVelocities(
            fromX = minXVelocity(xRange.first),
            toX = minXVelocity(xRange.last),
            fromY = minXVelocity(xRange.first),
            toY = -yRange.first
        )
            .maxOf { velocity ->
                var vector = Vector(Point(0, 0), velocity)
                var maxY: Int = vector.location.y
                while (safe(vector)) {
                    if (vector.location.y > maxY) {
                        maxY = vector.location.y
                    }
                    if (inTarget(vector)) return@maxOf maxY
                    vector = step(vector)
                }
                Int.MIN_VALUE
            }
    }

    override fun solvePartTwo(): Int {
        val startLocation = Point(0, 0)
        return generateVelocities(
            fromX = minXVelocity(xRange.first),
            toX = xRange.last,
            fromY = yRange.first,
            toY = -yRange.first
        )
            .count { velocity ->
                var vector = Vector(startLocation, velocity)
                while (safe(vector)) {
                    if (inTarget(vector)) return@count true
                    vector = step(vector)
                }
                false
            }
    }

    /**
     * Minimum X velocity needed to reach 'forTarget'. If vx is 10, max X it can reach is 10+9+8+...+3+2+1=55
     */
    private fun minXVelocity(forTarget: Int): Int {
        val d = 1 + 4 * forTarget * 2
        val sqrtD = sqrt(d.toDouble())
        return floor((sqrtD - 1) / 2).toInt()
    }

    private fun generateVelocities(fromX: Int, toX: Int, fromY: Int, toY: Int): List<Point> {
        return (fromX..toX).flatMap { x ->
            (fromY..toY).map { y -> Point(x, y) }
        }
    }

    private fun step(vector: Vector): Vector {
        val vx = vector.velocity.x
        val nx = when {
            vx > 0 -> vx - 1
            vx < 0 -> vx + 1
            else -> 0
        }
        val velocity = vector.velocity.copy(x = nx, y = vector.velocity.y - 1)
        return vector.copy(
            location = vector.location + vector.velocity,
            velocity = velocity
        )
    }

    private fun inTarget(vector: Vector): Boolean {
        return vector.location.x in xRange && vector.location.y in yRange
    }

    private fun safe(vector: Vector): Boolean {
        return vector.location.x <= xRange.last && vector.location.y >= yRange.first
    }

    data class Vector(val location: Point, val velocity: Point)
}
