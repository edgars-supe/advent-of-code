package lv.esupe.aoc.model

data class Point3(
    val x: Int,
    val y: Int,
    val z: Int
) {
    operator fun plus(point: Point3) = Point3(x + point.x, y + point.y, z + point.z)

    operator fun minus(point: Point3) = Point3(x - point.x, y - point.y, z - point.z)

    operator fun times(by: Int) = Point3(x * by, y * by, z * by)
}