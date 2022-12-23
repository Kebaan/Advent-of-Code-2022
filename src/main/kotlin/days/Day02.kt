package days

import days.Day02.HandShape.*
import utils.Day
import utils.GameResult
import utils.GameResult.*
import utils.toPair

fun main() {
    Day02.solve()
}

object Day02 : Day<Int>(2022, 2) {
    private fun calculateScoreForRound(round: RockPaperScissorsGame): Int {
        val outcomeScore = when (round.play()) {
            Lose -> 0
            Draw -> 3
            Win -> 6
        }
        return outcomeScore + (round.myChoice.value)
    }

    private fun parseHandShape(input: String) = when (input) {
        "A", "X" -> Rock
        "B", "Y" -> Paper
        "C", "Z" -> Scissor
        else -> error("invalid input")
    }

    private fun parseStrategy(input: String) = when (input) {
        "X" -> Lose
        "Y" -> Draw
        "Z" -> Win
        else -> error("invalid input")
    }

    private fun List<String>.toRockPaperScissorRounds(transform: (Pair<String, String>) -> RockPaperScissorsGame): List<RockPaperScissorsGame> {
        return this.map { transform(it.toPair(" ")) }
    }

    override fun part1(input: List<String>): Int {
        return input.toRockPaperScissorRounds {
            val opponentChoice = parseHandShape(it.first)
            val myChoice = parseHandShape(it.second)
            RockPaperScissorsGame(opponentChoice, myChoice)
        }.sumOf { calculateScoreForRound(it) }
    }

    override fun part2(input: List<String>): Int {
        return input.toRockPaperScissorRounds {
            val opponentChoice = parseHandShape(it.first)
            val gameStrategy = parseStrategy(it.second)
            val myChoice = opponentChoice.findHand(gameStrategy)
            RockPaperScissorsGame(opponentChoice, myChoice)
        }.sumOf { calculateScoreForRound(it) }
    }

    override fun doSolve() {
        part1(input).let {
            check(it == 11666)
            println(it)
        }

        part2(input).let {
            check(it == 12767)
            println(it)
        }
    }

    data class RockPaperScissorsGame(val opponentChoice: HandShape, val myChoice: HandShape) {
        fun play(): GameResult {
            return myChoice.playAgainst(opponentChoice)
        }
    }

    sealed interface HandShape {
        val value: Int
        fun winningAgainst(): HandShape
        fun losingAgainst(): HandShape

        fun playAgainst(opponentChoice: HandShape): GameResult {
            return when (opponentChoice) {
                winningAgainst() -> Win
                losingAgainst() -> Lose
                else -> Draw
            }
        }

        fun findHand(gameStrategy: GameResult) = when (gameStrategy) {
            Draw -> this
            Lose -> this.winningAgainst()
            Win -> this.losingAgainst()
        }

        object Rock : HandShape {
            override val value = 1
            override fun winningAgainst() = Scissor
            override fun losingAgainst() = Paper
        }

        object Paper : HandShape {
            override val value = 2
            override fun winningAgainst() = Rock
            override fun losingAgainst() = Scissor
        }

        object Scissor : HandShape {
            override val value = 3
            override fun winningAgainst() = Paper
            override fun losingAgainst() = Rock
        }
    }

    override val testInput = """
            A Y
            B X
            C Z""".trimIndent().lines()
}
