package lv.esupe.aoc.year2022

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day13() }

class Day13 : Puzzle<Int, Int>(2022, 13) {
    override val input = rawInput
        .filter { it.isNotBlank() }
        .map { line -> Packet.from(line) }

    override fun solvePartOne(): Int {
        return input.asSequence()
            .chunked(2) { (a, b) -> a < b }
            .withIndex()
            .filter { (_, match) -> match }
            .map { (idx, _) -> idx + 1 }
            .sum()
    }

    override fun solvePartTwo(): Int {
        val divider1 = Packet.from("[[2]]")
        val divider2 = Packet.from("[[6]]")
        val sorted = (input + listOf(divider1, divider2)).sorted()
        return (sorted.indexOf(divider1) + 1) * (sorted.indexOf(divider2) + 1)
    }

    sealed class Packet : Comparable<Packet> {
        data class I(val value: Int) : Packet() {
            override fun compareTo(other: Packet): Int {
                return when (other) {
                    is I -> this.value.compareTo(other.value)
                    is L -> L(listOf(this)).compareTo(other)
                }
            }
        }

        data class L(val packets: List<Packet>) : Packet() {
            override fun compareTo(other: Packet): Int {
                return when (other) {
                    is I -> compareTo(L(listOf(other)))
                    is L -> {
                        packets.zip(other.packets)
                            .map { (a, b) -> a.compareTo(b) }
                            .firstOrNull { it != 0 }
                            ?: packets.size.compareTo(other.packets.size)
                    }
                }
            }
        }

        companion object {
            fun from(line: String): L {
                var index = 1

                fun parse(): L {
                    val subpackets = mutableListOf<Packet>()
                    while (index < line.length) {
                        // index is at '['
                        val char = line[index++]
                        if (char.isDigit()) {
                            val numberString = line.substring(index - 1).takeWhile { it.isDigit() }
                            subpackets.add(I(numberString.toInt()))
                            index = index + numberString.length - 1
                        } else if (char == '[') {
                            subpackets.add(parse())
                        } else if (char == ']') {
                            return L(subpackets)
                        }
                    }
                    error("Something went wrong")
                }

                return parse()
            }
        }
    }
}
