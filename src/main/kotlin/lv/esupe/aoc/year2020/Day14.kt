package lv.esupe.aoc.year2020

import lv.esupe.aoc.Puzzle
import lv.esupe.aoc.solve
import lv.esupe.aoc.utils.toBitSet
import lv.esupe.aoc.utils.toLong
import java.util.*

fun main() = solve { Day14() }

class Day14 : Puzzle<Long, Long>(2020, 14) {
    companion object {
        private const val MASK_BITS = 36
        private const val MASK_PAD = Long.SIZE_BITS - MASK_BITS
    }

    override val input = rawInput
        .mapNotNull { line ->
            when {
                line.startsWith("mask = ") -> Input.Mask.from(line)
                line.startsWith("mem") -> Input.Memory.from(line)
                else -> null
            }
        }

    override fun solvePartOne(): Long {
        val memory = mutableMapOf<Long, Long>()
        var mask = Array(MASK_BITS) { Mask.F }
        input.forEach { cmd ->
            when (cmd) {
                is Input.Mask -> mask = cmd.mask
                is Input.Memory -> memory[cmd.addr] = applyMaskToValue(mask, cmd.value)
            }
        }
        return memory.values.sum()
    }

    override fun solvePartTwo(): Long {
        val memory = mutableMapOf<Long, Long>()
        var mask = Array(MASK_BITS) { Mask.F }
        input.forEach { cmd ->
            when (cmd) {
                is Input.Mask -> mask = cmd.mask
                is Input.Memory -> getAddresses(cmd.addr.toBitSet(), mask).forEach { a -> memory[a] = cmd.value }
            }
        }
        return memory.values.sum()
    }

    private fun applyMaskToValue(mask: Array<Mask>, value: Long): Long {
        val valueBits = value.toBitSet()
        mask.forEachIndexed { idx, m ->
            when (m) {
                Mask.T -> valueBits.set(MASK_PAD + idx, true)
                Mask.F -> valueBits.set(MASK_PAD + idx, false)
                else -> { }
            }
        }
        return valueBits.toLong()
    }

    private fun getAddresses(addr: BitSet, mask: Array<Mask>): List<Long> = mask
        .onEachIndexed { idx, m -> if (m == Mask.T) addr.set(MASK_PAD + idx, true) }
        .indices
        .filter { mask[it] == Mask.X }
        .fold(listOf(addr)) { acc, idx ->
            acc.flatMap { bitSet ->
                listOf(
                    (bitSet.clone() as BitSet).apply { set(MASK_PAD + idx, true) },
                    (bitSet.clone() as BitSet).apply { set(MASK_PAD + idx, false) }
                )
            }
        }
        .map { it.toLong() }

    sealed class Input {
        class Mask(val mask: Array<Day14.Mask>) : Input() {
            companion object {
                fun from(line: String): Mask {
                    val mask = line.substringAfter("mask = ").map { c ->
                        when (c) {
                            '0' -> Day14.Mask.F
                            '1' -> Day14.Mask.T
                            'X' -> Day14.Mask.X
                            else -> error("Invalid mask")
                        }
                    }
                    return Mask(mask.toTypedArray())
                }
            }
        }

        data class Memory(val addr: Long, val value: Long) : Input() {
            companion object {
                fun from(line: String): Memory {
                    val addr = line.substringAfter("mem[").substringBefore(']').toLong()
                    val value = line.substringAfter(" = ").toLong()
                    return Memory(addr, value)
                }
            }
        }
    }

    enum class Mask {
        T,
        F,
        X
    }
}
