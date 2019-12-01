package lv.esupe.aoc.year2017

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main(args: Array<String>) = solve { Day7() }

class Day7 : Puzzle<String, Int>(2017, 7) {
    override val input = rawInput

    override fun solvePartOne(): String {
        val subs = mutableSetOf<String>()
        return input.map { it.toProgram() }
            .filter { it.subprogramNames != null }
            .apply {
                forEach { program ->
                    program.subprogramNames?.forEach {
                        subs.add(it)
                    }
                }
            }
            .first { !subs.contains(it.name) }
            .name
    }

    override fun solvePartTwo(): Int = 0
}

private fun String.toProgram(): Program {
    Regex("""(\w+) \((\d+)\)(?: -> (.*))?""")
        .find(this)
        ?.let {
            val name = it.groupValues[1]
            val weight = it.groupValues[2].toInt()
            val subprogramNames = it.groupValues.getOrNull(3)?.split(", ")
            return Program(name, weight, subprogramNames)
        }
    throw Exception("Skip Christmas")
}

data class Program(
    val name: String,
    val weight: Int,
    val subprogramNames: List<String>?
)