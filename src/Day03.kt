fun main() {

    fun part1(input: List<String>): Int {
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val inputLines = readInput("Day03_test")
    check(part1(inputLines) == 15)
    check(part2(inputLines) == 12)

    val input = readInput("Day03")
    part1(input).let {
        check(it == 11666)
        println(it)
    }
    part2(input).let {
        check(it == 12767)
        println(it)
    }
}
