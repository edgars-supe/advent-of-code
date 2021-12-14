package lv.esupe.aoc.utils

fun <K, V> Map<K, V>.mergedWith(map: Map<K, V>, merge: (key: K, a: V, b: V?) -> V): Map<K, V> {
    return toMutableMap().mergeWith(map, merge)
}

fun <K, V> MutableMap<K, V>.mergeWith(map: Map<K, V>, merge: (key: K, a: V, b: V?) -> V): MutableMap<K, V> {
    map.forEach { (key: K, a: V) ->
        compute(key) { _, b: V? -> merge(key, a, b) }
    }
    return this
}
