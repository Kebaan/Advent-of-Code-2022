package utils

import kotlin.math.abs
import kotlin.math.max

sealed interface GameResult {
    object Win : GameResult
    object Lose : GameResult
    object Draw : GameResult
}

data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    operator fun minus(other: Point) = Point(x - other.x, y - other.y)
    fun move(direction: Direction) = this + direction.delta
    fun normalized() = Point(if (x != 0) x / abs(x) else 0, if (y != 0) y / abs(y) else 0)
    fun manhattanDistance(other: Point): Int {
        val deltaX = abs(x - other.x)
        val deltaY = abs(y - other.y)
        return deltaX + deltaY
    }

    fun manhattanLength(): Int = abs(x) + abs(y)
    fun chebyshevDistance(other: Point): Int {
        val deltaX = abs(x - other.x)
        val deltaY = abs(y - other.y)
        return max(deltaX, deltaY)
    }

    fun chebyshevLength(): Int = max(abs(x), abs(y))
}

enum class Direction(val delta: Point) {
    NORTH(Point(0, 1)),
    NORTH_EAST(Point(1, 1)),
    EAST(Point(1, 0)),
    SOUTH(Point(0, -1)),
    SOUTH_EAST(Point(1, -1)),
    SOUTH_WEST(Point(-1, -1)),
    WEST(Point(-1, 0)),
    NORTH_WEST(Point(-1, 1));

    companion object {
        fun from(delta: Point): Direction =
            values().find { it.delta == delta } ?: error("unknown delta $delta")

        fun from(input: String): Direction = when (input) {
            "U", "N" -> NORTH
            "NE" -> NORTH_EAST
            "R", "E" -> EAST
            "SE" -> SOUTH_EAST
            "D", "S" -> SOUTH
            "SW" -> SOUTH_WEST
            "L", "W" -> WEST
            "NW" -> NORTH_WEST
            else -> throw Exception("unknown direction $input")
        }
    }
}
