package days

import utils.Day
import utils.Direction
import utils.Point

typealias Instruction = Pair<Direction, Int>

fun main() {
    Day09.solve()
}

object Day09 : Day<Int>(2022, 9) {
    private fun parseInput(input: List<String>): List<Instruction> {
        return input.map {
            it.split(" ").let { (direction, steps) ->
                Direction.from(direction) to steps.toInt()
            }
        }
    }

    override fun part1(input: List<String>): Int {
        val instructions = parseInput(input)
        return simulateTravellingKnots(instructions, 2)
    }

    override fun part2(input: List<String>): Int {
        val instructions = parseInput(input)
        return simulateTravellingKnots(instructions, 10)
    }

    private fun simulateTravellingKnots(instructions: List<Instruction>, knotCount: Int): Int {
        val knots = Array(knotCount) { Point(0, 0) }
        val visited = mutableSetOf(knots.last())
        instructions.forEach { (direction, steps) ->
            repeat(steps) {
                knots[0] = knots[0].move(direction)

                for (n in 1 until knots.size) {
                    val childKnot = knots[n]
                    val parentKnot = knots[n - 1]
                    val diff = parentKnot - childKnot
                    if (diff.chebyshevLength() > 1) {
                        val relativeDirection = Direction.from(diff.normalized())
                        knots[n] = knots[n].move(relativeDirection)
                    }
                }

                visited.add(knots.last())
            }
        }
        return visited.size
    }

    override fun doSolve() {
        part1(input).let {
            println(it)
            check(it == 6090)
        }

        part2(input).let {
            println(it)
            check(it == 2566)
        }
    }

    override val testInput = """
        R 4
        U 4
        L 3
        D 1
        R 4
        D 1
        L 5
        R 2
        """.trimIndent().lines()
}
