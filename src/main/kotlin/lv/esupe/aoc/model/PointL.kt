package lv.esupe.aoc.model

import lv.esupe.aoc.utils.gcd
import kotlin.math.absoluteValue
import kotlin.math.sign

data class PointL(
    val x: Long,
    val y: Long
) {
    operator fun plus(PointL: PointL) = PointL(x + PointL.x, y + PointL.y)

    operator fun minus(PointL: PointL) = PointL(x - PointL.x, y - PointL.y)

    operator fun times(by: Int) = PointL(x * by, y * by)

    operator fun times(by: Long) = PointL(x * by, y * by)

    fun move(dx: Int, dy: Int) = PointL(x + dx, y + dy)

    fun move(dx: Long, dy: Long) = PointL(x + dx, y + dy)

    fun move(direction: Direction) = this + direction.pointL

    fun moveBy(direction: Direction, amount: Int) = this + direction.pointL * amount

    fun distanceTo(x: Long, y: Long): Long {
        val dx = (x - this.x).absoluteValue
        val dy = (y - this.y).absoluteValue
        return dx + dy
    }

    infix fun distanceTo(other: PointL) = distanceTo(other.x, other.y)

    fun up() = move(Direction.North)

    fun left() = move(Direction.West)

    fun right() = move(Direction.East)

    fun down() = move(Direction.South)

    fun neighbors(diagonal: Boolean = false) = listOf(up(), right(), down(), left()) +
        if (diagonal) listOf(up().right(), right().down(), down().left(), left().up()) else emptyList()

    fun isNeighbor(other: PointL, diagonal: Boolean = false): Boolean {
        val d = this - other
        return if (diagonal) {
            d.x in -1..1 && d.y in -1..1
        } else {
            (d.x in -1..1) xor (d.y in -1..1)
        }
    }

    fun slope(other: PointL): PointL {
        val dX = other.x - x
        val dY = other.y - y
        if (dX == 0L) return PointL(0, 1L * dY.sign)
        else if (dY == 0L) return PointL(1L * dX.sign, 0)

        val gcd = gcd(dX.absoluteValue, dY.absoluteValue)
        return PointL(dX / gcd, dY / gcd)
    }

    fun pointsTo(other: PointL): Sequence<PointL> {
        val slope = this.slope(other)
        return sequence {
            yield(this@PointL)
            var curr = this@PointL
            while (curr != other) {
                curr += slope
                yield(curr)
            }
        }
    }
}

fun <T> Map<PointL, T>.asString(
    prefix: String = "\n",
    transform: (T?) -> CharSequence
): String = asString(prefix) { x, y -> transform(get(PointL(x, y))) }

fun <T> Map<PointL, T>.asString(
    prefix: String = "\n",
    producer: (x: Long, y: Long) -> CharSequence
): String {
    val minX = keys.minOf { it.x }
    val maxX = keys.maxOf { it.x }
    val minY = keys.minOf { it.y }
    val maxY = keys.maxOf { it.y }
    return (minY .. maxY)
        .joinToString(prefix = prefix, separator = "\n") { y ->
            (minX..maxX).joinToString(separator = "") { x ->
                producer(x, y)
            }
        }
}
