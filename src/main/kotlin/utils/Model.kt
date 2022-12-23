package utils

sealed interface GameResult {
    object Win : GameResult
    object Lose : GameResult
    object Draw : GameResult
}

data class Point(val x: Int, val y: Int)
