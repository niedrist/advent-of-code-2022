package day05

import BasicDay
import util.FileReader

val d = FileReader.readResource("day05.txt").split("\n\n")
val regexPattern = "move (\\d+) from (\\d+) to (\\d+)".toRegex()
val commands = d[1].split("\n").map {
    regexPattern.find(it).let { match ->
        val (a, s, d) = match!!.destructured
        Command(a.toInt(), s.toInt() - 1, d.toInt() - 1)
    }
}

fun main() = Day05.run()

object Day05 : BasicDay() {
    override fun part1() = readStacks().apply {
        commands.forEach { command ->
            repeat(command.amount) {
                this[command.dest].addFirst(this[command.source].removeFirst())
            }
        }
    }.cratesOnTop()

    override fun part2() = readStacks().apply {
        commands.forEach { command ->
            val h = ArrayDeque<Char>()
            repeat(command.amount) {
                h.addFirst(this[command.source].removeFirst())
            }
            h.forEach { c ->
                this[command.dest].addFirst(c)
            }
        }
    }.cratesOnTop()
}

data class Command(val amount: Int, val source: Int, val dest: Int)

private fun List<ArrayDeque<Char>>.cratesOnTop() = this.joinToString(separator = "") { it.first().toString() }

private fun readStacks() = d[0].let { s ->
    List(s.split("\n")[0].length / 3 - 1) { ArrayDeque<Char>() }.also {
        s.split("\n").forEach { line ->
            if (!line.contains("\\d".toRegex()))
                line.forEachIndexed { index, char ->
                    if (char != ' ') {
                        if (index == 1) {
                            it[0].addLast(char)
                        }
                        if (index > 1 && index.mod(4) == 1)
                            it[(index - 1) / 4].addLast(char)
                    }
                }
        }
    }
}