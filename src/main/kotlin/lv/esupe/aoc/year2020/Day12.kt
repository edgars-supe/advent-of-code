package lv.esupe.aoc.year2020

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Direction
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve

fun main() = solve { Day12() }

class Day12 : Puzzle<Int, Int>(2020, 12) {
    override val input = rawInput.map { it.first() to it.drop(1).toInt() }

    override fun solvePartOne(): Int {
        var location = Point(0, 0)
        var facing = Direction.East
        input.forEach { (cmd, value) ->
            when (cmd) {
                'N' -> location += Direction.North * value
                'E' -> location += Direction.East * value
                'S' -> location += Direction.South * value
                'W' -> location += Direction.West * value
                'F' -> location += facing.point * value
                'R' -> {
                    when (value) {
                        90 -> facing = facing.right
                        180 -> facing = facing.opposite
                        270 -> facing = facing.left
                    }
                }
                'L' -> {
                    when (value) {
                        90 -> facing = facing.left
                        180 -> facing = facing.opposite
                        270 -> facing = facing.right
                    }
                }
            }
        }
        return Point(0, 0).distanceTo(location)
    }

    override fun solvePartTwo(): Int {
        var waypoint = Point(10, 1)
        var location = Point(0, 0)
        input.forEach { (cmd, value) ->
            when (cmd) {
                'N' -> waypoint += Direction.North * value
                'E' -> waypoint += Direction.East * value
                'S' -> waypoint += Direction.South * value
                'W' -> waypoint += Direction.West * value
                'F' -> location += waypoint * value
                'R' -> {
                    when (value) {
                        90 -> waypoint = Point(waypoint.y, -waypoint.x)
                        180 -> waypoint *= -1
                        270 -> waypoint = Point(-waypoint.y, waypoint.x)
                    }
                }
                'L' -> {
                    when (value) {
                        90 -> waypoint = Point(-waypoint.y, waypoint.x)
                        180 -> waypoint *= -1
                        270 -> waypoint = Point(waypoint.y, -waypoint.x)
                    }
                }
            }
        }
        return Point(0, 0).distanceTo(location)
    }
}
