package lv.esupe.aoc.year2022

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day7() }

class Day7 : Puzzle<Int, Int>(2022, 7) {

    override val input = rawInput.drop(1)

    private val rootDir: Dir = Dir("/", mutableListOf(), null)

    init {
        var currentDir = rootDir
        input.forEach { line ->
            if (line.startsWith('$')) {
                parseInput(line, currentDir)?.let { currentDir = it }
            } else {
                parseOutput(line, currentDir)
            }
        }
    }

    private fun parseInput(line: String, currentDir: Dir): Dir? {
        val l = line.split(' ')
        if (l[1] != "cd") return null

        return if (l[2] == "..") {
            currentDir.parent!!
        } else {
            currentDir.children.first { it.name == l[2] }
        }
    }

    private fun parseOutput(line: String, currentDir: Dir) {
        val l = line.split(' ')
        if (l[0] == "dir") { // "dir foobar"
            currentDir.children.add(Dir(l[1], mutableListOf(), currentDir))
        } else { // "12345 foobar"
            val size = l[0].toInt()
            currentDir.addFile(size)
        }
    }

    override fun solvePartOne(): Int {
        var smallDirTotalSize = 0
        traverseDirs { dir ->
            if (dir.totalSize <= PART_1_THRESHOLD) smallDirTotalSize += dir.totalSize
        }
        return smallDirTotalSize
    }

    override fun solvePartTwo(): Int {
        val freeSpace = TOTAL_SPACE - rootDir.totalSize
        val toDelete = REQUIRED_FREE_SPACE - freeSpace
        var minSize = Int.MAX_VALUE
        traverseDirs(filter = { dir -> dir.totalSize > toDelete }) { dir ->
            if (dir.totalSize < minSize) minSize = dir.totalSize
        }
        return minSize
    }

    private fun traverseDirs(filter: (Dir) -> Boolean = { true }, onEach: (Dir) -> Unit) {
        val queue = ArrayDeque<Dir>()
        queue.add(rootDir)
        while (queue.isNotEmpty()) {
            val current = queue.removeLast()
            val dirs = current.children.filter(filter)
            queue.addAll(dirs)
            onEach(current)
        }
    }

    class Dir(val name: String, val children: MutableList<Dir>, val parent: Dir?) {
        var fileSize: Int = 0
            private set

        val totalSize: Int by lazy { children.sumOf { it.totalSize } + fileSize }

        fun addFile(size: Int) {
            fileSize += size
        }
    }

    companion object {
        const val TOTAL_SPACE = 70_000_000
        const val REQUIRED_FREE_SPACE = 30_000_000
        const val PART_1_THRESHOLD = 100_000
    }
}
