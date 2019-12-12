package lv.esupe.aoc.year2019

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Point3
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.lcm
import kotlin.math.abs

fun main(args: Array<String>) = solve { Day12() }

class Day12 : Puzzle<Int, Long>(2019, 12) {
    override val input = rawInput.map { coordinates ->
        Regex("<x=(-?\\d+), y=(-?\\d+), z=(-?\\d+)>")
            .find(coordinates)
            ?.groupValues
            ?.drop(1)
            ?.map { it.toInt() }
            ?.let { (x, y, z) -> Moon(position = Point3(x, y, z)) }
            ?: error("Failed to parse coordinates $coordinates")
    }

    override fun solvePartOne(): Int = (0 until 1000)
        .fold(input) { moons, _ -> moons.step() }
        .sumBy { it.energy }

    override fun solvePartTwo(): Long {
        var xPeriod = 0L
        var yPeriod = 0L
        var zPeriod = 0L
        var counter = 0L
        var step = input
        while (listOf(xPeriod, yPeriod, zPeriod).any { it == 0L }) {
            step = step.step()
            counter++
            if (xPeriod == 0L && step.check(Point3::x)) xPeriod = counter
            if (yPeriod == 0L && step.check(Point3::y)) yPeriod = counter
            if (zPeriod == 0L && step.check(Point3::z)) zPeriod = counter
        }
        return lcm(xPeriod, yPeriod, zPeriod)
    }

    private fun List<Moon>.step(): List<Moon> = mapIndexed { index, moon ->
        indices.filterNot { it == index }
            .map { get(it) }
            .fold(moon) { current, other -> current.gravitate(other) }
            .applyVelocity()
    }

    private inline fun List<Moon>.check(axis: Point3.() -> Int): Boolean =
        indices.all { idx -> matches(idx, axis) }

    private inline fun List<Moon>.matches(index: Int, axis: Point3.() -> Int): Boolean =
        this[index].position.axis() == input[index].position.axis() &&
            this[index].velocity.axis() == input[index].velocity.axis()

    data class Moon(
        val position: Point3,
        val velocity: Point3 = Point3(0, 0, 0)
    ) {
        val energy = position.energy() * velocity.energy()

        fun gravitate(other: Moon): Moon = copy(
            velocity = velocity.copy(
                x = gravitateAxis(other, Point3::x),
                y = gravitateAxis(other, Point3::y),
                z = gravitateAxis(other, Point3::z)
            )
        )

        fun applyVelocity(): Moon = copy(position = position + velocity)

        private inline fun gravitateAxis(other: Moon, axis: Point3.() -> Int): Int = when {
            position.axis() > other.position.axis() -> velocity.axis() - 1
            position.axis() < other.position.axis() -> velocity.axis() + 1
            else -> velocity.axis()
        }

        private fun Point3.energy() = abs(x) + abs(y) + abs(z)
    }
}
