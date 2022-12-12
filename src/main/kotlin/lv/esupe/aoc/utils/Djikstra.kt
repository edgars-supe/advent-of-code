package lv.esupe.aoc.utils

object Djikstra {
    fun <T, R> findPath(start: T, target: R, getValue: (T) -> R, getNeighbors: (T) -> Collection<T>): List<T> {
        djikstra(start, target, getValue, getNeighbors) { targetT, _, _, predecessors ->
            return getPath(predecessors, targetT)
        }

        error("Something went wrong")
    }

    fun <T, R> findShortestDistance(start: T, target: R, getValue: (T) -> R, getNeighbors: (T) -> Collection<T>): Int {
        djikstra(start, target, getValue, getNeighbors) { targetT, distances, _, _ ->
            return distances.getValue(targetT)
        }

        error("Something went wrong")
    }

    private inline fun <T, R> djikstra(
        start: T,
        target: R,
        getValue: (T) -> R,
        getNeighbors: (T) -> Collection<T>,
        onFinished: (targetT: T, distances: Map<T, Int>, visits: Set<T>, predecessors: Map<T, T>) -> Unit
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

                if (getValue(adjacent) == target) {
                    onFinished(adjacent, distance, visited, predecessors)
                }
            }
        }

        error("Something went wrong")
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
}