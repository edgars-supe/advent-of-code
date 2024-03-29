package lv.esupe.aoc.model

import lv.esupe.aoc.utils.gcd
import kotlin.math.absoluteValue
import kotlin.math.sign

data class Point(
    val x: Int,
    val y: Int
) {
    operator fun plus(point: Point) = Point(x + point.x, y + point.y)

    operator fun minus(point: Point) = Point(x - point.x, y - point.y)

    operator fun times(by: Int) = Point(x * by, y * by)

    fun move(dx: Int, dy: Int) = Point(x + dx, y + dy)

    fun move(direction: Direction) = this + direction.point

    fun moveBy(direction: Direction, amount: Int) = this + direction.point * amount

    fun distanceTo(x: Int, y: Int): Int {
        val dx = (x - this.x).absoluteValue
        val dy = (y - this.y).absoluteValue
        return dx + dy
    }

    infix fun distanceTo(other: Point) = distanceTo(other.x, other.y)

    fun up() = move(Direction.North)

    fun left() = move(Direction.West)

    fun right() = move(Direction.East)

    fun down() = move(Direction.South)

    fun neighbors(diagonal: Boolean = false) = listOf(up(), right(), down(), left()) +
        if (diagonal) listOf(up().right(), right().down(), down().left(), left().up()) else emptyList()

    fun isNeighbor(other: Point, diagonal: Boolean = false): Boolean {
        val d = this - other
        return if (diagonal) {
            d.x in -1..1 && d.y in -1..1
        } else {
            (d.x in -1..1) xor (d.y in -1..1)
        }
    }

    fun slope(other: Point): Point {
        val dX = other.x - x
        val dY = other.y - y
        if (dX == 0) return Point(0, 1 * dY.sign)
        else if (dY == 0) return Point(1 * dX.sign, 0)

        val gcd = gcd(dX.absoluteValue, dY.absoluteValue)
        return Point(dX / gcd, dY / gcd)
    }

    fun pointsTo(other: Point): Sequence<Point> {
        val slope = this.slope(other)
        return sequence {
            yield(this@Point)
            var curr = this@Point
            while (curr != other) {
                curr += slope
                yield(curr)
            }
        }
    }

    fun toLong(): PointL {
        return PointL(x.toLong(), y.toLong())
    }
}

fun <T> Map<Point, T>.asString(
    prefix: String = "\n",
    transform: (T?) -> CharSequence
): String = asString(prefix) { x, y -> transform(get(Point(x, y))) }

fun <T> Map<Point, T>.asString(
    prefix: String = "\n",
    producer: (x: Int, y: Int) -> CharSequence
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
