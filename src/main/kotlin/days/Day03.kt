package days

import utils.Day
import utils.readInput

fun main() {
    Day03.solve()
}

object Day03 : Day<Int>(2022, 3) {
    private fun Char.priority() = when (this) {
        in 'a'..'z' -> this - 'a' + 1
        in 'A'..'Z' -> this - 'A' + 27
        else -> error("invalid input")
    }

    override fun part1(input: List<String>): Int {
        return input.sumOf { rucksack ->
            val (firstCompartment, secondCompartment) = rucksack.chunked(rucksack.length / 2) { it.toSet() }
            val item = firstCompartment.intersect(secondCompartment).first()
            item.priority()
        }
    }

    override fun part2(input: List<String>): Int {
        return input.chunked(3)
            .sumOf { rucksacks ->
                val badge = rucksacks
                    .fold(rucksacks.first().toSet()) { acc, rucksack ->
                        acc.intersect(rucksack.toSet())
                    }.first()
                badge.priority()
            }
    }

    override fun doSolve() {
        part1(input).let {
            println(it)
            check(it == 7821)
        }

        part2(input).let {
            println(it)
            check(it == 2752)
        }
    }

    override val testInput = """
        vJrwpWtwJgWrhcsFMMfFFhFp
        jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
        PmmdzqPrVvPwwTWBwg
        wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
        ttgJtRGJQctTZtZT
        CrZsJsPPZsGzwwsLwLmpwMDw""".trimIndent().lines()
}
