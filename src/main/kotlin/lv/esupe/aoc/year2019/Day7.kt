package lv.esupe.aoc.year2019

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.last
import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.permute
import lv.esupe.aoc.year2019.model.Intcode
import lv.esupe.aoc.year2019.model.toProgram

fun main() = solve { Day7() }

class Day7 : Puzzle<Long, Long>(2019, 7) {

    override val input = rawInput[0].toProgram()

    override fun solvePartOne(): Long = runBlocking {
        permute(listOf(0L, 1L, 2L, 3L, 4L))
            .map { perm ->
                (0..4).fold(0L) { inp, i ->
                    Intcode(input).execute(inputs = listOf(perm[i], inp)).last()
                }
            }
            .max()!!
    }

    override fun solvePartTwo(): Long = runBlocking(Dispatchers.Default) {
        permute(listOf(5L, 6L, 7L, 8L, 9L))
            .map { perm -> calculateOutput(perm) }
            .max()!!
    }

    private suspend fun calculateOutput(perm: List<Long>): Long {
        val a = Intcode(input).also { it.input(perm[0], 0) }
        val b = Intcode(input, a.output).also { it.input(perm[1]) }
        val c = Intcode(input, b.output).also { it.input(perm[2]) }
        val d = Intcode(input, c.output).also { it.input(perm[3]) }
        val e = Intcode(input, d.output, a.input).also { it.input(perm[4]) }
        coroutineScope {
            launch { a.execute() }
            launch { b.execute() }
            launch { c.execute() }
            launch { d.execute() }
            launch { e.execute() }
        }
        return e.output.last()
    }
}
