package day11

import BasicDay
import util.FileReader

private fun parseInput() = FileReader.readResource("day11.txt").split("\n\n").map { monkeyString ->
    parseMonkey(monkeyString)
}

private fun parseMonkey(input: String): Monkey {
    val lines = input.split("\n").map { it.trim() }
    val index = lines[0].split(" ")[1].removeSuffix(":").toInt()
    val items = lines[1].split(":")[1].split(",").map { it.trim().toLong() }.toMutableList()
    val operation = lines[2].split(" ")
    val testDivider = lines[3].split(" ")[3].toLong()
    val nextMonkeyTrue = lines[4].takeLast(1).toInt()
    val nextMonkeyFalse = lines[5].takeLast(1).toInt()
    return Monkey(
        index = index,
        items = items,
        operationInt1 = operation[3],
        operationInt2 = operation[5],
        operationOp = getOperation(operation[4].first()),
        testOperation = { testInput -> testInput.mod(testDivider) == 0L },
        testDivider = testDivider,
        testTrueMonkeyIndex = nextMonkeyTrue,
        testFalseMonkeyIndex = nextMonkeyFalse
    )
}

fun main() = Day11.run()

object Day11 : BasicDay() {
    override fun part1() = solve(parseInput(), 20) { x -> x.floorDiv(3) }

    override fun part2(): Long {
        val monkeys = parseInput()
        val divisor = monkeys.map { it.testDivider }.reduce(Long::times)
        val reduceOperation = { x: Long -> x % divisor }
        return solve(monkeys, 10000, reduceOperation)
    }
}

private fun solve(monkeys: List<Monkey>, rounds: Int, reduceOperation: (Long) -> Long): Long {
    for (i in 1..rounds)
        monkeys.forEach { it.makeTurn(monkeys, reduceOperation) }
    return monkeys.sortedBy { it.inspectedItems }.takeLast(2).fold(1) { acc, m -> acc * m.inspectedItems }
}

data class Monkey(
    val index: Int,
    val items: MutableList<Long>,
    val operationInt1: String,
    val operationInt2: String,
    val operationOp: (Long, Long) -> Long,
    val testOperation: (Long) -> Boolean,
    val testDivider: Long,
    val testTrueMonkeyIndex: Int,
    val testFalseMonkeyIndex: Int,
    var inspectedItems: Int = 0
) {
    fun makeTurn(monkeys: List<Monkey>, reduceOperation: (Long) -> Long) {
        while(items.isNotEmpty()) {
            val newWorryLevel = reduceOperation(getNewWorryLevel(items.removeFirst()))
            if (testOperation(newWorryLevel))
                monkeys[testTrueMonkeyIndex].items.add(newWorryLevel)
            else
                monkeys[testFalseMonkeyIndex].items.add(newWorryLevel)
            inspectedItems++

        }
    }

    private fun getNewWorryLevel(oldWorryLevel: Long): Long {
        val x = if (operationInt1 == "old") oldWorryLevel else operationInt1.toLong()
        val y = if (operationInt2 == "old") oldWorryLevel else operationInt2.toLong()
        return operationOp(x, y)
    }
}

private fun getOperation(symbol: Char): (Long, Long) -> Long {
    return when (symbol) {
        '+' -> { x, y -> x + y }
        '*' -> { x, y -> x * y }
        else -> throw RuntimeException("unknown operation")
    }
}