package lv.esupe.aoc.model

import lv.esupe.aoc.utils.gcd
import kotlin.math.absoluteValue

data class Point(
    val x: Int,
    val y: Int
) {
    operator fun plus(point: Point) = Point(x + point.x, y + point.y)

    operator fun minus(point: Point) = Point(x - point.x, y - point.y)

    operator fun times(by: Int) = Point(x * by, y * by)

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

    fun neighbors() = listOf(up(), right(), down(), left())

    fun slope(other: Point): Point {
        val dX = other.x - x
        val dY = other.y - y
        val gcd = gcd(dX.absoluteValue, dY.absoluteValue)
        return Point(dX / gcd, dY / gcd)
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
    val minX = keys.minBy { it.x }!!.x
    val maxX = keys.maxBy { it.x }!!.x
    val minY = keys.minBy { it.y }!!.y
    val maxY = keys.maxBy { it.y }!!.y
    return (minY .. maxY)
        .joinToString(prefix = prefix, separator = "\n") { y ->
            (minX..maxX).joinToString(separator = "") { x ->
                producer(x, y)
            }
        }
}
