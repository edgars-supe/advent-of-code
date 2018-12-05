package lv.esupe.aoc.utils

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking


fun <T : Any> List<T>.asInfiniteSequence(): Sequence<T> {
    var index = 0
    return generateSequence(get(index)) { get(++index % size) }
}

inline fun <T> List<T>.forAllPairs(block: (T, T) -> Unit) {
    for (i in 0 until size) {
        for (j in (i + 1) until size) {
            block(get(i), get(j))
        }
    }
}

inline fun <T> List<T>.forAllUniquePairs(block: (T, T) -> Unit) {
    forAllPairs { i, j ->
        block(i, j)
        block(j, i)
    }
}

fun <A, B> Iterable<A>.pmap(f: suspend (A) -> B): List<B> = runBlocking {
    map { async { f(it) } }.map { it.await() }
}