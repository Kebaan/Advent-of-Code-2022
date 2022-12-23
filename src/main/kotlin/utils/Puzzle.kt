package utils

interface Puzzle<T> {
    val year: Int
    val day: Int
    val input: List<String>

    fun part1(input: List<String>): T
    fun part2(input: List<String>): T
    fun solve() {
        println("solving ${this.javaClass.simpleName}")
        doSolve()
    }

    fun doSolve()

    val testInput: List<String>
}
