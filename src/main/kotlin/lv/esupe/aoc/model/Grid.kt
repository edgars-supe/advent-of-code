package lv.esupe.aoc.model

class Grid<T : Any> private constructor(
    private val default: T?,
    private val invertY: Boolean,
    private val insertDefault: Boolean,
    private val delegate: MutableMap<Point, T> = mutableMapOf()
) : MutableMap<Point, T> by delegate {

    val height
        get() = maxY - minY + 1
    val width
        get() = maxX - minX + 1

    var minY: Int = 0
        private set
    var maxY: Int = 0
        private set
    var minX: Int = 0
        private set
    var maxX: Int = 0
        private set

    init {
        if (delegate.isNotEmpty()) {
            delegate.keys.minOfOrNull { it.x }?.let { minX = it }
            delegate.keys.maxOfOrNull { it.x }?.let { maxX = it }
            delegate.keys.minOfOrNull { it.y }?.let { minY = it }
            delegate.keys.maxOfOrNull { it.y }?.let { maxY = it }
        }
    }

    override fun put(key: Point, value: T): T? {
        if (!insertDefault && value == default) return null
        adjust(key)
        return delegate.put(key, value)
    }

    override fun putAll(from: Map<out Point, T>) {
        val map =
            if (insertDefault) from
            else from.filterValues { it != default }
        map.keys.forEach { point -> adjust(point) }
        delegate.putAll(map)
    }

    operator fun get(x: Int, y: Int): T? {
        return get(Point(x, y))
    }

    operator fun set(x: Int, y: Int, value: T) {
        set(Point(x, y), value)
    }

    fun putAll(keys: Collection<Point>, value: T) {
        keys.forEach { key -> put(key, value) }
    }

    fun isInBounds(point: Point): Boolean {
        return isInBounds(point.x, point.y)
    }

    fun isInBounds(x: Int, y: Int): Boolean {
        return x in minX..maxX && y in minY..maxY
    }

    fun getNeighbors(point: Point, ignoreDefault: Boolean, diagonal: Boolean): Set<Pair<Point, T>> {
        if (point !in this) return emptySet()
        return point.neighbors(diagonal)
            .filter { it in this }
            .fold(mutableSetOf()) { set, p ->
                val value = get(p)
                if (value != null && !(value == default && ignoreDefault)) {
                    set += p to value
                }
                set
            }
    }

    private fun adjust(point: Point) {
        if (minX > point.x) minX = point.x
        if (maxX < point.x) maxX = point.x
        if (minY > point.y) minY = point.y
        if (maxY < point.y) maxY = point.y
    }

    fun toString(invertY: Boolean = this.invertY): String {
        return toString(invertY) { _, value -> (value ?: default).toString() }
    }

    fun toString(invertY: Boolean = this.invertY, transform: (x: Int, y: Int, T?) -> CharSequence): String {
        return toString(invertY) { point, value -> transform(point.x, point.y, value) }
    }

    fun toString(invertY: Boolean = this.invertY, transform: (Point, T?) -> CharSequence): String {
        val yRange = if (invertY) maxY downTo minY else minY..maxY
        return (yRange)
            .joinToString(separator = "\n") { y ->
                (minX..maxX).joinToString(separator = "") { x ->
                    val point = Point(x, y)
                    transform(point, get(point) ?: default)
                }
            }
    }

    override fun toString(): String {
        return toString(invertY)
    }

    companion object {
        fun from(
            lines: List<String>,
            default: Char = '.',
            insertDefault: Boolean = false,
            invertY: Boolean = false
        ): Grid<Char> {
            return from(lines, default, insertDefault, invertY) { it }
        }

        fun <T : Any> from(
            lines: List<String>,
            default: T?,
            insertDefault: Boolean = false,
            invertY: Boolean = false,
            transform: (Char) -> T
        ): Grid<T> {
            val map = mutableMapOf<Point, T>()
            lines
                .let { if (invertY) it.asReversed() else it }
                .forEachIndexed { y, line ->
                    line.forEachIndexed { x, char ->
                        val value = transform(char)
                        if (value != default || insertDefault) {
                            map[Point(x, y)] = transform(char)
                        }
                    }
                }
            return Grid(default, invertY, insertDefault, map)
        }
    }
}
