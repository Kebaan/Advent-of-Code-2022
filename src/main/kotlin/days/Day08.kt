package days

import Point
import Puzzle
import utils.readInput
import utils.takeUntil

data class Tree(val location: Point, val height: Int) {
    fun visibleThrough(direction: List<Tree>) = direction.all { it.height < height }
}

private typealias Forest = List<List<Tree>>

private fun Forest.leftTrees(tree: Tree) = (tree.location.x - 1 downTo 0).map { x -> this[x][tree.location.y] }
private fun Forest.rightTrees(tree: Tree) = (tree.location.x + 1 until this.size).map { x -> this[x][tree.location.y] }
private fun Forest.topTrees(tree: Tree) = (tree.location.y - 1 downTo 0).map { y -> this[tree.location.x][y] }
private fun Forest.bottomTrees(tree: Tree) =
    (tree.location.y + 1 until this[tree.location.x].size).map { y -> this[tree.location.x][y] }

private fun Forest.isVisible(tree: Tree): Boolean {
    return when {
        tree.location.x == 0 || tree.location.x == this.size -> true
        tree.location.y == 0 || tree.location.y == this[tree.location.x].size -> true
        tree.visibleThrough(leftTrees(tree)) -> true
        tree.visibleThrough(rightTrees(tree)) -> true
        tree.visibleThrough(topTrees(tree)) -> true
        tree.visibleThrough(bottomTrees(tree)) -> true
        else -> return false
    }
}

private fun Forest.scenicScore(tree: Tree): Int {
    when {
        tree.location.x == 0 || tree.location.x == this.size - 1 -> return 0
        tree.location.y == 0 || tree.location.y == this[tree.location.x].size - 1 -> return 0
    }

    val visibleLeft = leftTrees(tree).takeUntil { it.height >= tree.height }
    val visibleRight = rightTrees(tree).takeUntil { it.height >= tree.height }
    val visibleUp = topTrees(tree).takeUntil { it.height >= tree.height }
    val visibleDown = bottomTrees(tree).takeUntil { it.height >= tree.height }

    return visibleLeft.size * visibleRight.size * visibleUp.size * visibleDown.size
}

object Day08 : Puzzle<Int> {
    private fun parseForest(input: List<String>) = input
        .mapIndexed { x, row ->
            row.mapIndexed { y, tree ->
                Tree(Point(x, y), tree.digitToInt())
            }
        }

    override fun part1(input: List<String>): Int {
        val forest = parseForest(input)
        return forest.sumOf { row ->
            row.count { tree ->
                forest.isVisible(tree)
            }
        }
    }

    override fun part2(input: List<String>): Int {
        val forest = parseForest(input)
        val scores = forest.flatMap { row ->
            row.map { tree ->
                forest.scenicScore(tree)
            }
        }

        return scores.max()
    }

    override fun doSolve() {
        val input = readInput(2022, 8)
        val testInput = """
            30373
            25512
            65332
            33549
            35390""".trimIndent().lines()
        check(part1(testInput) == 21)


        part1(input).let {
            println(it)
            check(it == 1763)
        }

        check(part2(testInput) == 8)
        part2(input).let {
            println(it)
            check(it == 671160)
        }
    }
}
