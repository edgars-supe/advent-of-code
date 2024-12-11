package lv.esupe.aoc.utils

fun <T, R> memoize(function: (T) -> R): (T) -> R {
    val cache = mutableMapOf<T, R>()
    return { t -> cache.computeIfAbsent(t, function) }
}

fun <T, R, V> memoize(function: (T, R) -> V): (T, R) -> V {
    val cache = mutableMapOf<Pair<T, R>, V>()
    return { t, r -> cache.computeIfAbsent(t to r) { (t, r) -> function(t, r) } }
}