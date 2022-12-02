import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')


fun <T> List<T>.splitBy(predicate: (T) -> Boolean): List<List<T>> = this
    .splitIndices(predicate)
    .windowed(size = 2, step = 2) { (from, to) ->
        this.slice(from..to)
    }

private fun <T> List<T>.splitIndices(predicate: (T) -> Boolean): List<Int> = this
    .flatMapIndexed { index, x ->
        when {
            index == 0 || index == this.lastIndex -> listOf(index)
            predicate(x) -> listOf(index - 1, index + 1)
            else -> emptyList()
        }
    }
