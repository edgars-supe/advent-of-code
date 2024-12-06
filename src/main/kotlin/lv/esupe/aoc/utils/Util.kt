package lv.esupe.aoc.utils

import lv.esupe.aoc.model.Point


fun <T> Array<Array<T>>.forEachIndexed(action: (Int, Int, T) -> Unit) {
    forEachIndexed { i: Int, array: Array<T> ->
        array.forEachIndexed { j: Int, item: T ->
            action(i, j, item)
        }
    }
}

inline fun IntRange.over(other: IntRange, action: (Int, Int) -> Unit) {
    for (i in this) {
        for (j in other) {
            action(i, j)
        }
    }
}

inline fun LongRange.over(other: LongRange, action: (Long, Long) -> Unit) {
    for (i in this) {
        for (j in other) {
            action(i, j)
        }
    }
}

fun List<String>.toGrid(invertY: Boolean = false) = this
    .let { if (invertY) it.asReversed() else it }
    .flatMapIndexed { col, line ->
        line.mapIndexed { row, char ->
            Point(row, col) to char
        }
    }
    .associate { it }
