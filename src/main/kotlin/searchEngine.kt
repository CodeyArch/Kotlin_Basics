// Dataset used will be first name, last name, optional email
fun main() {
    searcher()
}
fun searcher() {
    val listOfSearches = mutableListOf<MutableList<String>>()
    var searchEnd = false
    println("Enter the number of people: ")
    val searchCount = readLine()?.toInt() ?: 0
    println("Enter all people: ")
    repeat(searchCount) {
        // Takes input and adds it to the list of data
        val searchableInput = readLine()?.split(" ")
        searchableInput?.toMutableList()?.let { listOfSearches.add(it) }
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
fun findPerson(listOfSearches: MutableList<MutableList<String>>) {
    // Takes data and compares it to list to search for it. Linear search used at this stage
    println("")
    println("Enter a name or email to search all suitable people: ")
    val dataInput = readLine()
    val foundSearches = mutableListOf<String>()
    for (i in listOfSearches) {
        val stringVers = i.joinToString().replace(",", "")
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
fun printAll(listOfSearches: MutableList<MutableList<String>>) {
    // Prints every entry in list
    println("")
    println("=== List of people ===")
    for (i in listOfSearches) {
        println(i.joinToString().replace(",", ""))
    }
}