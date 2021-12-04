package lv.esupe.aoc.utils

fun Any.bold(color: String = ""): String = style("$color;1")

fun Any.style(color: String): String {
    return "\u001B[${color}m$this\u001B[0m"
}
