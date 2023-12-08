package lv.esupe.aoc.year2023

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.asInfiniteSequence
import lv.esupe.aoc.utils.lcm

fun main() = solve { Day8() }

class Day8 : Puzzle<Long, Long>(2023, 8) {
    override val input = rawInput
    private val instructions = input[0]
    private val nodes = rawInput.drop(2)
        .map { line ->
            val name = line.substring(0..2)
            val left = line.substring(7..9)
            val right = line.substring(12..14)
            Node(name, left, right)
        }
        .associateBy { it.name }

    override fun solvePartOne(): Long {
        val node = nodes.getValue("AAA")
        return findStepsTaken(node) { current -> current.name == "ZZZ" }
    }

    override fun solvePartTwo(): Long {
        val startNodes = nodes.values.filter { it.name.endsWith('A') }.toMutableList()
        val stepsTaken = startNodes.map { node -> findStepsTaken(node) { current -> current.name.endsWith('Z') } }
        return stepsTaken.lcm()
    }

    private fun findStepsTaken(startNode: Node, target: (currentNode: Node) -> Boolean): Long {
        var stepsTaken = 0L
        var node = startNode
        val iterator = instructions.toList().asInfiniteSequence().iterator()
        while (true) {
            val instruction = iterator.next()
            when (instruction) {
                'L' -> node = nodes.getValue(node.left)
                'R' -> node = nodes.getValue(node.right)
            }
            stepsTaken++
            if (target(node)) break
        }
        return stepsTaken
    }

    data class Node(val name: String, val left: String, val right: String)
}
