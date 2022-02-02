// Dataset used will be first name, last name, optional email
fun main() {
    searcher()
}
fun searcher() {
    val listOfSearches = mutableListOf<MutableList<String>>()
    println("Enter the number of people: ")
    val searchCount = readLine()?.toInt() ?: 0
    println("Enter all people: ")
    repeat(searchCount) {
        // Takes input and adds it to the list of data
        val searchableInput = readLine()?.split(" ")
        searchableInput?.toMutableList()?.let { listOfSearches.add(it) }
    }
    println("")
    println("Enter the number of search queries: ")
    val searchQueriesCount = readLine()?.toInt() ?: 0
    repeat(searchQueriesCount) {
        // Takes data and compares it to list to search for it. Linear search used at this stage
        println("")
        println("Enter data to search people: ")
        val dataInput = readLine()
        val foundSearches = mutableListOf<String>()
        for (i in listOfSearches) {
            val stringVers = i.joinToString().replace(",", "")
            if (stringVers.contains(dataInput.toString(), ignoreCase = true)) {
                foundSearches.add(stringVers)
            }
        }
        if (foundSearches.isNotEmpty()) {
            println("")
            println("People found: ")
            for (i in foundSearches) {
                println(i)
            }
        } else println("No matching people found. ")
    }
}