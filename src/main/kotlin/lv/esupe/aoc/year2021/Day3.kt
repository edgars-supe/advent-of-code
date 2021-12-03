package lv.esupe.aoc.year2021

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day3() }

class Day3 : Puzzle<Int, Int>(2021, 3) {
    override val input = rawInput.map { line -> line.map { it.digitToInt() } }
    private var mostCommonBits: String = ""
    private var leastCommonBits: String = ""

    override fun solvePartOne(): Int {
        val occurrences = input.calculateBitOccurrences()
        mostCommonBits = occurrences
            .map { bit -> if (bit > 0) 1 else 0 }
            .joinToString("")
        leastCommonBits = occurrences
            .map { bit -> if (bit > 0) 0 else 1 }
            .joinToString("")

        val gamma = mostCommonBits.toInt(2)
        val epsilon = leastCommonBits.toInt(2)
        return gamma * epsilon
    }

    override fun solvePartTwo(): Int {
        val ogr = input.findRating { bit, bitOccurrence ->
            // if bit is 1 and there are more or equal amount of 1 bits OR bit is 0 and there are more 0 bits.
            bit == 1 && bitOccurrence >= 0 || bit == 0 && bitOccurrence < 0
        }
        val co2 = input.findRating { bit, bitOccurrence ->
            // if bit is 1 and there are less 1 bits OR bit is 0 and there are more or equal amount of 0 bits.
            bit == 1 && bitOccurrence < 0 || bit == 0 && bitOccurrence >= 0
        }
        return ogr * co2
    }

    /**
     * Calculates the number of times bits appear in a list of numbers. A positive value indicates more 1 bits, zero
     * means 1s and 0s occur the same amount of times, and a negative value indicates more 0 bits.
     */
    private fun List<List<Int>>.calculateBitOccurrences(): List<Int> =
        (first().indices).map { idx -> calculateBitOccurrenceForPosition(idx) }

    /**
     * Calculates the number of times bits appear in a list of numbers at the specified `position`. A positive value
     * indicates more 1 bits, zero means 1s and 0s occur the same amount of times, and a negative value indicates more
     * 0 bits.
     */
    private fun List<List<Int>>.calculateBitOccurrenceForPosition(position: Int): Int =
        fold(0) { acc, number ->
            if (number[position] == 1) acc + 1
            else acc - 1
        }

    /**
     * Finds rating based on criteria set by `compare`.
     *
     * @param compare Function that returns if a number is valid based on the `bit` at the given `position` and the
     *   occurrence of bits denoted by `bitOccurrence`.
     */
    private fun List<List<Int>>.findRating(position: Int = 0, compare: (bit: Int, bitOccurrence: Int) -> Boolean): Int {
        val bitOccurrence = calculateBitOccurrenceForPosition(position)
        val validNumbers = filter { number -> compare(number[position], bitOccurrence) }
        return if (validNumbers.size == 1) {
            validNumbers.first().joinToString("").toInt(2)
        } else {
            validNumbers.findRating(position + 1, compare)
        }
    }
}
