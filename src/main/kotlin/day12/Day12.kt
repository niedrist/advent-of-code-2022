package day12

import BasicDay
import util.FileReader

val d = FileReader.asStrings("day12.txt").flatMapIndexed { y, row ->
    row.mapIndexed { x, c ->
        Point(x, y) to Position(
            parseHeight(c), c == 'S', c == 'E'
        )
    }
}.toMap()

private fun parseHeight(c: Char) = when (c) {
    'S' -> 'a'
    'E' -> 'z'
    else -> c
}

fun main() = Day12.run()

object Day12 : BasicDay() {
    override fun part1() = find(
        d.entries.first { it.value.isStart }.key,
        d.entries.first { it.value.isEnd }.key
    )

    override fun part2() = d.entries.filter { it.value.elevation == 'a' }.minOf { newStart ->
        find(
            newStart.key,
            d.entries.first { it.value.isEnd }.key
        )
    }
}

private fun find(startPoint: Point, endPoint: Point): Int {
    val distances = d.keys.associateWith { Int.MAX_VALUE }.toMutableMap()
    distances[startPoint] = 0

    val toVisit = mutableListOf(startPoint)

    while (toVisit.isNotEmpty()) {
        val current = toVisit.removeFirst()
        current.neighbours()
            .forEach { neighbour ->
                val newDistance = distances[current]!! + 1

                if (d[neighbour] == d[endPoint]) return newDistance

                if (newDistance < distances[neighbour]!!) {
                    distances[neighbour] = newDistance
                    toVisit.add(neighbour)
                }
            }
    }
    return Int.MAX_VALUE
}

data class Position(
    val elevation: Char,
    val isStart: Boolean,
    val isEnd: Boolean,
)

data class Point(val x: Int, val y: Int) {
    fun neighbours() = listOf((-1 to 0), (1 to 0), (0 to -1), (0 to 1))
        .map { (dx, dy) -> Point(x + dx, y + dy) }
        .filter { neighbour -> neighbour in d && d[this]!!.elevation - d[neighbour]!!.elevation >= -1 }
}