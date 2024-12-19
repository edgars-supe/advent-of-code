package lv.esupe.aoc.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking


fun <T : Any> List<T>.asInfiniteSequence(): Sequence<T> {
    var index = 0
    return generateSequence(get(index)) { get(++index % size) }
}

fun <T> List<T>.repeat(times: Int): List<T> {
    val sequence = sequence { repeat(times) { yieldAll(this@repeat) } }
    val list = ArrayList<T>(sequence.count())
    sequence.forEach { list.add(it) }
    return list
}

inline fun <T> List<T>.forAllPairs(action: (T, T) -> Unit) {
    for (i in indices) {
        for (j in (i + 1) until size) {
            action(get(i), get(j))
        }
    }
}

inline fun <T, R> List<T>.mapAllPairs(mapper: (T, T) -> R): List<R> {
    val list = mutableListOf<R>()
    for (i in indices) {
        for (j in (i + 1) until size) {
            list += mapper(get(i), get(j))
        }
    }
    return list
}

fun <T> List<T>.getAllPairs(): List<Pair<T, T>> {
    return mapAllPairs { a, b -> a to b }
}

inline fun <T> List<T>.forAllUniquePairs(action: (T, T) -> Unit) {
    forAllPairs { i, j ->
        action(i, j)
        action(j, i)
    }
}

fun <T> List<T>.countOf(element: T): Int = count { it == element }

fun <A, B> Iterable<A>.pmap(f: suspend (A) -> B): List<B> = runBlocking(Dispatchers.Default) {
    map { async { f(it) } }.map { it.await() }
}

fun <A, B> Iterable<A>.pmapIndexed(f: suspend (Int, A) -> B): List<B> = runBlocking(Dispatchers.Default) {
    mapIndexed { idx, item -> async { f(idx, item) } }.map { it.await() }
}

fun <T> permute(list: List<T>): List<List<T>> {
    if (list.size == 1) return listOf(list)

    val perms = mutableListOf<List<T>>()
    val sub = list[0]
    for (perm in permute(list.drop(1)))
        for (i in 0..perm.size) {
            val newPerm = perm.toMutableList()
            newPerm.add(i, sub)
            perms.add(newPerm)
        }
    return perms
}

fun <T> List<T>.chunkedBy(selector: (T) -> Boolean): List<List<T>> =
    fold(mutableListOf(mutableListOf<T>())) { acc, item ->
        if (selector(item)) acc.add(mutableListOf())
        else acc.last().add(item)
        acc
    }

infix fun <T> T.prependTo(list: List<T>): List<T> = buildList { add(this@prependTo); addAll(list) }

fun <T> MutableList<T>.replace(element: T, newElement: T): Boolean {
    if (element !in this) return false
    val idx = indexOf(element)
    add(idx, newElement)
    remove(element)
    return true
}