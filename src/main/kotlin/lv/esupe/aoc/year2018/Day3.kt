package lv.esupe.aoc.year2018

import lv.esupe.aoc.Puzzle


fun main(args: Array<String>) {
    Day3Puzzle1().calculateAndPrint()
    Day3Puzzle2().calculateAndPrint()
}

class Day3Puzzle1 : Puzzle<Int>(2018, 3, 1) {
    override fun calculate(): Int {
        val sheet = createSheet()
        input.map { it.toClaim() }
            .forEach { sheet.plotClaim(it) }
        return sheet.sumBy { it.count { cell -> cell > 1 } }
    }
}

class Day3Puzzle2 : Puzzle<String>(2018, 3, 2) {
    override fun calculate(): String {
        val sheet = createSheet()
        val claims = input.map { it.toClaim() }
        claims.forEach { sheet.plotClaim(it) }
        return claims.first { sheet.hasNoOverlap(it) }.id
    }
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
