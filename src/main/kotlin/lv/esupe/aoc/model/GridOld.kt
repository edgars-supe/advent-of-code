package lv.esupe.aoc.model

import kotlin.math.absoluteValue

@Deprecated("Use Grid")
class GridOld<T : Any>(val default: T) {
    private var offsetX = Int.MAX_VALUE
    private var offsetY = Int.MAX_VALUE

    private val grid = ArrayList<ArrayList<T?>>()

    val height
        get() = grid.size
    val width
        get() = grid.maxOfOrNull { it.size } ?: 0

    val minY
        get() = offsetY
    val maxY
        get() = minY + height
    val minX
        get() = offsetX
    val maxX
        get() = minX + width

    fun insert(points: Collection<Point>, value: T) {
        //val insertMinX = points.minOf { it.x }
        //val insertMinY = points.minOf { it.y }
        //val insertMaxX = points.maxOf { it.x }
        //val insertMaxY = points.maxOf { it.y }
        //val newWidth = if (offsetX != Int.MAX_VALUE) {
        //    maxOf(maxX, insertMaxX) - minOf(minX, insertMinX)
        //} else {
        //    insertMaxX - insertMinX
        //}
        //val newHeight = if (offsetY != Int.MAX_VALUE) {
        //    maxOf(maxY, insertMaxY) - minOf(minY, insertMinY)
        //} else {
        //    insertMaxY - insertMinY
        //}
        //if (newHeight > height) {
        //    offsetY = minOf(minY, insertMinY)
        //    grid.ensureCapacity(newHeight)
        //}
        //if (newWidth > width) {
        //    offsetX = minOf(minX, insertMinX)
        //    grid.forEach { row -> row.ensureCapacity(newWidth) }
        //}
        points.forEach { set(it, value) }
    }

    operator fun get(x: Int, y: Int): T {
        val adjX = x - offsetX
        val adjY = y - offsetY

        val row = grid.getOrNull(adjY) ?: return default
        return row.getOrNull(adjX) ?: default
    }

    operator fun get(point: Point): T {
        return get(point.x, point.y)
    }

    operator fun set(x: Int, y: Int, value: T) {
        if (offsetX == Int.MAX_VALUE) { offsetX = x }
        if (offsetY == Int.MAX_VALUE) { offsetY = y }
        var adjX = x - offsetX
        var adjY = y - offsetY

        if (adjY < 0) {
            val add = adjY.absoluteValue
            grid.ensureCapacity(grid.size + add)
            repeat(add) {
                grid.add(0, ArrayList(width))
            }
            offsetY += adjY // actually subtracts
            adjY = 0
        } else if (adjY >= grid.size) {
            val add = (adjY - grid.size) + 1
            grid.ensureCapacity(grid.size + add)
            repeat(add) {
                grid.add(ArrayList(width))
            }
        }

        val row = grid[adjY]
        if (adjX < 0) {
            val add = adjX.absoluteValue
            row.ensureCapacity(row.size + add)
            grid.forEach { row1 ->
                row1.ensureCapacity(row1.size + add)
                repeat(add) {
                    row1.add(0, null)
                }
            }
            offsetX += adjX // actually subtracts
            adjX = 0
        } else if (adjX >= row.size) {
            val add = (adjX - row.size) + 1
            row.ensureCapacity(row.size + add)
            repeat(add) {
                row.add(null)
            }
        }
        row[adjX] = value
    }

    operator fun set(point: Point, value: T) {
        set(point.x, point.y, value)
    }

    fun contains(x: Int, y: Int): Boolean {
        return _contains(x, y)
    }

    operator fun contains(point: Point): Boolean {
        return _contains(point.x, point.y)
    }

    private fun _contains(x: Int, y: Int): Boolean {
        val adjX = x - offsetX
        val adjY = y - offsetY

        val row = grid.getOrNull(adjY) ?: return false
        return row.getOrNull(adjX)?.let { true } ?: false
    }

    fun count(predicate: (T) -> Boolean): Int = grid.sumOf { row -> row.count { it?.let { predicate(it) } ?: false } }

    fun locationOf(item: T): Point? {
        grid.forEachIndexed { y, row ->
            row.forEachIndexed { x, c -> if (c == item) return Point(x + offsetX, y + offsetY) }
        }
        return null
    }

    fun toString(invertY: Boolean = false): String {
        return (minY..maxY)
            .joinToString(separator = "\n") { y ->
                (minX..maxX).joinToString(separator = "") { x ->
                    get(x, y).toString()
                }
            }
    }

    override fun toString(): String {
        return (maxY downTo minY)
            .joinToString(separator = "\n") { y ->
                (minX..maxX).joinToString(separator = "") { x ->
                    get(x, y).toString()
                }
            }
    }

    companion object {
        fun from(lines: List<String>, default: Char = '.'): GridOld<Char> {
            val grid = GridOld(default)
            lines.forEachIndexed { y, line ->
                line.forEachIndexed { x, char -> grid[x, y] = char }
            }
            return grid
        }

        fun <T : Any> from(lines: List<String>, default: T, transform: (Char) -> T): GridOld<T> {
            val grid = GridOld(default)
            lines.forEachIndexed { y, line ->
                line.forEachIndexed { x, char -> grid[x, y] = transform(char) }
            }
            return grid
        }
    }
}
