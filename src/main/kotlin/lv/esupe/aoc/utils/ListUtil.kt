package lv.esupe.aoc.utils


fun <T : Any> List<T>.asInfiniteSequence(): Sequence<T> {
    var index = 0
    return generateSequence(get(index)) { get(++index % size) }
}

inline fun <T> List<T>.withAllOtherElements(block: (T, T) -> Unit) {
    forEachIndexed { idx1, i ->
        forEachIndexed { idx2, j ->
            if (idx1 != idx2) block(i, j)
        }
    }
}