package days

import Puzzle
import days.Day07.VirtualEntry.VirtualDirectory
import days.Day07.VirtualEntry.VirtualFile
import utils.readInput

typealias VirtualPath = String
typealias FileSize = Int

object Day07 : Puzzle<Int> {
    private val CD_COMMAND_REGEX = """[$] cd (.*)""".toRegex()
    private val SIZE_REGEX = """(\d+) (.*)""".toRegex()
    private const val rootPath = "/"

    private fun parseInput(input: List<String>): VirtualFileSystem {
        val fileSystem = VirtualFileSystem("/")
        var cwd = rootPath
        for (line in input) {
            CD_COMMAND_REGEX.matchEntire(line)?.destructured?.let { (dir) ->
                cwd = when (dir) {
                    rootPath -> rootPath
                    ".." -> fileSystem.findParentPath(cwd)
                    else -> if (cwd == rootPath) "/$dir" else "$cwd/$dir"
                }
                fileSystem.addDirectoryIfAbsent(cwd)
            } ?: SIZE_REGEX.matchEntire(line)?.destructured?.let { (size, fileName) ->
                val dir = if (cwd == rootPath) "/$fileName" else "$cwd/$fileName"
                fileSystem.addFileIfAbsent(dir, size.toInt())
            }
        }
        return fileSystem
    }

    override fun part1(input: List<String>): Int {
        val fileSystem = parseInput(input)
        return fileSystem.findAllEntriesBy {
            (it is VirtualDirectory && it.size <= 100000)
        }.sumOf { it.size }
    }

    override fun part2(input: List<String>): Int {
        val fileSystem = parseInput(input)
        val totalDiskSpace = 70_000_000
        val totalUsed = fileSystem.lookupSize(rootPath)
        val neededUnusedSpace = 30_000_000
        return fileSystem.findAllEntriesBy {
            it is VirtualDirectory && totalDiskSpace - (totalUsed - it.size) >= neededUnusedSpace
        }.minOf { it.size }
    }

    override fun doSolve() {
        val testInput = """
            $ cd /
            $ ls
            dir a
            14848514 b.txt
            8504156 c.dat
            dir d
            $ cd a
            $ ls
            dir e
            29116 f
            2557 g
            62596 h.lst
            $ cd e
            $ ls
            584 i
            $ cd ..
            $ cd ..
            $ cd d
            $ ls
            4060174 j
            8033020 d.log
            5626152 d.ext
            7214296 k""".trimIndent().lines()
        check(part1(testInput) == 95437)

        val input = readInput(2022, 7)

        part1(input).let {
            println(it)
            check(it == 1644735)
        }

        check(part2(testInput) == 24933642)
        part2(input).let {
            println(it)
            check(it == 1300850)
        }
    }

    sealed interface VirtualEntry {
        val path: VirtualPath
        val size: FileSize

        data class VirtualDirectory(override val path: VirtualPath, override val size: FileSize) : VirtualEntry
        data class VirtualFile(override val path: VirtualPath, override val size: FileSize) : VirtualEntry
    }

    class VirtualFileSystem(
        private val rootPath: VirtualPath,
    ) {
        private val data: MutableMap<VirtualPath, VirtualEntry> =
            mutableMapOf(rootPath to VirtualDirectory(rootPath, 0))

        fun findAllEntriesBy(predicate: (VirtualEntry) -> Boolean): List<VirtualEntry> {
            return data.filter { predicate(it.value) }.map { it.value }.toList()
        }

        fun findParentPath(orgPath: VirtualPath): VirtualPath {
            val parentPath = orgPath.substringBeforeLast('/')
            return parentPath.ifEmpty { rootPath }
        }

        fun lookupSize(virtualPath: VirtualPath): FileSize {
            return get(virtualPath).size
        }

        fun addDirectoryIfAbsent(path: VirtualPath) {
            data.computeIfAbsent(path) { VirtualDirectory(it, 0) }
        }

        fun addFileIfAbsent(path: VirtualPath, size: FileSize) {
            data.computeIfAbsent(path) {
                var dir = path
                while (true) {
                    dir = findParentPath(dir)
                    updateSizes(dir, size)
                    if (dir == rootPath) break
                }
                VirtualFile(path, size)
            }
        }

        fun updateSizes(path: VirtualPath, size: FileSize) {
            val entry = get(path)
            val newSize = entry.size + size
            val updatedEntry = when (entry) {
                is VirtualDirectory -> entry.copy(size = newSize)
                is VirtualFile -> entry.copy(size = newSize)
            }
            data[path] = updatedEntry
        }

        fun get(path: VirtualPath): VirtualEntry {
            return data[path] ?: error("invalid input")
        }
    }
}
