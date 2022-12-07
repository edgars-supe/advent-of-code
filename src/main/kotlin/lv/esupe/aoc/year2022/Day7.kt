package lv.esupe.aoc.year2022

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day7() }

class Day7 : Puzzle<Int, Int>(2022, 7) {

    override val input = rawInput.drop(1)

    private val rootDir: Node.Dir

    init {
        rootDir = Node.Dir("/", mutableListOf(), null)
        var currentDir = rootDir
        input.forEach { line ->
            if (line.startsWith('$')) {
                val l = line.split(' ')
                if (l[1] == "cd") {
                    currentDir = if (l[2] != "..") {
                        currentDir.children.first { it is Node.Dir && it.name == l[2] } as Node.Dir
                    } else {
                        currentDir.parent!!
                    }
                }
            } else {
                val l = line.split(' ')
                if (l[0] == "dir") {
                    currentDir.children.add(Node.Dir(l[1], mutableListOf(), currentDir))
                } else { // file
                    val size = l[0].toInt()
                    currentDir.children.add(Node.File(l[1], size))
                }
            }
        }
    }

    override fun solvePartOne(): Int {
        var smallDirTotalSize = 0
        traverseDirs { dir ->
            if (dir.size() <= PART_1_THRESHOLD) smallDirTotalSize += dir.size()
        }
        return smallDirTotalSize
    }

    override fun solvePartTwo(): Int {
        val freeSpace = TOTAL_SPACE - rootDir.size()
        val toDelete = REQUIRED_FREE_SPACE - freeSpace
        var minSize = Int.MAX_VALUE
        traverseDirs(filter = { dir -> dir.size() > toDelete }) { dir ->
            if (dir.size() < minSize) minSize = dir.size()
        }
        return minSize
    }

    private fun traverseDirs(filter: (Node) -> Boolean = { true }, onEach: (Node.Dir) -> Unit) {
        val queue = ArrayDeque<Node>()
        queue.add(rootDir)
        while (queue.isNotEmpty()) {
            val current = queue.removeLast()
            if (current is Node.Dir) {
                val dirs = current.children.filter(filter)
                queue.addAll(dirs)
                onEach(current)
            }
        }
    }

    sealed class Node {
        data class Dir(val name: String, val children: MutableList<Node>, val parent: Dir?) : Node() {
            private val _size: Int by lazy { children.sumOf { it.size() } }

            override fun size(): Int = _size
        }

        data class File(val name: String, val size: Int): Node() {
            override fun size(): Int = size
        }

        abstract fun size(): Int
    }

    companion object {
        const val TOTAL_SPACE = 70_000_000
        const val REQUIRED_FREE_SPACE = 30_000_000
        const val PART_1_THRESHOLD = 100_000
    }
}
