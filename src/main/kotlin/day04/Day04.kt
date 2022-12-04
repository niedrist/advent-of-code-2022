package day04

import BasicDay
import util.FileReader
import kotlin.math.max

val d = FileReader.asStrings("day04.txt").map { line ->
    line.split(',').map { a ->
        a.split('-').let { it[0].toInt()..it[1].toInt() }
    }.let { Pair(it[0], it[1]) }
}

fun main() = Day04.run()

object Day04 : BasicDay() {
    override fun part1() = d.count { (first, second) ->
        first.union(second).size == max(first.toSet().size, second.toSet().size)
    }

    override fun part2() = d.count { (first, second) ->
        first.intersect(second).isNotEmpty()
    }
}