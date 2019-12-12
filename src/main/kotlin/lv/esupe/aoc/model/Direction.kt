package lv.esupe.aoc.model

import lv.esupe.aoc.utils.modulo

enum class Direction(val point: Point) {
    North(Point(1, 0)),
    East(Point(0, 1)),
    South(Point(-1, 0)),
    West(Point(0, -1));

    val left: Direction get() = getByOffset(-1)
    val right: Direction get() = getByOffset(1)
    val opposite: Direction get() = getByOffset(2)

    private fun getByOffset(offset: Int): Direction {
        val values = values()
        val index = values.indexOf(this)
        return values[(index + offset) modulo values.size]
    }
}