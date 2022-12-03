fun main() {

    fun part1(input: List<String>): Int {
        return input.sumOf { rucksack ->
            val (firstCompartment, secondCompartment) = rucksack.chunked(rucksack.length / 2) { it.toSet() }
            val item = firstCompartment.intersect(secondCompartment).first()
            item.priority()
         }
    }

    fun part2(input: List<String>): Int {
        return input.chunked(3)
        .sumOf { rucksacks ->
            val badge = rucksacks
                .fold(rucksacks.first().toSet()) { acc, rucksack -> 
                    acc.intersect(rucksack.toSet())
                }.first()
            badge.priority()
        }
    }

    val testInput = readInput("Day03_test")
    val input = readInput("Day03")

    check(part1(testInput) == 157)
    part1(input).let {
        println(it)
        check(it == 7821)
    }
    
    check(part2(testInput) == 70)
    part2(input).let {
        println(it)
        check(it == 2752)
    }
}

fun Char.priority() = when (this) {
    in 'a'..'z' -> this - 'a' + 1
    in 'A'..'Z' -> this - 'A' + 27
    else -> error("invalid input")
}
