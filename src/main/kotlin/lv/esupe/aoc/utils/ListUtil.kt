package lv.esupe.aoc.utils


fun <T : Any> List<T>.asInfiniteSequence(): Sequence<T> {
    var index = 0
    return generateSequence(get(index)) { get(++index % size) }
}

inline fun <T> List<T>.withAllOtherElements(block: (T, T) -> Unit) {
    for (i in 0 until size) {
        for (j in (i + 1) until size) {
            block(get(i), get(j))
        }
    }
}