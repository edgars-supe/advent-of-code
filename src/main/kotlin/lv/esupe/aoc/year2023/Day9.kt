package lv.esupe.aoc.year2023

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day9() }

class Day9 : Puzzle<Int, Int>(2023, 9) {
    override val input = rawInput.map { line -> line.split(" ").map { it.toInt() } }

    override fun solvePartOne(): Int {
        return input.sumOf { history ->
            getHistoryValue(
                history,
                isForward = true,
                filler = { curr, prev -> curr += curr.last() + prev.last() }
            )
        }
    }

    override fun solvePartTwo(): Int {
        return input.sumOf { history ->
            getHistoryValue(
                history,
                isForward = false,
                filler = { curr, prev -> curr.add(0, curr.first() - prev.first()) }
            )
        }
    }

    private fun getHistoryValue(
        history: List<Int>,
        isForward: Boolean,
        filler: (current: MutableList<Int>, previous: List<Int>) -> Unit
    ): Int {
        val sequences = mutableListOf(history.toMutableList())
        while (!sequences.last().all { it == 0 }) {
            sequences += sequences.last()
                .zipWithNext()
                .map { (a, b) -> b - a }
                .toMutableList()
        }

        sequences.last()
            .let { s -> if (isForward) s.add(0) else s.add(0, 0) }

        (sequences.lastIndex - 1 downTo 0)
            .forEach { idx -> filler(sequences[idx], sequences[idx + 1]) }
        return if (isForward) sequences[0].last() else sequences[0].first()
    }
}
