package lv.esupe.aoc.year2021

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve
import kotlin.math.floor
import kotlin.math.sign
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
        return (minXVelocity(xRange.first)..minXVelocity(xRange.last)).maxOf { vx ->
            (minXVelocity(xRange.last)..-yRange.first).maxOf { vy ->
                val probe = Probe(vx, vy)
                var maxY: Int = probe.ly
                while (safe(probe)) {
                    if (probe.ly > maxY) maxY = probe.ly
                    if (inTarget(probe)) return@maxOf maxY
                    probe.step()
                }
                Int.MIN_VALUE
            }
        }
    }

    override fun solvePartTwo(): Int {
        return (minXVelocity(xRange.first)..xRange.last).sumOf { vx ->
            (yRange.first..-yRange.first).count { vy ->
                val probe = Probe(vx, vy)
                while (safe(probe)) {
                    if (inTarget(probe)) return@count true
                    probe.step()
                }
                false
            }
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

    private fun inTarget(probe: Probe): Boolean {
        return probe.lx in xRange && probe.ly in yRange
    }

    private fun safe(probe: Probe): Boolean {
        return probe.lx <= xRange.last && probe.ly >= yRange.first
    }

    class Probe(var vx: Int, var vy: Int) {
        var lx = 0
        var ly = 0

        fun step() {
            lx += vx
            ly += vy
            vx -= vx.sign
            vy -= 1
        }
    }
}
