package lv.esupe.aoc.year2023

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.count

fun main() = solve { Day7() }

class Day7 : Puzzle<Int, Int>(2023, 7) {
    override val input = rawInput.map { line ->
        val (hand, bid) = line.split(" ")
        Play(hand, bid.toInt())
    }

    override fun solvePartOne(): Int {
        val cardStrengths = listOf('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A')
        val handTypes = input.associateWith { getHandType(it.hand) }
        val sorted = sortPlays(cardStrengths, handTypes)
        return sorted
            .mapIndexed { idx, play -> play.bid * (idx + 1) }
            .sum()
    }

    override fun solvePartTwo(): Int {
        val cardStrengths = listOf('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A')
        val handTypes = input.associateWith { getHandTypeWithJoker(it.hand) }
        val sorted = sortPlays(cardStrengths, handTypes)
        return sorted
            .mapIndexed { idx, play -> play.bid * (idx + 1) }
            .sum()
    }

    private fun getHandType(hand: String): HandType {
        val unique = hand.toSet()
        val counts = unique.associateWith { c -> hand.count(c) }
        return when (unique.size) {
            1 -> HandType.FiveOfAKind
            2 -> if (counts.any { (_, c) -> c == 4 }) HandType.FourOfAKind else HandType.FullHouse
            3 -> if (counts.count { (_, c) -> c == 2 } == 2) HandType.TwoPair else HandType.ThreeOfAKind
            4 -> HandType.OnePair
            else -> HandType.HighCard
        }
    }

    private fun getHandTypeWithJoker(hand: String): HandType {
        val unique = hand.toSet()
        val counts = unique.associateWith { c -> hand.count(c) }
        val jokerCount = counts['J']
        return when (unique.size) {
            1 -> HandType.FiveOfAKind
            2 -> {
                if (counts.any { (_, c) -> c == 4 }) {
                    if ('J' in unique) {
                        HandType.FiveOfAKind
                    } else {
                        HandType.FourOfAKind
                    }
                } else {
                    when (jokerCount) {
                        2 -> HandType.FiveOfAKind
                        3 -> HandType.FiveOfAKind
                        else -> HandType.FullHouse
                    }
                }
            }
            3 -> {
                if (counts.count { (_, c) -> c == 2 } == 2) {
                    when (jokerCount) {
                        1 -> HandType.FullHouse
                        2 -> HandType.FourOfAKind
                        else -> HandType.TwoPair
                    }
                } else {
                    when (jokerCount) {
                        1 -> HandType.FourOfAKind
                        3 -> HandType.FourOfAKind
                        else -> HandType.ThreeOfAKind
                    }
                }
            }
            4 -> {
                when (jokerCount) {
                    1 -> HandType.ThreeOfAKind
                    2 -> HandType.ThreeOfAKind
                    else -> HandType.OnePair
                }
            }
            else -> {
                if (jokerCount == 1) HandType.OnePair else HandType.HighCard
            }
        }
    }

    private fun sortPlays(cardStrengths: List<Char>, handTypes: Map<Play, HandType>): List<Play> {
        return input.sortedWith(
            compareBy<Play> { handTypes[it] }
                .thenComparing { left, right ->
                    left.hand.zip(right.hand).forEach { (l, r) ->
                        if (l != r) {
                            return@thenComparing cardStrengths.indexOf(l) - cardStrengths.indexOf(r)
                        }
                    }

                    return@thenComparing 0
                }
        )
    }

    data class Play(val hand: String, val bid: Int)

    enum class HandType {
        HighCard,
        OnePair,
        TwoPair,
        ThreeOfAKind,
        FullHouse,
        FourOfAKind,
        FiveOfAKind,
    }
}
