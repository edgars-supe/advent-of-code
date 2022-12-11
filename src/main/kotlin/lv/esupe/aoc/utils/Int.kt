package lv.esupe.aoc.utils

import kotlin.math.pow

/**
 * Returns the Int "trimmed" to `n` digits. E.g., `123.trim(1) == 3`, `123.trim(2) == 23` and so on.
 */
fun Int.trim(n: Int) = rem(10.0.pow(n)).toInt()

fun List<Int>.product(): Int = fold(1) { acc, i -> acc * i }