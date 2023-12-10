package lv.esupe.aoc.utils

object Dijkstra {
    fun <T : Any, R : Any> findPath(start: T, target: R, getValue: (T) -> R, getNeighbors: (T) -> Collection<T>): TargetResult<T> {
        djikstra(start, target, getValue, getNeighbors) { targetT, _, predecessors ->
            return TargetResult(getPath(predecessors, targetT!!))
        }

        error("Something went wrong")
    }

    fun <T : Any> findShortestPaths(start: T, getNeighbors: (T) -> Collection<T>): Result<T> {
        djikstra(start, null, null, getNeighbors) { _, distances, predecessors ->
            return Result(distances, predecessors)
        }

        error("Something went wrong")
    }

    private inline fun <T, R> djikstra(
        start: T,
        target: R?,
        noinline getValue: ((T) -> R)?,
        getNeighbors: (T) -> Collection<T>,
        onFinished: (targetT: T?, distances: Map<T, Int>, predecessors: Map<T, T>) -> Unit
    ) {
        val visited = hashSetOf(start)
        val distance = mutableMapOf(start to 0).withDefault { Int.MAX_VALUE }
        val predecessors = mutableMapOf<T, T>()
        val queue = ArrayDeque<T>()
        queue.add(start)
        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            val adjacents = getNeighbors(current)
                .filter { it !in visited }

            for (adjacent in adjacents) {
                visited.add(adjacent)
                distance[adjacent] = distance.getValue(current) + 1
                predecessors[adjacent] = current
                queue.add(adjacent)

                if (target != null && getValue != null) {
                    if (getValue.invoke(adjacent) == target) {
                        onFinished(adjacent, distance, predecessors)
                    }
                }
            }
        }

        onFinished(null, distance, predecessors)
    }

    private fun <T> getPath(predecessors: Map<T, T>, destination: T): List<T> {
        val path = mutableListOf<T>()
        var crawl = destination
        path.add(destination)
        while (predecessors[crawl] != null) {
            path.add(0, predecessors.getValue(crawl))
            crawl = predecessors.getValue(crawl)
        }
        return path
    }

    data class TargetResult<T>(val path: List<T>) {
        val steps = path.size - 1
    }

    data class Result<T>(
        val distances: Map<T, Int>,
        val predecessors: Map<T, T>
    )
}
