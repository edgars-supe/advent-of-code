package lv.esupe.aoc.year2022

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve

fun main() = solve { Day14() }

class Day14 : Puzzle<Int, Int>(2022, 14) {
    override val input = rawInput
        .flatMap { line ->
            line.split(" -> ")
                .map { coords ->
                    val (x, y) = coords.split(',')
                    Point(x.toInt(), y.toInt())
                }
                .zipWithNext()
                .flatMap { (a, b) -> a.pointsTo(b) }
        }
        .toSet()

    private val sandOrigin = Point(500, 0)
    private val maxHeight = input.maxOf { it.y } // abyss beyond this height

    override fun solvePartOne(): Int {
        return simulateSand(maxHeight, hasFloor = false)
    }

    override fun solvePartTwo(): Int {
        return simulateSand(maxHeight + 2, hasFloor = true)
    }

    private fun simulateSand(maxHeight: Int, hasFloor: Boolean): Int {
        val sand = mutableSetOf<Point>()
        var newParticle: Point?
        do {
            newParticle = simulateSandParticle(sand, input, maxHeight, hasFloor)
            if (newParticle != null) sand += newParticle
            if (newParticle == sandOrigin) break
        } while (newParticle != null)
        return sand.size
    }

    private fun simulateSandParticle(sand: Set<Point>, rocks: Set<Point>, maxHeight: Int, hasFloor: Boolean): Point? {
        var particle = sandOrigin
        while (particle.y <= maxHeight) {
            var candidate = particle.up() // y increases as we go lower :(
            val hasHitFloor = if (hasFloor) candidate.y >= maxHeight else false
            if (!hasHitFloor && candidate !in sand && candidate !in rocks) {
                particle = candidate
                continue
            }
            candidate = particle.up().left() // x decreases
            if (!hasHitFloor && candidate !in sand && candidate !in rocks) {
                particle = candidate
                continue
            }
            candidate = particle.up().right()
            if (!hasHitFloor && candidate !in sand && candidate !in rocks) {
                particle = candidate
                continue
            }
            return particle
        }
        return null
    }
}
