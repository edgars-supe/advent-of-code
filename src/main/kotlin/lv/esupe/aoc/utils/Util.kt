package lv.esupe.aoc.utils


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