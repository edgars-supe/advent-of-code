package lv.esupe.aoc.year2024

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day5() }

class Day5 : Puzzle<Int, Int>(2024, 5) {
    override val input = rawInput
    private val precedents: Map<Int, Set<Int>>
    private val updates: List<List<Int>>

    init {
        val divider = input.indexOfFirst { it.isEmpty() }
        precedents = input.take(divider)
            .map { line -> line.split("|").map(String::toInt) }
            .fold(mutableMapOf()) { map, (precedent, page) ->
                map[page] = map.getOrDefault(page, emptySet()) + precedent
                map
            }
        updates = input.drop(divider + 1)
            .map { line -> line.split(",").map(String::toInt) }
    }

    override fun solvePartOne(): Int {
        return updates.filter { isInCorrectOrder(it, precedents) }
            .sumOf { update -> update[update.size / 2] }
    }

    override fun solvePartTwo(): Int {
        return 0
    }

    private fun isInCorrectOrder(update: List<Int>, precedents: Map<Int, Set<Int>>): Boolean {
        val seen = mutableSetOf<Int>()
        return update.all { page ->
            seen.add(page)
            precedents[page]?.all { it in seen || it !in update } ?: true
        }
    }
}
