package lv.esupe.aoc.year2018

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve


fun main() = solve { Day9() }

class Day9 : Puzzle<Long, Long>(2018, 9) {
    override val input = rawInput.first().split(" ")
    private val players = input[0].toInt()
    private val marbles = input[6].toInt()
    private val circle = ArrayList<Int>(marbles * 100)
    private val scores = MutableList(players) { 0L }

    override fun solvePartOne(): Long {
        play(marbles)
        return scores.maxOrNull()!!
    }

    override fun solvePartTwo(): Long {
        for (i in 0 until circle.size) { circle[i] = 0 }
        for (i in 0 until scores.size) { scores[i] = 0L }
        play(marbles * 100)
        return scores.maxOrNull()!!
    }

    private fun play(marbleCount: Int) {
        var currentPlayer = 0
        var current = Marble(0).apply {
            prev = this
            next = this
        }
        for (marble in 1..marbleCount) {
            if (marble % 23L == 0L) {
                scores[currentPlayer] += marble.toLong()
                var m: Marble? = current
                repeat(7) { m = m?.prev }
                current = m?.next!!
                scores[currentPlayer] += (m?.remove() ?: 0).toLong()
            } else {
                val m = Marble(marble)
                current = current.next!!
                m.prev = current
                m.next = current.next
                m.next?.prev = m
                current.next = m
                current = m
            }
            currentPlayer = nextPlayer(currentPlayer)
        }
    }

    private fun nextPlayer(currentPlayer: Int) = (currentPlayer + 1) % players
}

class Marble(val value: Int) {
    var prev: Marble? = null
    var next: Marble? = null

    fun remove(): Int {
        prev?.next = next
        next?.prev = prev
        return value
    }
}
