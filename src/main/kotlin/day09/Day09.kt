package day09

import BasicDay

import util.FileReader
import kotlin.math.absoluteValue

val d = FileReader.asStrings("day09.txt").map { line ->
    line.split(" ").let {
        Move(Direction.valueOf(it[0]), it[1].toInt())
    }
}

fun main() = Day09.run()

object Day09 : BasicDay() {
    override fun part1() = simulate(2)

    override fun part2() = simulate(10)
}

private fun simulate(ropeLength: Int): Int {
    val visitedPositions = HashSet<Point>()
    val rope = List(ropeLength) { Point(0, 0) }
    d.forEach {
        step(it, rope, visitedPositions)
    }
    return visitedPositions.size
}

private fun step(move: Move, rope: List<Point>, visitedPosition: HashSet<Point>) {
    val head = rope.first()
    repeat(move.steps) {
        when (move.direction) {
            Direction.L -> head.x--
            Direction.R -> head.x++
            Direction.U -> head.y--
            Direction.D -> head.y++
        }
        rope.zipWithNext() { h, t ->
            moveTail(h, t)
        }
        visitedPosition.add(rope.last().copy())

    }
}

private fun moveTail(h: Point, t: Point) {
    if (h.xDist(t).absoluteValue > 1 || h.yDist(t).absoluteValue > 1) {
        if (h.xDist(t) == 0)
            t.y += h.yDist(t) / 2
        else if (h.yDist(t) == 0)
            t.x += h.xDist(t) / 2
        else {
            t.x += if (h.xDist(t) > 0) 1 else -1
            t.y += if (h.yDist(t) > 0) 1 else -1
        }
    }
}

data class Move(val direction: Direction, val steps: Int)

data class Point(var x: Int, var y: Int) {
    fun xDist(other: Point) = this.x - other.x
    fun yDist(other: Point) = this.y - other.y
}

enum class Direction {
    L, R, U, D
}
