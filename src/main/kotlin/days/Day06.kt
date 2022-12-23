package days

import utils.Day

fun main() {
    Day06.solve()
}

object Day06 : Day<Int>(2022, 6) {
    private fun locateMarker(input: String, size: Int): Int {
        return input
            .windowed(size, 1)
            .withIndex()
            .first { (_, chars) ->
                chars.toSet().size == size
            }
            .let { (index, _) -> index + size }
    }

    private fun locateStartOfPacketMarker(input: String): Int {
        return locateMarker(input, 4)
    }

    private fun locateStartOfMessageMarker(input: String): Int {
        return locateMarker(input, 14)
    }

    override fun part1(input: List<String>): Int {
        return locateStartOfPacketMarker(input.first())
    }

    override fun part2(input: List<String>): Int {
        return locateStartOfMessageMarker(input.first())
    }

    override fun doSolve() {
        part1(input).let {
            println(it)
            check(it == 1892)
        }

        part2(input).let {
            println(it)
            check(it == 2313)
        }
    }

    override val testInput = listOf("mjqjpqmgbljsphdztnvjfqwrcgsmlb")
}
