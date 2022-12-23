package days

import utils.Day
import utils.splitBy

fun main() {
    Day01.solve()
}

object Day01 : Day<Int>(2022, 1) {
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
        part1(input).let {
            println(it)
            check(it == 74198)
        }

        part2(input).let {
            println(it)
            check(it == 209914)
        }
    }

    override val testInput = """
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
}
