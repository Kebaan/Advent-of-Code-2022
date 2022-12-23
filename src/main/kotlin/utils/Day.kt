package utils

abstract class Day<T>(
    final override val year: Int,
    final override val day: Int,
) : Puzzle<T> {
    override val input = readInput(year, day)
}
