package lv.esupe.aoc.year2018

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.utils.forAllPairs
import lv.esupe.aoc.utils.asString


fun main(args: Array<String>) {
    Day2Puzzle1().calculateAndPrint()
    Day2Puzzle2().calculateAndPrint()
}

class Day2Puzzle1 : Puzzle<Int>(2018, 2, 1) {
    override fun calculate(): Int {
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
}

class Day2Puzzle2 : Puzzle<String>(2018, 2, 2) {
    override fun calculate(): String {
        input.forAllPairs { s1, s2 ->
            s1.zip(s2)
                .takeIf { pair -> pair.count { it.first != it.second } == 1 }
                ?.unzip()
                ?.let { it.first.asString() to it.second.asString() }
                ?.let { return it.filterMatchingChars() }
        }
        throw Exception("Christmas is cancelled")
    }

    private fun Pair<String, String>.filterMatchingChars(): String = first.zip(second)
        .filter { it.first == it.second }
        .map { it.first }
        .asString()
}