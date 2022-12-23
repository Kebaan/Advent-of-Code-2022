package days

import utils.Day

fun main() {
    DayXX.solve()
}

object DayXX : Day<Int>(2022, 0) {
    override fun part1(input: List<String>): Int {
        return 0
    }

    override fun part2(input: List<String>): Int {
        return 0
    }

    override fun doSolve() {
        part1(input).let {
            println(it)
            check(it == 0)
        }

        part2(input).let {
            println(it)
            check(it == 0)
        }
    }

    override val testInput = """
        
        """.trimIndent().lines()
}
