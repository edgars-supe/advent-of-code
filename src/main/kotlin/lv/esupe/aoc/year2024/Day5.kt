package lv.esupe.aoc.year2024

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day5() }

class Day5 : Puzzle<Int, Int>(2024, 5) {
    override val input = rawInput
    private val precedents: Map<Int, Set<Int>>
    private val correct: List<List<Int>>
    private val incorrect: List<List<Int>>

    init {
        val divider = input.indexOfFirst { it.isEmpty() }
        precedents = input.take(divider)
            .map { line -> line.split("|").map(String::toInt) }
            .fold(mutableMapOf()) { map, (precedent, page) ->
                map[page] = map.getOrDefault(page, emptySet()) + precedent
                map
            }
        val updates = input.drop(divider + 1)
            .map { line -> line.split(",").map(String::toInt) }
        correct = updates.filter { isInCorrectOrder(it, precedents) }
        incorrect = updates.filterNot { isInCorrectOrder(it, precedents) }
    }

    override fun solvePartOne(): Int {
        return correct.sumOf { update -> update[update.size / 2] }
    }

    override fun solvePartTwo(): Int {
        return incorrect
            .map { update ->
                fixUpdate(update)
            }
            .sumOf { update -> update[update.size / 2] }
    }

    private fun isInCorrectOrder(update: List<Int>, precedents: Map<Int, Set<Int>>): Boolean {
        val seen = mutableSetOf<Int>()
        return update.all { page ->
            seen += page
            precedents[page]?.all { it in seen || it !in update } ?: true
        }
    }

    private fun fixUpdate(update: List<Int>): List<Int> {
        val queue = update.toMutableList()
        var idx = 0
        return buildList {
            while (queue.isNotEmpty()) {
                val page = queue[idx]
                if (precedents[page]?.all { it in this || it !in update } != false) {
                    queue.removeAt(idx)
                    add(page)
                    idx = 0
                } else {
                    idx++
                }
            }
        }
    }
}
