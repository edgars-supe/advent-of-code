package lv.esupe.aoc.year2018

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.utils.charByChar
import lv.esupe.aoc.utils.withAllOtherElements


fun main(args: Array<String>) {
    Day2Puzzle1().calculateAndPrint()
    Day2Puzzle2().calculateAndPrint()
}

class Day2Puzzle1 : Puzzle<Int>(2018, 2, 1) {
    override fun calculate(): Int {
        var twos = 0
        var threes = 0
        input.forEach {
            it.forEach { c ->
                if (it.count { c1 -> c == c1 } == 2) twos++
                else if (it.count { c1 -> c == c1 } == 3) threes++
            }
        }
        return twos * threes
    }
}

class Day2Puzzle2 : Puzzle<String>(2018, 2, 2) {
    override fun calculate(): String {
        var pair: Pair<String, String>? = null
        input.withAllOtherElements { s1, s2 ->
            var hasOneDiscrepancy = false
            for (idx in 0 until s1.length) {
                val c = s1[idx]
                if (s2[idx] != c && hasOneDiscrepancy) {
                    hasOneDiscrepancy = false
                    break
                } else if (s2[idx] != c && !hasOneDiscrepancy) {
                    hasOneDiscrepancy = true
                }
            }
            if (hasOneDiscrepancy) pair = Pair(s1, s2)
        }
        var id = ""
        if (pair != null) {
            pair!!.first.charByChar(pair!!.second) { c1, c2 ->
                if (c1 == c2 && c1 != null) id += c1
            }
        }
        return id
    }
}