package lv.esupe.aoc.year2021

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.model.Point
import lv.esupe.aoc.solve

fun main() = solve { Day2() }

class Day2 : Puzzle<Int, Int>(2021, 2) {
    override val input = rawInput.map { it.toCommand() }

    override fun solvePartOne(): Int {
        // Point.x represents horizontal location, Point.y represents depth (the higher, the deeper)
        return input.fold(Point(0, 0)) { acc, command ->
            when (command) {
                is Command.Forward -> acc + Point(command.amount, 0)
                is Command.Down -> acc + Point(0, command.amount)
                is Command.Up -> acc + Point(0, -command.amount)
            }
        }
            .let { it.x * it.y }
    }

    override fun solvePartTwo(): Int {
        // Point.x represents horizontal location, Point.y represents depth (the higher, the deeper)
        return input.fold(Pair(Point(0, 0), 0)) { (location, aim), command ->
            when (command) {
                is Command.Forward -> location + Point(command.amount, aim * command.amount) to aim
                is Command.Down -> location to aim + command.amount
                is Command.Up -> location to aim - command.amount
            }
        }
            .let { (location, _) -> location.x * location.y }
    }

    private fun String.toCommand(): Command {
        return split(" ").let { (direction, value) ->
            val amount = value.toInt()
            when (direction) {
                "forward" -> Command.Forward(amount)
                "down" -> Command.Down(amount)
                "up" -> Command.Up(amount)
                else -> error("Unknown direction $direction")
            }
        }
    }

    sealed class Command(val amount: Int) {
        class Forward(amount: Int) : Command(amount)
        class Up(amount: Int) : Command(amount)
        class Down(amount: Int) : Command(amount)
    }

}
