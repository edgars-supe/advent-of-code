package lv.esupe.aoc.year2022

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve
import kotlin.math.absoluteValue

fun main() = solve { Day15(findAtY = 2000000, xyMax = 4000000) }

class Day15(val findAtY: Int, val xyMax: Int) : Puzzle<Int, Long>(2022, 15) {
    override val input = rawInput
        .map { line ->
            val (xS, yS) = line.removePrefix("Sensor at ").substringBefore(':').split(", ")
            val sensorX = xS.split('=').last().toInt()
            val sensorY = yS.split('=').last().toInt()
            val (xB, yB) = line.substringAfter(": closest beacon is at ").split(", ")
            val beaconX = xB.split('=').last().toInt()
            val beaconY = yB.split('=').last().toInt()
            SensorBeacon(
                sensor = Point(sensorX, sensorY),
                beacon = Point(beaconX, beaconY)
            )
        }

    override fun solvePartOne(): Int {
        val invalidXs = input
            .asSequence()
            .flatMap { sb -> listOf(sb.sensor, sb.beacon) }
            .filter { it.y == findAtY }
            .map { it.x }
            .toHashSet()
        return input
            .asSequence()
            .filter { sb ->
                val lowerBound = sb.sensor.y - sb.distance
                val upperBound = sb.sensor.y + sb.distance
                findAtY in lowerBound..upperBound
            }
            .map { sb ->
                val dY = (findAtY - sb.sensor.y).absoluteValue
                val dDistance = sb.distance - dY
                (sb.sensor.x - dDistance)..(sb.sensor.x + dDistance)
            }
            .flatten()
            .toSet()
            .count { it !in invalidXs }
    }

    override fun solvePartTwo(): Long {
        val beacon = input.firstNotNullOf { sb ->
            val d = sb.distance + 1
            val x = sb.sensor.x
            val y = sb.sensor.y
            val (l, u, r, b) = listOf(Point(x - d, y), Point(x, y + d), Point(x + d, y), Point(x, y - d))
            val perimeter = (l.pointsTo(u) + u.pointsTo(r) + r.pointsTo(b) + b.pointsTo(l))
                .filter { (x, y) -> x in 0..xyMax && y in 0..xyMax }
                .toHashSet()
            input
                .forEach { sb2 ->
                    perimeter.removeIf { p -> sb2.sensor.distanceTo(p) <= sb2.distance }
                }
            perimeter.firstOrNull()
        }
        return beacon.x.toLong() * 4000000 + beacon.y.toLong()
    }

    data class SensorBeacon(val sensor: Point, val beacon: Point) {
        val distance = sensor.distanceTo(beacon)
    }
}
