package days

import utils.Day
import utils.asIntRange

fun main() {
    Day04.solve()
}

object Day04 : Day<Int>(2022, 4) {
    private fun parseGroups(input: List<String>): List<Pair<IntRange, IntRange>> {
        return input.map { group ->
            group.substringBefore(",").asIntRange() to group.substringAfter(",").asIntRange()
        }
    }

    operator fun IntRange.contains(other: IntRange): Boolean {
        return other.first >= first && other.last <= last
    }

    override fun part1(input: List<String>): Int {
        val groupRanges = parseGroups(input)
        return groupRanges.count { (first, second) ->
            (first in second) || (second in first)
        }
    }

    override fun part2(input: List<String>): Int {
        val groupRanges = parseGroups(input)
        return groupRanges.count { (first, second) -> (first intersect second).isNotEmpty() }
    }

    override fun doSolve() {
        part1(input).let {
            println(it)
            check(it == 305)
        }

        part2(input).let {
            println(it)
            check(it == 811)
        }
    }

    override val testInput = """
            2-4,6-8
            2-3,4-5
            5-7,7-9
            2-8,3-7
            6-6,4-6
            2-6,4-8""".trimIndent().lines()
}
