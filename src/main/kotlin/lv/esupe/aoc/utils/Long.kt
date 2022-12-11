package lv.esupe.aoc.utils

import java.util.BitSet

fun Long.toBitSet(): BitSet {
    val bitSet = BitSet(Long.SIZE_BITS)
    for (idx in (0 until Long.SIZE_BITS)) {
        bitSet.set(Long.SIZE_BITS - 1 - idx, this shr idx and 1L == 1L)
    }
    return bitSet
}

fun BitSet.toLong(): Long {
    var value = 0L
    for (idx in (0 until Long.SIZE_BITS)) {
        value = value shl 1 or (if (get(idx)) 1L else 0L)
    }
    return value
}

fun List<Long>.product(): Long = fold(1) { acc, i -> acc * i }