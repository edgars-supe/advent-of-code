package lv.esupe.aoc.utils

fun <T, R> memoize(function: (T) -> R): (T) -> R {
    val cache = mutableMapOf<T, R>()
    return { t -> cache.getOrPut(t) { function(t) } }
}

fun <T, R, V> memoize(function: (T, R) -> V): (T, R) -> V {
    val cache = mutableMapOf<Pair<T, R>, V>()
    return { t, r -> cache.getOrPut(t to r) { function(t, r) } }
}