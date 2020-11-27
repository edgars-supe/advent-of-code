package lv.esupe.aoc.year2019

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import kotlin.math.absoluteValue

fun main() = solve { Day22() }

class Day22 : Puzzle<Int, Int>(2019, 22) {
    override val input = rawInput.map { Technique.from(it) }

    override fun solvePartOne(): Int {
        val deck = List(10007) { it }
        val result = input.fold(deck) { d, t -> t.execute(d) }
        return result.indexOf(2019)
    }

    override fun solvePartTwo(): Int = 0

    sealed class Technique {
        companion object {
            fun from(string: String) = when {
                string.startsWith("cut") -> {
                    val by = string.split(" ").last().toInt()
                    Cut(by)
                }
                string.startsWith("deal with") -> {
                    val n = string.split(" ").last().toInt()
                    Increment(n)
                }
                else -> NewStack()
            }
        }

        class NewStack : Technique() {
            override fun execute(deck: List<Int>): List<Int> = deck.reversed()
        }

        class Cut(val by: Int) : Technique() {
            override fun execute(deck: List<Int>): List<Int> = if (by > 0) {
                deck.drop(by) + deck.take(by)
            } else {
                val n = by.absoluteValue
                deck.takeLast(n) + deck.dropLast(n)
            }
        }

        class Increment(val n: Int) : Technique() {
            override fun execute(deck: List<Int>): List<Int> {
                val list = MutableList(deck.size) { 0 }
                var i = 0
                deck.forEach { card ->
                    list[i] = card
                    i = (i + n) % deck.size
                }
                return list
            }
        }

        abstract fun execute(deck: List<Int>): List<Int>
    }
}
