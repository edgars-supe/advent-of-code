package lv.esupe.aoc.year2022

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day2() }

class Day2 : Puzzle<Int, Int>(2022, 2) {
    override val input = rawInput.map { line -> line.split(" ") }

    override fun solvePartOne(): Int {
        return input
            .map { (elf, me) ->
                when (elf to me) {
                    "A" to "X" -> 4 // rock:1 + draw:3
                    "A" to "Y" -> 8 // paper:2 + win:6
                    "A" to "Z" -> 3 // scissors:3 + lose:0
                    "B" to "X" -> 1 // rock:1 + lose:0
                    "B" to "Y" -> 5 // paper:2 + draw:3
                    "B" to "Z" -> 9 // scissors:3 + win:6
                    "C" to "X" -> 7 // rock:1 + win:6
                    "C" to "Y" -> 2 // paper:2 + lose:0
                    "C" to "Z" -> 6 // scissors:3 + draw:3
                    else -> error("$elf vs $me does not make sense")
                }
            }
            .sum()
    }

    override fun solvePartTwo(): Int {
        return input
            .map { (elf, me) ->
                when (elf to me) {
                    "A" to "X" -> 3 // scissors:3 + lose:0
                    "A" to "Y" -> 4 // rock:1 + draw:3
                    "A" to "Z" -> 8 // paper:2 + win:6
                    "B" to "X" -> 1 // rock:1 + lose:0
                    "B" to "Y" -> 5 // paper:2 + draw:3
                    "B" to "Z" -> 9 // scissors:3 + win:6
                    "C" to "X" -> 2 // paper:2 + lose:0
                    "C" to "Y" -> 6 // scissors:3 + draw:3
                    "C" to "Z" -> 7 // rock:1 + win:6
                    else -> error("$elf vs $me does not make sense")
                }
            }
            .sum()
    }
}
