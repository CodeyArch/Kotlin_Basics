import java.util.Scanner

fun main(args: Array<String>) {
    // args should be something like -datatype long
    val scanner = Scanner(System.`in`)
    if(args[1] == "long") {
        numSort(scanner)
    } else if(args[1] == "line") {
        lineSort(scanner)
    } else if(args[1] == "word") {
        wordSort(scanner)
    } else {
        wordSort(scanner)
    }
}
fun numSort(scanner: Scanner) {
    // This function takes endless number inputs and outputs the most common number and amount of times used
    val numList = mutableListOf<Int>()
    while(scanner.hasNext()) {
        val nums = scanner.nextInt()
        numList.add(nums)
    }
    val largestNumber = numList.maxOrNull()
    var largestNumberCount = 0
    for(i in numList) {
        if(i == largestNumber) {
            largestNumberCount++
        }
    }
    val numPercentage = (largestNumberCount.toDouble()/numList.count()) * 100
    println("Total numbers: ${numList.count()}")
    println("The greatest number: $largestNumber ($largestNumberCount time(s), ${numPercentage.toInt()}%).")
}
fun lineSort(scanner: Scanner) {
    // This function takes endless line inputs and outputs the most common line and amount of times used
    val lineList = mutableListOf<String>()
    while(scanner.hasNext()) {
        val lines = scanner.nextLine()
        lineList.add(lines)
    }
    val longestLine = lineList.maxByOrNull { it.length }
    val lineCount = getLargest(lineList, longestLine)
    val linePercentage = (lineCount.toDouble()/lineList.count())*100
    println("Total lines: ${lineList.count()}")
    println("The longest line:")
    println("$longestLine")
    println("($lineCount time(s), ${linePercentage.toInt()}%).")
}
fun wordSort(scanner: Scanner) {
    // This function takes endless word inputs and outputs the most common word and amount of times used
    val wordList = mutableListOf<String>()
    while (scanner.hasNext()) {
        val words = scanner.next()
        wordList.add(words)
    }
    val longestWord = wordList.maxByOrNull { it.length }
    val wordCount = getLargest(wordList, longestWord)
    val wordPercentage = (wordCount.toDouble() / wordList.count()) * 100
    println("Total words: ${wordList.count()}")
    println("The longest word: $longestWord ($wordCount time(s), ${wordPercentage.toInt()}%).")
}
fun getLargest(list: List<String>, longest: String?): Int {
    var count = 0
    for(i in list) {
        if(i == longest) {
            count++
        }
    }
    return count
}