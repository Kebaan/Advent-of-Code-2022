fun main() {
    fun calculateSumOfNLargest(bags: List<List<String>>, n: Int) = bags.map { bagOfCalories ->
        bagOfCalories.sumOf { it.toInt() }
    }.sortedDescending().take(n).sum()

    fun part1(input: List<String>): Int {
        val bags = input.splitBy { it.isEmpty() }
        return calculateSumOfNLargest(bags, 1)
    }

    fun part2(input: List<String>): Int {
        val bags = input.splitBy { it.isEmpty() }
        return calculateSumOfNLargest(bags, 3)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
