package days

import Puzzle
import utils.readInput
import utils.splitBy
import java.util.TreeMap

typealias Crate = Char
object Day05 : Puzzle<String> {
    data class Move(val amount: Int, val from: Int, val to: Int)
    private val MOVE_REGEX = """move (\d+) from (\d+) to (\d+)""".toRegex()

    private fun parseInput(input: List<String>): Pair<TreeMap<Int, ArrayDeque<Crate>>, List<Move>> {
        val (layerDescription, moveDescriptions) = input.splitBy { it.isEmpty() }

        val stacks = TreeMap<Int, ArrayDeque<Crate>>()
        layerDescription.forEach { layer ->
            layer.forEachIndexed { index, spot ->
                if (spot.isLetter()) {
                    val stackId = (index / 4 + 1)
                    stacks.getOrPut(stackId) { ArrayDeque() }.addFirst(spot)
                }
            }
        }
        val moves = moveDescriptions.map {
            val (amount, from, to) = MOVE_REGEX.find(it)!!.destructured
            Move(amount.toInt(), from.toInt(), to.toInt())
        }
        return stacks to moves
    }

    override fun part1(input: List<String>): String {
        val (crateStacks, moves) = parseInput(input)

        moves.forEach { move ->
            repeat(move.amount) {
                val crateToMove = crateStacks[move.from]!!.removeLast()
                crateStacks[move.to]!!.addLast(crateToMove)
            }
        }

        return crateStacks.extractTopCrates()
    }

    override fun part2(input: List<String>): String {
        val (crateStacks, moves) = parseInput(input)

        moves.forEach { move ->
            val buffer = ArrayDeque<Crate>()
            repeat(move.amount) {
                val crateToMove = crateStacks[move.from]!!.removeLast()
                buffer.addFirst(crateToMove)
            }
            crateStacks[move.to]!!.addAll(buffer)
        }

        return crateStacks.extractTopCrates()
    }


    private fun TreeMap<Int, ArrayDeque<Crate>>.extractTopCrates(): String {
        return this.map { (_, stack) ->
            stack.last()
        }.joinToString("")
    }

    override fun doSolve() {
        val testInput = """
                                [D]    
                            [N] [C]    
                            [Z] [M] [P]
                             1   2   3 
                            
                            move 1 from 2 to 1
                            move 3 from 1 to 3
                            move 2 from 2 to 1
                            move 1 from 1 to 2""".trimIndent().lines()
        check(part1(testInput) == "CMZ")

        val input = readInput(2022, 5)

        part1(input).let {
            println(it)
            check(it == "TBVFVDZPN")
        }

        check(part2(testInput) == "MCD")
        part2(input).let {
            println(it)
            check(it == "VLCWHTDSZ")
        }
    }
}
