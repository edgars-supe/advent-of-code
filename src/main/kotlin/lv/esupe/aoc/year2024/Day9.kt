package lv.esupe.aoc.year2024

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.replace

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
        var block = 0
        var fileId = 0
        val drive = fileBlocks.zip(emptyBlocks + 0)
            .flatMap { (f, e) ->
                val file = BlockSet.File(fileId++, block, f)
                block += f
                val empty = BlockSet.Empty(block, e)
                block += e
                listOf(file, empty)
            }
            .toMutableList()
        val files = drive.filterIsInstance<BlockSet.File>()
        for (file in files.asReversed()) {
            val empty = drive
                .firstOrNull { b -> b.startIdx < file.startIdx && b is BlockSet.Empty && b.size >= file.size } as? BlockSet.Empty
                ?: continue
            val newFile = file.copy(startIdx = empty.startIdx)
            val emptyIdx = drive.indexOf(empty)
            val fileEmpty = BlockSet.Empty(file.startIdx, file.size)
            drive.replace(file, fileEmpty)
            drive.add(emptyIdx, newFile)
            if (empty.size == file.size) {
                drive.remove(empty)
            } else {
                val newEmpty = empty.copy(size = empty.size - file.size, startIdx = empty.startIdx + file.size)
                drive.replace(empty, newEmpty)
            }
        }
        return drive.filterIsInstance<BlockSet.File>().sumOf { it.checksum() }
    }

    private sealed class BlockSet {
        abstract val startIdx: Int
        abstract val size: Int

        data class File(val id: Int, override val startIdx: Int, override val size: Int) : BlockSet() {
            fun checksum(): Long {
                return (startIdx until startIdx + size).sum() * id.toLong()
            }
        }

        data class Empty(override val startIdx: Int, override val size: Int) : BlockSet()
    }
}
