
import java.io.File
import java.io.FileNotFoundException

//
// Dataset used will be first name, last name, optional email
fun main(args: Array<String>) {
    // Validation and assignment of file
    val dataFile = if ("--data" in args) {
        try {
            args[args.indexOf("--data") + 1]
        } catch (e: ArrayIndexOutOfBoundsException) {
            println("No file attached")
            null
        }
    } else {
        null
    }
    if (dataFile != null) {
        searcher(dataFile)
    }
}
fun searcher(dataFile: String?) {
    var searchEnd = false
    var searchableInput = listOf<String>()
    // Input validation
    try {
        searchableInput = File(dataFile ?: error("No file")).readLines()
    } catch (e: FileNotFoundException) {
        println("Invalid File")
        searchEnd = true // Ends program
    }
    val invertedIndex = index(searchableInput)
    while (!searchEnd) {
        // Endless loop for easy searching
        println("""
            
            === Menu ===
            1. Find a person
            2. Print all people
            0. Exit
        """.trimIndent())
        when (readLine()?.toInt() ?: 0) {
            1 -> searchType(invertedIndex, searchableInput)
            2 -> printAll(searchableInput)
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
fun searchType(invertedIndex: MutableMap<String, MutableList<Int>>, searchableInput: List<String>) {
    println("Select a matching strategy: ALL, ANY, NONE")
    when (readLine()) {
        "ANY" -> findAny(invertedIndex, searchableInput)
        "ALL" -> findAll(invertedIndex, searchableInput)
        "NONE" -> findNone(invertedIndex, searchableInput)
        else -> println("Search type not found")
    }
}
fun createIndices(invertedIndex: MutableMap<String, MutableList<Int>>, dataInput: List<String>?): MutableList<Int> {
    val indices = mutableListOf<Int>()
    if (dataInput != null) {
        for (strings in dataInput) {
            if (strings in invertedIndex.keys) {
                for (index in invertedIndex[strings] ?: error("Nothing here") ) {
                    if (indices.isEmpty()) {
                        indices.add(index)
                    } else{
                        if (index !in indices) {
                            indices.add(index)
                        }
                    }
                }
            }
        }
    }
    return indices
}
fun findNone(invertedIndex: MutableMap<String, MutableList<Int>>, searchableInput: List<String>) {
    println("")
    println("Enter a name or email to search none suitable people: ")
    val dataInput = readLine()?.lowercase()?.split(" ")
    val indices = createIndices(invertedIndex, dataInput)
    val notIn = mutableListOf<Int>()
    if (dataInput != null) {
        for (id in searchableInput.indices) {
            if (id !in indices) {
                notIn.add(id)
            }
        }
    }
    if (notIn.isNotEmpty()) {
        println("")
        println("${notIn.size} persons found:")
        for (idx in notIn) {
            println(searchableInput[idx])
        }
    } else {
        println("No matching people found")
    }
}
fun findAny(invertedIndex: MutableMap<String, MutableList<Int>>, searchableInput: List<String>) {
    println("")
    println("Enter a name or email to search any suitable people: ")
    val dataInput = readLine()?.lowercase()?.split(" ")
    val indices = createIndices(invertedIndex, dataInput)
    if (indices.isNotEmpty()) {
        println("")
        println("${indices.size} persons found:")
        for (idx in indices) {
            println(searchableInput[idx])
        }
    } else {
        println("No matching people found")
    }
}
fun findAll(invertedIndex: MutableMap<String, MutableList<Int>>, searchableInput: List<String>) {
    // Takes data input and compares to a pre-indexed map
    println("")
    println("Enter a name or email to search all suitable people: ")
    val dataInput = readLine()?.lowercase()?.split(" ")
    var indices = mutableListOf<Int>()
    if (dataInput != null) {
        for (id in dataInput.indices) {
            if (dataInput[id] in invertedIndex.keys) {
                // Checks it matches a key and saves the indexes
                indices = if (id == 0) {
                    invertedIndex[dataInput[id]] ?: error("Nothing here")
                } else {
                    val tempIds = invertedIndex[dataInput[id]] ?: error("Nothing here")
                    indices.intersect(tempIds.toSet()).toMutableList()
                }
            }
        }
    }
    if (indices.isNotEmpty()) {
        println("")
        println("${indices.size} persons found:")
        for (idx in indices) {
            println(searchableInput[idx])
        }
    } else {
        println("No matching people found")
    }
}
fun printAll(searchableInput: List<String>) {
    // Prints every entry in list
    println("")
    println("=== List of people ===")
    for (i in searchableInput) {
        println(i.replace(",", ""))
    }
}
fun index(searchableInput: List<String>): MutableMap<String, MutableList<Int>> {
    val invertedIndex = mutableMapOf<String, MutableList<Int>>()
    for (l in 0..searchableInput.lastIndex) {
        val line = searchableInput[l].split(' ')
        for (words in line) {
            val word = words.lowercase()
            if (word in invertedIndex.keys) {
                val values = invertedIndex.getValue(word)
                values.add(l)
                invertedIndex[word] = values
            } else {
                invertedIndex[word] = mutableListOf(l)
            }
        }
    }
    return invertedIndex
}
