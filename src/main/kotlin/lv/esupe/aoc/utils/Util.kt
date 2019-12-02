package lv.esupe.aoc.utils


inline fun String.charByChar(other: String, crossinline block: (Char?, Char?) -> Unit) {
    val max = maxOf(length, other.length)
    for (i in 0 until max) {
        val c1 = getOrNull(i)
        val c2 = other.getOrNull(i)
        block(c1, c2)
    }
}

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