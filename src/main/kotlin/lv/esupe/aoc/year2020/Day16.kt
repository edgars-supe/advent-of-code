package lv.esupe.aoc.year2020

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.chunkedBy

fun main() = solve { Day16() }

class Day16 : Puzzle<Int, Long>(2020, 16) {
    override val input = rawInput.chunkedBy { it.isBlank() }
        .let { chunks ->
            val fields = chunks[0].map { Field.from(it) }
            val myTicket = Ticket.from(chunks[1][1])
            val tickets = chunks[2].drop(1).map { Ticket.from(it) }
            Input(fields, myTicket, tickets)
        }

    private val fieldCount = input.fields.count()

    override fun solvePartOne(): Int = input.tickets
        .flatMap { it.values }
        .filter { !input.isValidForAnyField(it) }
        .sum()

    override fun solvePartTwo(): Long {
        val validTickets = input.tickets.filterNot { ticket -> ticket.values.any { !input.isValidForAnyField(it) } }

        return (0 until fieldCount)
            .associateWith { idx ->
                input.fields.filter { field ->
                    validTickets.all { ticket -> field.isValid(ticket.values[idx]) }
                }
            }
            .toList()
            .sortedBy { (_, fields) -> fields.count() }
            .fold(mutableMapOf<Field, Int>()) { acc, (idx, fields) ->
                acc[fields.first { it !in acc.keys }] = idx
                acc
            }
            .filterKeys { it.name.startsWith("departure") }
            .values
            .fold(1L) { acc, i -> acc * input.myTicket.values[i] }
    }

    data class Input(val fields: List<Field>, val myTicket: Ticket, val tickets: List<Ticket>) {
        fun isValidForAnyField(value: Int) = fields.any { it.isValid(value) }
    }

    data class Field(val name: String, val range1: IntRange, val range2: IntRange) {
        companion object {
            private val regex = Regex("""(.+): ([\d]+)-([\d]+) or ([\d]+)-([\d]+)""")
            fun from(line: String): Field {
                val result = regex.find(line) ?: error("Can't parse field")
                val (name, range1Min, range1Max, range2Min, range2Max) = result.destructured
                return Field(name, range1Min.toInt()..range1Max.toInt(), range2Min.toInt()..range2Max.toInt())
            }
        }

        fun isValid(value: Int) = value in range1 || value in range2
    }

    data class Ticket(val values: List<Int>) {
        companion object {
            fun from(line: String): Ticket = Ticket(line.split(",").map { it.toInt() })
        }
    }
}
