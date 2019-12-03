package lv.esupe.aoc.model

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
}