package lv.esupe.aoc.utils

object Paths {

    fun <T : Any, R : Any> findPath(
        start: T,
        target: R,
        getValue: (T) -> R,
        getNeighbors: (T) -> Collection<T>
    ) : TargetResult<T>? {
        val result = dijkstra(start, target, getValue, getNeighbors)
        return result.targetT?.let { targetT ->
            TargetResult(path = getPath(result.predecessors, targetT))
        }
    }

    fun <T : Any> findAllPaths(start: T, target: T, getNeighbors: (T) -> Collection<T>): List<List<T>> {
        val result = mutableListOf<List<T>>()
        val visited = mutableSetOf<T>()

        fun dfs(current: T, path: MutableList<T>) {
            path.add(current)
            visited.add(current)

            if (current == target) {
                result.add(path.toList())
            } else {
                getNeighbors(current).forEach { neighbor ->
                    if (neighbor !in visited) {
                        dfs(neighbor, path)
                    }
                }
            }

            path.removeAt(path.size - 1)
            visited.remove(current)
        }

        dfs(start, mutableListOf())
        return result
    }

    fun <T : Any> findShortestPaths(start: T, getNeighbors: (T) -> Collection<T>): Result<T> {
        return dijkstra(start, null, null, getNeighbors)
    }

    private inline fun <T, R> dijkstra(
        start: T,
        target: R?,
        noinline getValue: ((T) -> R)?,
        getNeighbors: (T) -> Collection<T>
    ) : Result<T> {
        val visited = hashSetOf(start)
        val distances: MutableMap<T, Int> = mutableMapOf(start to 0).withDefault { Int.MAX_VALUE }
        val predecessors = mutableMapOf<T, T>()
        val queue = ArrayDeque<T>()
        queue.add(start)
        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            val adjacents = getNeighbors(current)
                .filter { it !in visited }

            for (adjacent in adjacents) {
                visited.add(adjacent)
                distances[adjacent] = distances.getValue(current) + 1
                predecessors[adjacent] = current
                queue.add(adjacent)

                if (target != null && getValue != null) {
                    if (getValue.invoke(adjacent) == target) {
                        return Result(adjacent, distances, predecessors)
                    }
                }
            }
        }

        return Result(targetT = null, distances, predecessors)
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
        val targetT: T?,
        val distances: Map<T, Int>,
        val predecessors: Map<T, T>
    )
}
