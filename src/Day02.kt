import GameResult.Draw
import GameResult.Lose
import GameResult.Win
import HandShape.Paper
import HandShape.Rock
import HandShape.Scissor

fun main() {
    fun calculateScoreForRound(round: RockPaperScissorsGame): Int {
        val outcomeScore = when (round.play()) {
            Lose -> 0
            Draw -> 3
            Win -> 6
        }
        return outcomeScore + (round.myChoice.value)
    }

    fun parseHandShape(input: String) = when (input) {
        "A", "X" -> Rock
        "B", "Y" -> Paper
        "C", "Z" -> Scissor
        else -> error("invalid input")
    }

    fun parseStrategy(input: String) = when (input) {
        "X" -> Lose
        "Y" -> Draw
        "Z" -> Win
        else -> error("invalid input")
    }

    fun part1(input: List<String>): Int {
        return input.toRockPaperScissorRounds {
            val opponentChoice = parseHandShape(it.first)
            val myChoice = parseHandShape(it.second)
            RockPaperScissorsGame(opponentChoice, myChoice)
        }.sumOf { calculateScoreForRound(it) }
    }

    fun part2(input: List<String>): Int {
        return input.toRockPaperScissorRounds {
            val opponentChoice = parseHandShape(it.first)
            val gameStrategy = parseStrategy(it.second)
            val myChoice = opponentChoice.findHand(gameStrategy)
            RockPaperScissorsGame(opponentChoice, myChoice)
        }.sumOf { calculateScoreForRound(it) }
    }

    val inputLines = readInput("Day02_test")
    check(part1(inputLines) == 15)
    check(part2(inputLines) == 12)

    val input = readInput("Day02")
    part1(input).let {
        check(it == 11666)
        println(it)
    }
    part2(input).let {
        check(it == 12767)
        println(it)
    }
}

private fun List<String>.toRockPaperScissorRounds(transform: (Pair<String, String>) -> RockPaperScissorsGame): List<RockPaperScissorsGame> {
    return this.map { transform(it.toPair(" ")) }
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
