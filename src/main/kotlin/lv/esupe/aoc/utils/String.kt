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

fun List<String>.transposed(): List<String> {
    require(isNotEmpty()) { "Input must not be empty." }
    val width = first().length
    require(all { it.length == width }) { "All strings must have the same length." }

    return List(width) { col ->
        buildString(size) {
            for (row in this@transposed.indices) {
                this.append(this@transposed[row][col])
            }
        }
    }
}

fun List<String>.rotated90(ccw: Boolean): List<String> {
    return if (ccw) {
        transposed().reversed()
    } else {
        transposed().map { it.reversed() }
    }
}

fun List<String>.rotated180(): List<String> =
    reversed().map { it.reversed() }
