package day03

import BasicDay
import util.FileReader

val d = FileReader.asStrings("day03.txt")

fun main() = Day03.run()

object Day03 : BasicDay() {
    override fun part1() = d.sumOf { it.chunked(it.length / 2).getPriority() }

    override fun part2() = d.chunked(3) { it.getPriority() }.sum()
}

private fun List<String>.getPriority() =
    this.map { i -> i.toSet() }.reduce { acc, s ->
        acc.intersect(s)
    }.first().toPriority()

private fun Char.toPriority() = if (this.isLowerCase()) this.code - 96 else this.code - 38
