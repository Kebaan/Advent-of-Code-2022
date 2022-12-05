interface Puzzle<T> {
    fun part1(input: List<String>): T
    fun part2(input: List<String>): T
    fun solve() {
        println("solving ${this.javaClass.simpleName}")
        doSolve()
    }

    fun doSolve()
}
