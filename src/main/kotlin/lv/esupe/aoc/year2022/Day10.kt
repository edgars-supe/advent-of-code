package lv.esupe.aoc.year2022

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day10() }

class Day10 : Puzzle<Int, String>(2022, 10) {
    override val input = rawInput
        .asSequence()
        .map { line ->
            if (line == "noop") Cmd.Noop
            else Cmd.Addx(line.substringAfter(' ').toInt())
        }
        .flatMap { cmd ->
            when (cmd) {
                is Cmd.Addx -> listOf(Cmd.Noop, cmd)
                is Cmd.Noop -> listOf(cmd)
            }
        }

    override fun solvePartOne(): Int {
        return input
            .scan(1) { register, cmd ->
                if (cmd is Cmd.Addx) register + cmd.x
                else register
            }
            .withIndex()
            .filterIndexed { idx, _ ->
                when (idx + 1) {
                    20, 60, 100, 140, 180, 220 -> true
                    else -> false
                }
            }
            .fold(0) { acc, (idx, x) -> acc + ((idx + 1) * x) }
    }

    override fun solvePartTwo(): String {
        return input
            .fold("" to 1) { (screen, x), cmd ->
                val currentPixel = screen.length + 1
                val draw = (currentPixel - 1) % 40 in (x - 1)..(x + 1)
                val newPixel = if (draw) '#' else '.'
                val newScreen = screen + newPixel
                val newX = if (cmd is Cmd.Addx) x + cmd.x else x
                newScreen to newX
            }
            .first
            .chunked(40)
            .joinToString(separator = "\n", prefix = "\n")
    }

    sealed class Cmd {
        object Noop : Cmd()
        data class Addx(val x: Int) : Cmd()
    }
}
