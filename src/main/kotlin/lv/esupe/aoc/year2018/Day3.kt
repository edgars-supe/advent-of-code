package lv.esupe.aoc.year2018

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve


fun main() = solve { Day3() }

class Day3 : Puzzle<Int, String>(2018, 3) {
    override val input = rawInput.map { it.toClaim() }
    val sheet = createSheet().apply { input.forEach { plotClaim(it) } }

    override fun solvePartOne(): Int = sheet.sumBy { it.count { cell -> cell > 1 } }

    override fun solvePartTwo(): String = input.first { sheet.hasNoOverlap(it) }.id
}

data class Claim(
    val id: String,
    val x: Int,
    val y: Int,
    val width: Int,
    val height: Int
)

private fun createSheet() = Array(1000) { Array(1000) { 0 } }

private fun String.toClaim(): Claim = split(" ")
    .let {
        val (x, y) = it[2].split(',').map { it.removeSuffix(":").toInt() }
        val (width, height) = it[3].split('x').map { it.toInt() }
        Claim(it[0], x, y, width, height)
    }

private fun Array<Array<Int>>.plotClaim(it: Claim) {
    for (i in it.x until it.x + it.width) {
        for (j in it.y until it.y + it.height) {
            this[i][j]++
        }
    }
}

private fun Array<Array<Int>>.hasNoOverlap(it: Claim): Boolean {
    for (i in it.x until it.x + it.width) {
        for (j in it.y until it.y + it.height) {
            if (this[i][j] > 1) return false
        }
    }
    return true
}
