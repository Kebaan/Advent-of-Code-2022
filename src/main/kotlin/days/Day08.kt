package days

import Puzzle
import utils.readInput

object Day08 : Puzzle<Int> {
    override fun part1(input: List<String>): Int {
        return 0
    }

    override fun part2(input: List<String>): Int {
        return 0
    }

    override fun doSolve() {
        val input = readInput(2022, 8)
        val testInput = """
            30373
            25512
            65332
            33549
            35390""".trimIndent().lines()
        check(part1(testInput) == 21)


        part1(input).let {
            println(it)
            check(it == 1644735)
        }

        check(part2(testInput) == 24933642)
        part2(input).let {
            println(it)
            check(it == 1300850)
        }
    }
}
