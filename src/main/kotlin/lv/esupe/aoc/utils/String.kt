package lv.esupe.aoc.utils

private val NUMBER_REGEX = """-?\d+""".toRegex()

inline fun String.charByChar(other: String, crossinline block: (Char?, Char?) -> Unit) {
    val max = maxOf(length, other.length)
    for (i in 0 until max) {
        val c1 = getOrNull(i)
        val c2 = other.getOrNull(i)
        block(c1, c2)
    }
}

fun String.count(char: Char): Int = count { it == char }

fun String.findAllInts(): List<Int> = NUMBER_REGEX.findAll(this).map { it.value.toInt() }.toList()