import java.util.*
import kotlin.math.ceil
import java.io.File
import java.io.PrintStream

class TextIO(outputFilename: String?, inputFilename: String?) {
    // Checks if we're using files to determine where inputs come from and where outputs go
    private val outputFile: PrintStream = if (outputFilename == null) {
        System.out
    } else {
        PrintStream(File(outputFilename)) // Writes everything to file
    }
    val inputFile: Scanner = if (inputFilename == null) {
        Scanner(System.`in`)
    } else {
        Scanner(File(inputFilename)) // Takes input from file
    }
    fun out(message: String) = outputFile.println(message)
    fun input(): String = inputFile.next()
    fun error(message: String) = println(message) // For console errors

    companion object {
        lateinit var io: TextIO
            private set
        // To initialise IO to be used everywhere
        fun initialise(outputFilename: String?, inputFilename: String?) {
            io = TextIO(outputFilename, inputFilename)
        }
    }
}
fun main(args: Array<String>) {
    // Determines if we're using files in any capacity
    val outputFilename = if ("-outputFile" in args) {
        args[args.indexOf("-outputFile") + 1]
    } else {
        null
    }
    val inputFilename = if ("-inputFile" in args) {
        args[args.indexOf("-inputFile") + 1]
    } else {
        null
    }
    TextIO.initialise(outputFilename, inputFilename)
    val scanner = TextIO.io.inputFile
    val acceptedArgs = mutableListOf("long", "line", "word", "-inputFile", "-sortingType",
        "-dataType", "natural", "byCount", "-outputFile")
    for (i in 0..args.count()) { // Checks for valid args
        try {
            if (args[i] !in acceptedArgs) {
                if (args[i-1] != "-outputFile" && args[i-1] != "-inputFile") {
                    TextIO.io.error(" \"${args[i]}\" is not a valid parameter. It will be skipped.")
                }
            }
        } catch (_: ArrayIndexOutOfBoundsException) {} // Just holds exceptions
    }
    if("-sortingType" in args) {
        // args can include -sortingType followed by "byCount". Otherwise, it sorts by natural
        val naturalOrByCount = args.indexOf("-sortingType") + 1
        try {
            if(args[naturalOrByCount] == "byCount") {
                sortByCount(args, scanner)
            } else {
                sortNatural(args, scanner)
            }
        } catch (e: ArrayIndexOutOfBoundsException) {
            TextIO.io.error("No sorting type defined!")
        }
    } else {
        sortNatural(args, scanner)
    }
    // Closing scanner to prevent UninitializedPropertyAccessException
    scanner.close()
}
fun sortByCount(args: Array<String>, scanner: Scanner) {
    // Sorts list by lowest to the highest counts
    val lineLongWord = args.indexOf("-dataType") + 1
    if(args[lineLongWord] == "line") {
        val lineList = lineList(scanner)
        val countedMap = countInListStrings(lineList)
        val sortedMap = countedMap.toSortedMap() // Sorts list descending order
        TextIO.io.out("Total lines: ${lineList.count()}")
        printSorted(sortedMap, lineList)
    } else if(args[lineLongWord] == "long") {
        val (numList, wordList) = numList(scanner)
        val countedMap = countInListNums(numList.sortedBy { it })
        val sortedMap = countedMap.toList().sortedBy { (_, v) -> v }.toMap() // Sorts list descending order
        for(i in wordList) {
            TextIO.io.error("\"$i\" is not a long. It will be skipped. ")
        }
        TextIO.io.out("Total numbers: ${numList.count()}")
        for(i in sortedMap) {
            // Duplicate code, needs refactoring in future
            val percentageInMap = (i.value.toDouble()/numList.count())*100
            if((percentageInMap - 0.5).toInt() == percentageInMap.toInt()) {
                TextIO.io.out("${i.key}: ${i.value} time(s), ${ceil(percentageInMap).toInt()}%")
            } else {
                TextIO.io.out("${i.key}: ${i.value} time(s), ${percentageInMap.toInt()}%")
            }
        }
    } else {
        val wordList = wordList(scanner)
        val countedMap = countInListStrings(wordList)
        val sortedMap = countedMap.toList().sortedBy { (_, v) -> v }.toMap() // Sorts list descending order
        TextIO.io.out("Total words: ${wordList.count()}")
        printSorted(sortedMap, wordList)
    }
}
fun printSorted(sortedMap: Map<String, Int>, list: List<String>) {
    // Function to reduce duplicate code. Checks the map and prints the required statements
    sortedMap.forEach { i ->
        val percentageInMap = (i.value.toDouble()/list.count())*100
        if((percentageInMap - 0.5).toInt() == percentageInMap.toInt()) {
            TextIO.io.out("${i.key}: ${i.value} time(s), ${ceil(percentageInMap).toInt()}%")
        } else {
            TextIO.io.out("${i.key}: ${i.value} time(s), ${percentageInMap.toInt()}%")
        }
    }
}
fun countInListNums(list: List<Int>): MutableMap<Int,Int> {
    // Takes a list of ints and returns a map of ints and counts
    val mapOfCounts = mutableMapOf<Int, Int>()
    for(i in list) {
        var count = 0
        for (j in list) {
            if (j == i) count++
        }
        mapOfCounts += i to count
    }
    return mapOfCounts
}
fun countInListStrings(list: List<String>): MutableMap<String, Int> {
    // Takes a list of strings and returns a map of strings and counts
    val mapOfCounts = mutableMapOf<String, Int>()
    for(i in list) {
        var count = 0
        for (j in list) {
            if (j == i) count++
        }
        mapOfCounts += i to count
    }
    return mapOfCounts
}
fun sortNatural(args: Array<String>, scanner: Scanner){
    // Sorts by Numerical for nums and lexicographic for words and lines
    val lineLongWord = args.indexOf("-dataType") + 1
    try {
        if(args[lineLongWord] == "line") {
            var lineList = lineList(scanner)
            lineList = lineList.sorted()
            TextIO.io.out("Total lines: ${lineList.count()}")
            TextIO.io.out("Sorted data:")
            for((counter) in lineList.withIndex()) {
                TextIO.io.out(lineList[counter])
            }
        } else if(args[lineLongWord] == "long") {
            var (numList, wordList) = numList(scanner)
            numList = numList.sorted() as MutableList<Int>
            for(i in wordList) {
                TextIO.io.error("\"$i\" is not a long. It will be skipped. ")
            }
            TextIO.io.out("Total numbers: ${numList.count()}")
            TextIO.io.out("Sorted data: ${numList.joinToString().replace(",", "")}")
        } else if(args[lineLongWord] == "word") {
            var wordList = wordList(scanner)
            wordList = wordList.sorted()
            TextIO.io.out("Total words: ${wordList.count()}")
            TextIO.io.out("Sorted data: ${wordList.joinToString().replace(",", "")}")
        }
    } catch (e: IndexOutOfBoundsException) {
        TextIO.io.error("No data type defined!")
    }
}
fun numList(scanner: Scanner): Pair<MutableList<Int>, MutableList<String>> {
    // This function takes endless number inputs and returns a list
    val numList = mutableListOf<Int>()
    val notNums = mutableListOf<String>()
    while (scanner.hasNext()) {
        try {
            val nums = scanner.nextInt()
            numList.add(nums)
        } catch (e: InputMismatchException) {
            val words = TextIO.io.input()
            notNums.add(words)
        }
    }
    return Pair(numList, notNums)
}
fun lineList(scanner: Scanner): List<String> {
    // This function takes endless line inputs and returns a list
    val lineList = mutableListOf<String>()
    while(scanner.hasNext()) {
        val lines = scanner.nextLine()
        lineList.add(lines)
    }
    return lineList
}
fun wordList(scanner: Scanner): List<String> {
    // This function takes endless word inputs and returns a list
    val wordList = mutableListOf<String>()
    while (scanner.hasNext()) {
        val words = TextIO.io.input()
        wordList.add(words)
    }
    return wordList
}