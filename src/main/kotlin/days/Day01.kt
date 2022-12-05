package days

import Puzzle
import utils.readInput
import utils.splitBy

object Day01 : Puzzle<Int> {
    private fun calculateSumOfNLargest(bags: List<List<String>>, n: Int) = bags.map { bagOfCalories ->
        bagOfCalories.sumOf { it.toInt() }
    }.sortedDescending().take(n).sum()

    override fun part1(input: List<String>): Int {
        val bags = input.splitBy { it.isEmpty() }
        return calculateSumOfNLargest(bags, 1)
    }

    override fun part2(input: List<String>): Int {
        val bags = input.splitBy { it.isEmpty() }
        return calculateSumOfNLargest(bags, 3)
    }

    override fun doSolve() {
        val testInput = """
                    1000
                    2000
                    3000
                    
                    4000
                    
                    5000
                    6000
                    
                    7000
                    8000
                    9000
                    
                    10000""".trimIndent().lines()
        check(part1(testInput) == 24000)

        val input = readInput(2022, 1)
        part1(input).let {
            println(it)
            check(it == 74198)
        }

        check(part2(testInput) == 45000)
        part2(input).let {
            println(it)
            check(it == 209914)
        }
    }
}
