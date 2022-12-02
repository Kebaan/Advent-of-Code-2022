sealed interface GameResult {
    object Win : GameResult
    object Lose : GameResult
    object Draw : GameResult
}
