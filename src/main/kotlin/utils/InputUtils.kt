package utils

import java.io.File
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.createDirectories

private const val FILE_SUFFIX = ".txt"
private fun fileNameFrom(directory: String, day: Int) = "${directory}Day${day.asTwoDigitNumber()}$FILE_SUFFIX"

fun readInput(year: Int, day: Int): List<String> {
    val directory = "src/main/resources/$year/"
    val file = File(fileNameFrom(directory, day))
    return when (file.exists()) {
        true -> file.readLines()
        false -> downloadFile(year, day, directory).readLines()
    }
}

private fun downloadFile(year: Int, day: Int, directory: String): File {
    System.getenv("AOC_SESSION_TOKEN")?.let {
        Paths.get(directory).createDirectories()
        val aocUrl = URL("https://adventofcode.com/$year/day/$day/input")
        val inputStream = urlToInputStream(aocUrl, mapOf("cookie" to "session=$it"))
            ?: error("something wrong with url config, maybe update AOC_SESSION_TOKEN?")

        val fileName = fileNameFrom(directory, day)
        Files.copy(inputStream, Paths.get(fileName))
        return File(fileName)
    } ?: error("set AOC_SESSION_TOKEN environment variable")
}

private fun urlToInputStream(url: URL, args: Map<String, String>): InputStream? {
    return with(url.openConnection() as HttpURLConnection) {
        connectTimeout = 15000
        readTimeout = 15000
        args.forEach { setRequestProperty(it.key, it.value) }
        connect()
        if (responseCode in 200..299) {
            println("called AoC for input $url")
            inputStream
        } else {
            println("responseCode $responseCode, responseMessage $responseMessage")
            null
        }
    }
}
