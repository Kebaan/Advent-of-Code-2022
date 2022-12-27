import days.*
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.WithDataTestName
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import utils.Puzzle

private data class PuzzleAnswer<T>(
    val day: Puzzle<T>,
    val part1Expected: T,
    val part2Expected: T,
) : WithDataTestName {
    override fun dataTestName() = "Testing ${day.javaClass.simpleName}"
}

class DailyPuzzles : FunSpec({
    withData(
        PuzzleAnswer(Day01, 24000, 45000),
        PuzzleAnswer(Day02, 15, 12),
        PuzzleAnswer(Day03, 157, 70),
        PuzzleAnswer(Day04, 2, 4),
        PuzzleAnswer(Day05, "CMZ", "MCD"),
        PuzzleAnswer(Day06, 7, 19),
        PuzzleAnswer(Day07, 95437, 24933642),
        PuzzleAnswer(Day08, 21, 8),
        PuzzleAnswer(Day09, 13, 1),
    ) { (day, part1Expected, part2Expected) ->
        println("part 1 expected: $part1Expected")
        day.part1(day.testInput) shouldBe part1Expected

        println("part 2 expected: $part2Expected")
        day.part2(day.testInput) shouldBe part2Expected
    }

    test("day 9 extra test input") {
        val day = Day09
        day.part2(
            """
            R 5
            U 8
            L 8
            D 3
            R 17
            D 10
            L 25
            U 20
        """.trimIndent().lines()
        ) shouldBe 36
    }
})
