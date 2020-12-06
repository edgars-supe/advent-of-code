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
