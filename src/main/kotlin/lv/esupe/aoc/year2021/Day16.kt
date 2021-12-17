package lv.esupe.aoc.year2021

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve

fun main() = solve { Day16() }

class Day16 : Puzzle<Long, Long>(2021, 16) {
    private val reader = BitReader(rawInput[0])
    override val input = PacketParser(reader).parse()

    override fun solvePartOne(): Long {
        return sumVersions(input)
    }

    override fun solvePartTwo(): Long {
        return input.value()
    }

    private fun sumVersions(packet: Packet): Long {
        return when (packet) {
            is Packet.Literal -> packet.version.toLong()
            is Packet.Operator -> packet.version + packet.subpackets.sumOf { sumVersions(it) }
        }
    }

    sealed class Packet(val version: Int) {
        abstract fun value(): Long

        class Literal(version: Int, val literal: Long) : Packet(version) {
            override fun value(): Long = literal
        }

        class Operator(version: Int, val type: Int, val subpackets: List<Packet>) : Packet(version) {
            override fun value(): Long {
                return when (type) {
                    0 -> subpackets.sumOf { p -> p.value() }
                    1 -> subpackets.fold(1L) { acc, p -> acc * p.value() }
                    2 -> subpackets.minOf { p -> p.value() }
                    3 -> subpackets.maxOf { p -> p.value() }
                    5 -> {
                        val (a, b) = subpackets
                        if (a.value() > b.value()) 1 else 0
                    }
                    6 -> {
                        val (a, b) = subpackets
                        if (a.value() < b.value()) 1 else 0
                    }
                    7 -> {
                        val (a, b) = subpackets
                        if (a.value() == b.value()) 1 else 0
                    }
                    else -> error("Unknown type $type")
                }
            }
        }
    }

    class PacketParser(val reader: BitReader) {

        fun parse(): Packet {
            return readPacket()
        }

        private fun readPacket(): Packet {
            val version = reader.takeInt(3)
            val result = when (val type = reader.takeInt(3)) {
                4 -> readLiteral(version)
                else -> readOperator(version, type)
            }
            return result
        }

        private fun readLiteral(version: Int): Packet.Literal {
            var number = 0L
            var cont: Boolean
            do {
                cont = reader.takeBoolean()
                number = number shl 4 or reader.takeInt(4).toLong()
            } while (cont)
            return Packet.Literal(version, number)
        }

        private fun readOperator(version: Int, type: Int): Packet.Operator {
            val subpackets = if (!reader.takeBoolean()) {
                val subpacketLength = reader.takeInt(15)
                val targetIndex = reader.index + subpacketLength
                val subpackets = mutableListOf<Packet>()
                while (reader.index < targetIndex) {
                    subpackets.add(readPacket())
                }
                subpackets
            } else {
                val subpacketCount = reader.takeInt(11)
                (0 until subpacketCount).map { readPacket() }
            }
            return Packet.Operator(version, type, subpackets)
        }
    }

    class BitReader(val input: String) {
        val index: Int
            get() = charIndex * 4 + bitIndex
        private var bitIndex = 0
        private var charIndex = 0
        private var currDigit = input[charIndex].digitToInt(16)

        fun takeInt(bits: Int): Int {
            var result = 0
            repeat(bits) {
                result = result shl 1 or next()
            }
            return result
        }

        fun takeBoolean(): Boolean {
            return next() == 1
        }

        private fun next(): Int {
            val bit = (currDigit shr (3 - bitIndex)) and 1

            if (++bitIndex >= 4) {
                bitIndex = 0
                if (++charIndex < input.length) {
                    currDigit = input[charIndex].digitToInt(16)
                }
            }
            return bit
        }
    }
}
