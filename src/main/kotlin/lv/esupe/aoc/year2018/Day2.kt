package lv.esupe.aoc.year2018

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.utils.asString
import lv.esupe.aoc.utils.forAllPairs


fun main(args: Array<String>) = Day2().solve()

class Day2 : Puzzle<Int, String>(2018, 2) {
    override val input = rawInput

    override fun solvePartOne(): Int {
        var twos = 0
        var threes = 0
        input.forEach { id ->
            id.map { c -> id.count { c1 -> c == c1 } }
                .let {
                    if (it.contains(2)) twos++
                    if (it.contains(3)) threes++
                }
        }
        return twos * threes
    }

    override fun solvePartTwo(): String {
        input.forAllPairs { s1, s2 ->
            s1.zip(s2)
                .takeIf { pair -> pair.count { it.first != it.second } == 1 }
                ?.unzip()
                ?.let { it.first.asString() to it.second.asString() }
                ?.let { return it.filterMatchingChars() }
        }
        throw Exception("Christmas is cancelled")
    }
}

private fun Pair<String, String>.filterMatchingChars(): String = first.zip(second)
    .filter { it.first == it.second }
    .map { it.first }
    .asString()