package day01

import BasicDay
import util.FileReader

val d = FileReader.readResource("day01.txt")
    .split("\n\n")
    .map { it.split("\n").sumOf { i -> i.toInt() } }

fun main() = Day01.run()


object Day01 : BasicDay() {
    override fun part1() = d.maxOf { it }

    override fun part2() = d.map { it }.sorted().takeLast(3).sum()
}