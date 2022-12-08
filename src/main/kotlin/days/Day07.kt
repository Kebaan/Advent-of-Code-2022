package days

import Puzzle
import utils.readInput

object Day07 : Puzzle<Int> {
    private val PATTERN = """[$] cd (.*)|(\d+).*""".toRegex()

    override fun part1(input: List<String>): Int {
        val sizes = parseInput(input)
        return sizes.values.sumOf { if (it <= 100000) it else 0 }
    }

    private fun parseInput(input: List<String>)= buildMap {
        put("", 0)
        var cwd = ""
        for (line in input) {
            val match = PATTERN.matchEntire(line) ?: continue
            match.groups[1]?.value?.let { dir ->
                cwd = when (dir) {
                    "/" -> ""
                    ".." -> cwd.substringBeforeLast('/', "")
                    else -> if (cwd.isEmpty()) dir else "$cwd/$dir"
                }
            } ?: match.groups[2]?.value?.toIntOrNull()?.let { size ->
                var dir = cwd
                while (true) {
                    put(dir, getOrElse(dir) { 0 } + size)
                    if (dir.isEmpty()) break
                    dir = dir.substringBeforeLast('/', "")
                }
            }
        }
    }

    override fun part2(input: List<String>): Int {
        val sizes = parseInput(input)
        val total = sizes.getValue("")
        return sizes.values.asSequence().filter { 70000000 - (total - it) >= 30000000 }.min()
    }

    override fun doSolve() {
        val testInput = """
            $ cd /
            $ ls
            dir a
            14848514 b.txt
            8504156 c.dat
            dir d
            $ cd a
            $ ls
            dir e
            29116 f
            2557 g
            62596 h.lst
            $ cd e
            $ ls
            584 i
            $ cd ..
            $ cd ..
            $ cd d
            $ ls
            4060174 j
            8033020 d.log
            5626152 d.ext
            7214296 k""".trimIndent().lines()
        check(part1(testInput) == 95437)

        val input = readInput(2022, 7)

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
