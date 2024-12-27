package lv.esupe.aoc.year2024

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day23() }

class Day23 : Puzzle<Int, Int>(2024, 23) {
    override val input = rawInput
    private val connections = mutableMapOf<String, MutableList<String>>()

    init {
        input.forEach { line ->
            val (a, b) = line.split("-")
            connections.compute(a) { _, list -> (list ?: mutableListOf()).also { it.add(b) } }
            connections.compute(b) { _, list -> (list ?: mutableListOf()).also { it.add(a) } }
        }
    }

    override fun solvePartOne(): Int {
        return connections.flatMap { (c1, connectedTo) ->
            connectedTo.flatMap { c2 ->
                connections[c2].orEmpty()
                    .mapNotNull { c3 ->
                        val c3Conns = connections[c3].orEmpty()
                        if (c1 in c3Conns) {
                            listOf(c1, c2, c3)
                        } else {
                            null
                        }
                    }
            }
        }
            .map { it.toSet() }
            .toSet()
            .count { list -> list.any { it.startsWith("t") } }
    }

    override fun solvePartTwo(): Int {

        return 0
    }
    
}
