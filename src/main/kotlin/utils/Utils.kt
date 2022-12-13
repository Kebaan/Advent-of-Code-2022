package utils

import java.math.BigInteger
import java.security.MessageDigest

fun Int.asTwoDigitNumber(): String = "%02d".format(this)

/**
 * Converts string to utils.md5 hash.
 */
@Suppress("unused")
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

fun String.toPair(delimiter: String, limit: Int = 1): Pair<String, String> {
    val split = this.split(delimiter)
    split.onEach {
        require(it.length <= limit) { "$it is too large with limit $limit" }
    }
    require(split.size == 2) { "can't make a pair out of '$this'" }
    return split.first() to split.last()
}

fun String.asIntRange(): IntRange {
    return this.split("-")
        .let { (from, to) -> from.toInt()..to.toInt() }
}

@Suppress("unused")
infix fun IntRange.overlaps(other: IntRange): Boolean =
    first <= other.last && other.first <= last

@Suppress("unused")
infix fun IntRange.fullyOverlaps(other: IntRange): Boolean =
    first <= other.first && other.last >= last

inline fun <T> Iterable<T>.takeUntil(predicate: (T) -> Boolean): List<T> {
    val list = ArrayList<T>()
    for (item in this) {
        list.add(item)
        if (predicate(item))
            break
    }
    return list
}
