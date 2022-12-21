package lv.esupe.aoc.year2022

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day21() }

class Day21 : Puzzle<Long, Int>(2022, 21) {
    override val input = rawInput.associate { line ->
        val monkey = line.substringBefore(": ")
        val otherPart = line.substringAfter(": ")
        if (otherPart.toLongOrNull() != null) {
            monkey to Yell.Number(otherPart.toLong())
        } else {
            val (m1, op, m2) = otherPart.split(' ')
            val yell = when (op) {
                "+" -> Yell.Add(m1, m2)
                "-" -> Yell.Subtract(m1, m2)
                "*" -> Yell.Multiply(m1, m2)
                "/" -> Yell.Divide(m1, m2)
                else -> error("Unknown operation \"$op\"")
            }
            monkey to yell
        }
    }

    private val monkeyValues: MutableMap<String, Long> = mutableMapOf()

    override fun solvePartOne(): Long {
        return calculateValue(monkey = "root")
    }

    override fun solvePartTwo(): Int {
        return 0
    }

    private fun calculateValue(monkey: String): Long {
        return monkeyValues.getOrPut(monkey) {
            when (val yell = input.getValue(monkey)) {
                is Yell.Number -> yell.value
                is Yell.Add -> calculateValue(yell.m1) + calculateValue(yell.m2)
                is Yell.Subtract -> calculateValue(yell.m1) - calculateValue(yell.m2)
                is Yell.Multiply -> calculateValue(yell.m1) * calculateValue(yell.m2)
                is Yell.Divide -> calculateValue(yell.m1) / calculateValue(yell.m2)
            }
        }
    }

    sealed class Yell {
        data class Number(val value: Long) : Yell()
        data class Add(val m1: String, val m2: String) : Yell()
        data class Subtract(val m1: String, val m2: String) : Yell()
        data class Multiply(val m1: String, val m2: String) : Yell()
        data class Divide(val m1: String, val m2: String) : Yell()
    }
}
