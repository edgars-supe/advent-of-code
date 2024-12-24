package lv.esupe.aoc.year2024

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.chunkedBy

fun main() = solve { Day24() }

class Day24 : Puzzle<Long, Long>(2024, 24) {
    override val input = rawInput
    private val states = mutableMapOf<String, Boolean>()
    private val connections: List<Connection>

    init {
        val (inits, conns) = input.chunkedBy { it.isBlank() }
        inits.forEach { line ->
            val (wire, state) = line.split(": ")
            states[wire] = state == "1"
        }
        connections = conns.map { line ->
            val (a, op, b, _, output) = line.split(" ")
            Connection(a, Op.valueOf(op), b, output)
        }
    }

    override fun solvePartOne(): Long {
        val queue = connections.toMutableList()
        while (queue.isNotEmpty()) {
            val conn = queue.removeFirst()
            if (conn.a !in states || conn.b !in states) {
                queue.add(conn)
                continue
            }

            with(conn) {
                states[output] = op.get(states.getValue(a), states.getValue(b))
            }
        }

        return states.filterKeys { key -> key.matches(Z_REGEX) }
            .toList()
            .sortedByDescending { (key, _) -> key }
            .fold(0) { acc, (_, value) ->
                (acc shl 1) + if (value) 1 else 0
            }
    }

    override fun solvePartTwo(): Long {
        return 0
    }

    private data class Connection(
        val a: String,
        val op: Op,
        val b: String,
        val output: String
    )

    private enum class Op {
        AND {
            override fun get(a: Boolean, b: Boolean): Boolean = a && b
        },
        OR {
            override fun get(a: Boolean, b: Boolean): Boolean = a || b
        },
        XOR {
            override fun get(a: Boolean, b: Boolean): Boolean = a xor b
        };

        abstract fun get(a: Boolean, b: Boolean): Boolean
    }

    companion object {
        private val Z_REGEX = """z\d{2}""".toRegex()
    }
}
