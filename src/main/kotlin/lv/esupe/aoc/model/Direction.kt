package lv.esupe.aoc.model

import lv.esupe.aoc.utils.modulo

enum class Direction(val point: Point) {
    North(Point(0, 1)),
    East(Point(1, 0)),
    South(Point(0, -1)),
    West(Point(-1, 0));

    val left: Direction get() = getByOffset(-1)
    val right: Direction get() = getByOffset(1)
    val opposite: Direction get() = getByOffset(2)

    val pointL: PointL = point.toLong()

    operator fun times(by: Int) = point * by

    operator fun unaryMinus() = opposite

    private fun getByOffset(offset: Int): Direction {
        val values = entries
        val index = values.indexOf(this)
        return values[(index + offset) modulo values.size]
    }
}
