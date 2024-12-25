package lv.esupe.aoc.utils

fun <T> Sequence<T>.repeat() = sequence { while (true) yieldAll(this@repeat) }

fun <T> Sequence<T>.pairWith(other: Sequence<T>): Sequence<Pair<T, T>> {
    return flatMap { item ->
        other.map { other -> item to other }
    }
}