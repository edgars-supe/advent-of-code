package lv.esupe.aoc.year2022

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day5() }

class Day5 : Puzzle<String, String>(2022, 5) {
    private val stackNumberLine = rawInput.indexOfFirst { line -> '[' !in line }
    private val stackCount = rawInput[stackNumberLine - 1].count { it == '[' }
    private val stacks = List(stackCount) { ArrayDeque<Char>() }
    private val commands = rawInput.drop(stackNumberLine + 2)
        .map { line ->
            val split = line.split(' ')
            val count = split[1].toInt()
            val from = split[3].toInt() - 1
            val to = split[5].toInt() - 1
            Command(count, from, to)
        }

    override val input = rawInput

    init {
        rawInput.take(stackNumberLine)
            .reversed()
            .map { line -> line.chunked(4) }
            .forEach { chunks ->
                chunks
                    .withIndex()
                    .forEach { (idx, chunk) ->
                        if (chunk[1].isLetter()) stacks[idx].add(chunk[1])
                    }
            }
    }

    override fun solvePartOne(): String {
        val stacks = stacks.map { ArrayDeque(it) }
        commands.forEach { command ->
            val source = stacks[command.source]
            val target = stacks[command.target]
            repeat(command.count) {
                target.add(source.removeLast())
            }
        }
        return stacks.joinToString(separator = "") { stack -> stack.last().toString() }
    }

    override fun solvePartTwo(): String {
        commands.forEach { command ->
            val source = stacks[command.source]
            val target = stacks[command.target]
            val removeIdx = source.size - (command.count)
            repeat(command.count) {
                val char = source.removeAt(removeIdx)
                target.add(char)
            }
        }
        return stacks.joinToString(separator = "") { stack -> stack.last().toString() }
    }

    data class Command(val count: Int, val source: Int, val target: Int)
}
