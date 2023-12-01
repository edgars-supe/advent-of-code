package lv.esupe.aoc.year2023

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve(benchmark = true) { Day1() }

class Day1 : Puzzle<Int, Int>(2023, 1) {
    override val input = rawInput

    override fun solvePartOne(): Int {
        return input.sumFirstLastDigits()
    }

    override fun solvePartTwo(): Int {
        return input.sumOf { line ->
            val first = line.findFirstDigit()
            val last = line.findLastDigit()
            first * 10 + last
        }
    }

    private fun String.findFirstDigit(): Int {
        return findDigitIndex { strings -> indexOfAny(strings, startIndex = 0, ignoreCase = true) }

    }

    private fun String.findLastDigit(): Int {
        return findDigitIndex { strings -> lastIndexOfAny(strings, startIndex = lastIndex, ignoreCase = true) }
    }

    private fun String.findDigitIndex(function: (strings: Collection<String>) -> Int): Int {
        val idx = function(
            listOf(
                "one", "1",
                "two", "2",
                "three", "3",
                "four", "4",
                "five", "5",
                "six", "6",
                "seven", "7",
                "eight", "8",
                "nine", "9"
            )
        )
        if (get(idx).isDigit()) {
            return get(idx).digitToInt()
        } else {
            val substring = substring(idx)
            return when {
                substring.startsWith("one") -> 1
                substring.startsWith("two") -> 2
                substring.startsWith("three") -> 3
                substring.startsWith("four") -> 4
                substring.startsWith("five") -> 5
                substring.startsWith("six") -> 6
                substring.startsWith("seven") -> 7
                substring.startsWith("eight") -> 8
                substring.startsWith("nine") -> 9
                else -> error("Unmatched number")
            }
        }
    }


    private fun List<String>.sumFirstLastDigits(): Int =
        sumOf { line -> "${line.firstOrNull { it.isDigit() }}${line.lastOrNull { it.isDigit() }}".toInt() }
}
