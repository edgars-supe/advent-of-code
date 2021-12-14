package lv.esupe.aoc.year2021

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.mergeWith

fun main() = solve { Day14() }

class Day14 : Puzzle<Long, Long>(2021, 14) {
    override val input = rawInput
    private val template = rawInput[0]
    private val nodes: Set<Node> = rawInput.drop(2)
        .map { line -> line.split(" -> ") }
        .associateBy( // create pair -> insert mapping (AB -> c)
            keySelector = { rule -> Node(rule.first()) },
            valueTransform = { rule -> rule.last()[0] }
        )
        .also { rules ->
            rules.forEach { (node, insert) -> // set insert nodes: AB -> C => AB -> AC and AB -> CB
                node.left = rules.keys.first { other -> other.a == node.a && other.b == insert }
                node.right = rules.keys.first { it.a == insert && it.b == node.b }
            }
        }
        .keys

    private val merger = { _: Char, a: Long, b: Long? -> b?.plus(a) ?: a }

    override fun solvePartOne(): Long {
        return solve(steps = 10)
    }

    override fun solvePartTwo(): Long {
        return solve(steps = 40)
    }

    private fun solve(steps: Int): Long {
        val counts = template.windowed(size = 2, step = 1)
            .map { pair ->
                val node = nodes.first { it.pair == pair }
                poly(node, steps, 0).toMutableMap()
            }
            .reduce { acc, map -> acc.mergeWith(map, merger) }
            .mapValues { (char, counts) -> counts + template.count { it == char } }
        return counts.values.maxOf { it } - counts.values.minOf { it }
    }

    /**
     * Counts inserted characters for steps `step` until `target`. For the given node counts the inserted character,
     * recursively counts insertions for `node`'s children until `target`.
     */
    private fun poly(node: Node, target: Int, step: Int): Map<Char, Long> {
        if (step == target) return emptyMap()

        return node.counts[target - step]
            ?: mutableMapOf(node.left.b to 1L)
                .mergeWith(poly(node.left, target, step + 1), merger)
                .mergeWith(poly(node.right, target, step + 1), merger)
                .also { node.counts[target - step] = it }
    }

    class Node(val pair: String) {
        val a: Char get() = pair.first()
        val b: Char get() = pair.last()
        lateinit var left: Node
        lateinit var right: Node

        val counts: MutableMap<Int, Map<Char, Long>> = mutableMapOf()
    }
}
