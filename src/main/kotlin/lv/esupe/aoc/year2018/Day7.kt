package lv.esupe.aoc.year2018

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.toAlphabetIndex


fun main(args: Array<String>) = solve { Day7() }

class Day7 : Puzzle<String, Int>(2018, 7) {
    override val input = rawInput.map { it[5] to it[36] }
    val steps = input.groupBy { it.first }
        .mapValues { it.value.map { it.second }.sorted() }
    val prerequisites = input.groupBy { it.second }
        .mapValues { it.value.map { it.first }.sorted() }
    val roots = steps.keys.subtract(prerequisites.keys).sorted()

    override fun solvePartOne(): String {
        val visited = LinkedHashSet<Char>()
        step(roots.toMutableSet(), visited, 1)
        return visited.joinToString("")
    }

    override fun solvePartTwo(): Int {
        val visited = LinkedHashSet<Char>()
        return step(roots.toMutableSet(), visited, 5)
    }

    private fun step(queue: MutableSet<Char>, visited: LinkedHashSet<Char>, maxWorkers: Int): Int {
        var time = 0
        val workers = mutableMapOf<Char, Int>()
        while (queue.isNotEmpty() || workers.isNotEmpty()) {
            val removed = workers
                .minBy { it.value }
                ?.let { min -> workers.filterValues { it == min.value } }
            removed?.keys?.sorted()?.forEach {
                workers.remove(it)
                visited.add(it)
                queue.addAll(steps[it].orEmpty())
            }
            time = removed?.values?.first() ?: 0
            queue.filter { prerequisites[it].orEmpty().all { it in visited } }
                .sorted()
                .take(maxWorkers - workers.size)
                .forEach {
                    queue.remove(it)
                    workers.addStep(it, time)
                }
        }
        return time
    }

    private fun MutableMap<Char, Int>.addStep(char: Char, startTime: Int) {
        put(char, startTime + char.time())
    }

    private fun Char.time() = toAlphabetIndex() + 1 + 60
}