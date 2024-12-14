package lv.esupe.aoc.model

enum class Quadrant {
    UpperRight,
    UpperLeft,
    LowerLeft,
    LowerRight;

    class Finder(
        private val minX: Int,
        private val maxX: Int,
        private val minY: Int,
        private val maxY: Int
    ) {
        constructor(width: Int, height: Int) : this(minX = 0, maxX = width - 1, minY = 0, maxY = height - 1)

        private val middleX = (maxX - minX) / 2 + minX
        private val middleY = (maxY - minY) / 2 + minY

        fun find(point: Point): Quadrant? = find(point.x, point.y)

        fun find(x: Int, y: Int): Quadrant? {
            val inLeft = x in minX until middleX
            val inRight = x in (middleX + 1) .. maxX
            val inUpper = y in minY until middleY
            val inLower = y in (middleY + 1)..maxY
            return when {
                inLeft && inUpper -> UpperLeft
                inRight && inUpper -> UpperRight
                inLeft && inLower -> LowerLeft
                inRight && inLower -> LowerRight
                else -> null
            }
        }
    }
}