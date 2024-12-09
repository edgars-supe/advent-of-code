package lv.esupe.aoc.year2024

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day9() }

class Day9 : Puzzle<Long, Long>(2024, 9) {
    override val input = rawInput.first()

    private val fileBlocks = input.filterIndexed { idx, _ -> idx % 2 == 0 }
        .map(Char::digitToInt)

    private val emptyBlocks = input.filterIndexed { idx, _ -> idx % 2 == 1 }
        .map(Char::digitToInt)

    override fun solvePartOne(): Long {
        val files = fileBlocks.toMutableList()
        val emptys = emptyBlocks.toMutableList()
        var fileIdx = 0
        var emptyIdx = 0
        var checksum = 0L
        var currBlock = 0
        var checkFile = true
        var fileMoveIdx = fileBlocks.lastIndex
        while (fileMoveIdx >= fileIdx) {
            if (checkFile) {
                checksum += fileIdx * currBlock
                files[fileIdx] -= 1
                if (files[fileIdx] == 0) {
                    checkFile = false
                    fileIdx++
                }
            } else {
                val empty = emptys[emptyIdx]
                if (empty == 0) {
                    emptyIdx++
                    checkFile = true
                    continue
                } else {
                    checksum += fileMoveIdx * currBlock
                    files[fileMoveIdx] -= 1
                    if (files[fileMoveIdx] == 0) {
                        fileMoveIdx--
                    }
                    emptys[emptyIdx] -= 1
                }
            }
            currBlock++
        }
        return checksum
    }

    override fun solvePartTwo(): Long {
        return 0
    }
}
