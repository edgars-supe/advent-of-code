package lv.esupe.aoc.year2024

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.product

fun main() = solve { Day3() }

class Day3 : Puzzle<Int, Int>(2024, 3) {
    override val input = rawInput.joinToString(separator = "")

    override fun solvePartOne(): Int {
        return Regex("""mul\((\d{1,3}),(\d{1,3})\)""")
            .findAll(input)
            .sumOf { result ->
                result.groupValues
                    .drop(1)
                    .map { it.toInt() }
                    .product()
            }
    }

    override fun solvePartTwo(): Int {
        val pattern = Regex("""(mul\((\d{1,3}),(\d{1,3})\)|do\(\)|don't\(\))""")
        val results = pattern.findAll(input)
        var isEnabled = true
        return results.fold(0) { sum, match ->
            val text = match.groupValues.first()
            when {
                text == "do()" -> {
                    isEnabled = true
                    sum
                }
                text == "don't()" -> {
                    isEnabled = false
                    sum
                }
                isEnabled -> {
                    sum + match.groupValues
                        .drop(2)
                        .map { it.toInt() }
                        .product()
                }
                else -> sum
            }
        }
    }
}
