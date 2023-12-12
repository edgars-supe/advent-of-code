package lv.esupe.aoc.year2023

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.repeat

fun main() = solve { Day12() }

class Day12 : Puzzle<Int, Int>(2023, 12) {
    override val input = rawInput.map { line ->
        val (conditions, groupSizes) = line.split(" ")
        Record(conditions, groupSizes.split(",").map { it.toInt() })
    }

    override fun solvePartOne(): Int {
        return input.sumOf { record ->
            getArrangementCount(record.conditions, record.groupSizes)
        }
    }

    override fun solvePartTwo(): Int {
        return input.map { record ->
            Record(
                conditions = (1..5).joinToString(separator = "?") { record.conditions },
                groupSizes = record.groupSizes.repeat(5)
            )
        }
            .sumOf { getArrangementCount(it.conditions, it.groupSizes) }
    }

    private fun getArrangementCount(conditions: String, groupSizes: List<Int>): Int {
        if (conditions.isBlank() && groupSizes.isEmpty()) return 1
        if (conditions.isBlank()) return 0
        if (groupSizes.isEmpty() && conditions.none { it == '#' }) return 1
        if (groupSizes.isEmpty()) return 0

        return when (conditions[0]) {
            '.' -> {
                getArrangementCount(conditions.drop(1), groupSizes)
            }
            '#' -> {
                val expectedGroupSize = groupSizes.first()
                val doesNotContainDots = conditions.take(expectedGroupSize).none { it == '.' }
                if (conditions.length < expectedGroupSize) return 0
                val matchesExpectedLength = conditions.length == expectedGroupSize || conditions[expectedGroupSize] != '#'
                if (doesNotContainDots && matchesExpectedLength) {
                    getArrangementCount(conditions.drop(expectedGroupSize + 1), groupSizes.drop(1))
                } else {
                    return 0
                }
            }
            '?' -> getArrangementCount("." + conditions.drop(1), groupSizes) + getArrangementCount("#" + conditions.drop(1), groupSizes)
            else -> 0
        }
    }

    data class Record(val conditions: String, val groupSizes: List<Int>) {
        override fun toString(): String {
            return "$conditions ${groupSizes.joinToString { "$it" }}"
        }
    }
}
