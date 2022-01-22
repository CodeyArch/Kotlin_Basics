import java.util.Scanner
import kotlin.math.ceil

fun main(args: Array<String>) {
    // args should be something like -datatype long
    val scanner = Scanner(System.`in`)
    if("-sortingType" in args) {
        // args can now include -sortingType followed by "byCount". Otherwise, it sorts by natural
        val naturalOrByCount = args.indexOf("-sortingType") + 1
        if(args[naturalOrByCount] == "byCount") {
            sortByCount(args, scanner)
        } else {
            sortNatural(args, scanner)
        }
    } else {
        sortNatural(args, scanner)
    }
}
fun sortByCount(args: Array<String>, scanner: Scanner) {
    // Sorts list by lowest to the highest counts
    val lineLongWord = args.indexOf("-dataType") + 1
    if(args[lineLongWord] == "line") {
        val lineList = lineList(scanner)
        val countedMap = countInListStrings(lineList)
        val sortedMap = countedMap.toSortedMap() // Sorts list descending order
        println("Total lines: ${lineList.count()}")
        printSorted(sortedMap, lineList)
    } else if(args[lineLongWord] == "long") {
        val numList = numList(scanner)
        val countedMap = countInListNums(numList.sortedBy { it })
        val sortedMap = countedMap.toList().sortedBy { (_, v) -> v }.toMap() // Sorts list descending order
        println("Total numbers: ${numList.count()}")
        for(i in sortedMap) {
            // Duplicate code, needs refactoring in future
            val percentageInMap = (i.value.toDouble()/numList.count())*100
            if((percentageInMap - 0.5).toInt() == percentageInMap.toInt()) {
                println("${i.key}: ${i.value} time(s), ${ceil(percentageInMap).toInt()}%")
            } else {
                println("${i.key}: ${i.value} time(s), ${percentageInMap.toInt()}%")
            }
        }
    } else {
        val wordList = wordList(scanner)
        val countedMap = countInListStrings(wordList)
        val sortedMap = countedMap.toList().sortedBy { (_, v) -> v }.toMap() // Sorts list descending order
        println("Total words: ${wordList.count()}")
        printSorted(sortedMap, wordList)
    }
}
fun printSorted(sortedMap: Map<String, Int>, list: List<String>) {
    // Function to reduce duplicate code. Checks the map and prints the required statements
    for(i in sortedMap) {
        val percentageInMap = (i.value.toDouble()/list.count())*100
        if((percentageInMap - 0.5).toInt() == percentageInMap.toInt()) {
            println("${i.key}: ${i.value} time(s), ${ceil(percentageInMap).toInt()}%")
        } else {
            println("${i.key}: ${i.value} time(s), ${percentageInMap.toInt()}%")
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
    if(args[lineLongWord] == "line") {
        var lineList = lineList(scanner)
        lineList = lineList.sorted()
        println("Total lines: ${lineList.count()}")
        println("Sorted data:")
        for((counter) in lineList.withIndex()) {
            println(lineList[counter])
        }
    } else if(args[lineLongWord] == "long") {
        var numList = numList(scanner)
        numList = numList.sorted()
        println("Total numbers: ${numList.count()}")
        println("Sorted data: ${numList.joinToString().replace(",", "")}")
    } else {
        var wordList = wordList(scanner)
        wordList = wordList.sorted()
        println("Total words: ${wordList.count()}")
        println("Sorted data: ${wordList.joinToString().replace(",", "")}")
    }
}
fun numList(scanner: Scanner): List<Int> {
    // This function takes endless number inputs and returns a list
    val numList = mutableListOf<Int>()
    while(scanner.hasNext()) {
        val nums = scanner.nextInt()
        numList.add(nums)
    }
    return numList
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
fun wordList(scanner: Scanner): List<String>{
    // This function takes endless word inputs and returns a list
    val wordList = mutableListOf<String>()
    while (scanner.hasNext()) {
        val words = scanner.next()
        wordList.add(words)
    }
    return wordList
}