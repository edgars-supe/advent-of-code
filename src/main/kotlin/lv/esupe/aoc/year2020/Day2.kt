package lv.esupe.aoc.year2020

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day2() }

class Day2 : Puzzle<Int, Int>(2020, 2) {
    override val input = rawInput.map { line ->
        val (range, char, password) = line.split(" ")
        val (start, end) = range.split("-").map { it.toInt() }
        Entry(password, char.first(), start, end)
    }

    override fun solvePartOne(): Int = input.count { entry ->
        val count = entry.password.count { it == entry.char }
        count in entry.start..entry.end
    }

    override fun solvePartTwo(): Int = input.count { entry ->
        (entry.password[entry.start - 1] == entry.char) xor (entry.password[entry.end - 1] == entry.char)
    }

    data class Entry(val password: String, val char: Char, val start: Int, val end: Int)
}
