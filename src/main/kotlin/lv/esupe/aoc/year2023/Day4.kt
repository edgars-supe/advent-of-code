package lv.esupe.aoc.year2023

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import kotlin.math.pow

fun main() = solve { Day4() }

class Day4 : Puzzle<Int, Int>(2023, 4) {
    override val input = rawInput.map { line ->
        val (winning, my) = line.substringAfter(": ").split(" | ")
        Card(
            winningNumbers = winning.trim().split(' ').mapNotNull { it.toIntOrNull() },
            myNumbers = my.trim().split(' ').mapNotNull { it.toIntOrNull() }
        )
    }

    override fun solvePartOne(): Int {
        return input.sumOf { it.points }
    }

    override fun solvePartTwo(): Int {
        val addedCards = mutableMapOf<Int, Int>()
        input.forEachIndexed { idx, _ ->
            getCardCount(idx, addedCards)
                .also { addedCards[idx] = it }
        }
        return addedCards.values.sum() + input.size
    }

    private fun getCardCount(idx: Int, memo: MutableMap<Int, Int>): Int {
        val memoCount = memo[idx]
        if (memoCount != null) return memoCount

        val card = input[idx]
        return card.matchingNumbers + (1..card.matchingNumbers)
            .sumOf { c -> getCardCount(idx + c, memo) }
    }

    data class Card(val winningNumbers: List<Int>, val myNumbers: List<Int>) {
        val matchingNumbers: Int by lazy { winningNumbers.count { n -> n in myNumbers } }
        val points: Int by lazy { getPoints(matchingNumbers) }

        private fun getPoints(winners: Int): Int {
            return if (winners == 0) 0 else 2.0.pow(winners - 1).toInt()
        }
    }
}
