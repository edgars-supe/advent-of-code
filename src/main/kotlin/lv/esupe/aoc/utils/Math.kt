package lv.esupe.aoc.utils

import kotlin.math.abs

fun gcd(a: Int, b: Int, vararg numbers: Int): Int = when {
    numbers.isEmpty() -> if (b == 0) a else gcd(b, a % b)
    else -> gcd(gcd(a, b), numbers.first(), *numbers.drop(1).toIntArray())
}

fun lcm(a: Int, b: Int, vararg numbers: Int): Int = when {
    numbers.isEmpty() -> abs(a * b) / gcd(a, b)
    else -> lcm(lcm(a, b), numbers.first(), *numbers.drop(1).toIntArray())
}

fun List<Long>.gcd(): Long = when (size) {
    0 -> 0
    1 -> get(0)
    2 -> gcd(get(0), get(1))
    else -> gcd(get(0), get(1), *drop(2).toLongArray())
}

fun gcd(a: Long, b: Long, vararg numbers: Long): Long = when {
    numbers.isEmpty() -> if (b == 0L) a else gcd(b, a % b)
    else -> gcd(gcd(a, b), numbers.first(), *numbers.drop(1).toLongArray())
}

fun lcm(a: Long, b: Long, vararg numbers: Long): Long = when {
    numbers.isEmpty() -> abs(a * b) / gcd(a, b)
    else -> lcm(lcm(a, b), numbers.first(), *numbers.drop(1).toLongArray())
}

fun List<Long>.lcm(): Long = when (size) {
    0 -> 0
    1 -> get(0)
    2 -> lcm(get(0), get(1))
    else -> lcm(get(0), get(1), *drop(2).toLongArray())
}

infix fun Int.modulo(mod: Int): Int {
    val r = rem(mod)
    return if (r < 0) r + mod else r
}

fun Int.nearestMultipleAtOrAbove(of: Int): Int {
    return when {
        this < of -> of
        rem(of) == 0 -> this
        else -> this + of - rem(of)
    }
}

fun Long.nearestMultipleAtOrAbove(of: Long): Long {
    return when {
        this < of -> of
        rem(of) == 0L -> this
        else -> this + of - rem(of)
    }
}
