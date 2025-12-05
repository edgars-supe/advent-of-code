package lv.esupe.aoc.utils

infix fun IntRange.overlaps(other: IntRange): Boolean {
    return this.first <= other.last && other.first <= this.last
}

infix fun LongRange.overlaps(other: LongRange): Boolean {
    return this.first <= other.last && other.first <= this.last
}

fun IntRange.mergeWith(other: IntRange): IntRange {
    if (!(this overlaps other)) {
        throw IllegalArgumentException("Ranges must overlap to be merged")
    }

    return minOf(this.first, other.first)..maxOf(this.last, other.last)
}

fun LongRange.mergeWith(other: LongRange): LongRange {
    if (!(this overlaps other)) {
        throw IllegalArgumentException("Ranges must overlap to be merged")
    }

    return minOf(this.first, other.first)..maxOf(this.last, other.last)
}