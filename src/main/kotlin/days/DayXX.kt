package days

import Puzzle
import utils.readInput

object DayXX : Puzzle<Int> {
    override fun part1(input: List<String>): Int {
        return 0
    }

    override fun part2(input: List<String>): Int {
        return 0
    }

    override fun doSolve() {
        val input = readInput(2022, 8)
        val testInput = """
            
            """.trimIndent().lines()
        check(part1(testInput) == 0)

        part1(input).let {
            println(it)
            check(it == 0)
        }

        check(part2(testInput) == 0)
        part2(input).let {
            println(it)
            check(it == 0)
        }
    }
}
