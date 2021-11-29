package lv.esupe.aoc.year2018

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve


fun main() = solve { Day8() }

class Day8 : Puzzle<Int, Int>(2018, 8) {
    override val input = rawInput.first().split(" ").map { it.toInt() }
    val nodes = mutableListOf<Node>()

    init {
        createNodes(null, input)
    }

    override fun solvePartOne(): Int = nodes.sumOf { it.metadata.sum() }

    override fun solvePartTwo(): Int = nodes.first().value()

    /**
     * Creates a node, assuming `input[0]` is the child count and `input[1]` is metadata count.
     * Adds nodes to [nodes].
     *
     * @return Offset of first metadata entry for parent Node
     */
    private fun createNodes(parent: Node?, input: List<Int>): Int {
        val node = Node()
        val childCount = input[0]
        val metadataCount = input[1]
        parent?.children?.add(node)
        nodes.add(node)
        var offset = 2
        for (i in 0 until childCount) {
            offset += createNodes(node, input.drop(offset))
        }
        for (i in 0 until metadataCount) {
            node.metadata.add(input[offset++])
        }
        return offset
    }

    class Node {
        val children = mutableListOf<Node>()
        val metadata = mutableListOf<Int>()

        fun value(): Int =
            if (children.isEmpty()) metadata.sum()
            else metadata.sumOf { child -> children.getOrNull(child - 1)?.value() ?: 0 }
    }
}
