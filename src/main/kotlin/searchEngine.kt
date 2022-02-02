@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

import java.io.File

// Dataset used will be first name, last name, optional email
fun main(args: Array<String>) {
    val dataFile = if ("--data" in args) {
        args[args.indexOf("--data") + 1]
    } else {
        null
    }
    searcher(dataFile)
}
fun searcher(dataFile: String?) {
    val listOfSearches = mutableListOf<String>()
    var searchEnd = false
    val searchableInput = File(dataFile).readLines()
    for(i in searchableInput) {
        listOfSearches.add(i)
    }
    while (!searchEnd) {
        // Endless loop for easy searching
        println("""
            
            === Menu ===
            1. Find a person
            2. Print all people
            0. Exit
        """.trimIndent())
        when (readLine()?.toInt() ?: 0) {
            1 -> findPerson(listOfSearches)
            2 -> printAll(listOfSearches)
            0 -> {
                // Ends loop
                searchEnd = true
                println("")
                println("Bye!")
            }
            else -> {
                println("")
                println("Incorrect option! Try again.")
            }
        }
    }
}
fun findPerson(listOfSearches: MutableList<String>) {
    // Takes data and compares it to list to search for it. Linear search used at this stage
    println("")
    println("Enter a name or email to search all suitable people: ")
    val dataInput = readLine()
    val foundSearches = mutableListOf<String>()
    for (i in listOfSearches) {
        val stringVers = i.replace(",", "")
        if (stringVers.contains(dataInput.toString(), ignoreCase = true)) {
            foundSearches.add(stringVers)
        }
    }
    if (foundSearches.isNotEmpty()) {
        for (i in foundSearches) {
            println(i)
        }
    } else println("No matching people found. ")
}
fun printAll(listOfSearches: MutableList<String>) {
    // Prints every entry in list
    println("")
    println("=== List of people ===")
    for (i in listOfSearches) {
        println(i.replace(",", ""))
    }
}
