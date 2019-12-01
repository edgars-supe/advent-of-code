package lv.esupe.aoc.model

enum class Direction(val point: Point) {
    North(Point(1, 0)),
    South(Point(-1, 0)),
    West(Point(0, -1)),
    East(Point(0, 1));
}