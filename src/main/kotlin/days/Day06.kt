package days

import Puzzle
import utils.readInput

object Day06 : Puzzle<Int> {
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
        return locateMarker(input ,4)
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
        val testInput = listOf("mjqjpqmgbljsphdztnvjfqwrcgsmlb")
        check(part1(testInput) == 7)

        val input = readInput(2022, 6)

        part1(input).let {
            println(it)
            check(it == 1892)
        }

        check(part2(testInput) == 19)
        part2(input).let {
            println(it)
            check(it == 2313)
        }
    }
}
