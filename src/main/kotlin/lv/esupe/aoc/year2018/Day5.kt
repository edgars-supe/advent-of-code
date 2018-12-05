package lv.esupe.aoc.year2018

import lv.esupe.aoc.Puzzle


fun main(args: Array<String>) {
    Day5Puzzle1().calculateAndPrint()
    Day5Puzzle2().calculateAndPrint()
}

class Day5Puzzle1 : Puzzle<Int>(2018, 5, 1) {
    override fun calculate(): Int =
        input.first().destroyUnits().length
}

class Day5Puzzle2 : Puzzle<Int>(2018, 5, 2) {
    override fun calculate(): Int =
        ('a'..'z').map {
            input.first()
                .filterNot { c -> c.toLowerCase() == it }
                .destroyUnits()
                .length
        }
            .minBy { it }!!
}

private fun String.destroyUnits(): String {
    var polymer = this
    var destroyed = polymer.destroyFirstPair()
    while (polymer.length > destroyed.length) {
        polymer = destroyed
        destroyed = destroyed.destroyFirstPair()
    }
    return destroyed
}

private fun String.destroyFirstPair(): String {
    var idx = -1
    for (i in 0 until length) {
        if (get(i).sameCharDifferentCase(getOrNull(i + 1))) {
            idx = i
            break
        }
    }
    if (idx > -1) {
        return removeRange(idx, idx + 2)
    } else return this

}

private fun Char.sameCharDifferentCase(other: Char?): Boolean {
    if (other == null) return false
    if (isLowerCase()) return other.isUpperCase() && this.toUpperCase() == other
    else if (isUpperCase()) return other.isLowerCase() && this.toLowerCase() == other
    return false
}