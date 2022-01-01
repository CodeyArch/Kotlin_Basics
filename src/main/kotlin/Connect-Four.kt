fun main() {
    println("Connect Four")
    println("First player's name: ")
    val player1 = readLine() // Gets first player
    println("Second player's name: ")
    val player2 = readLine() // Gets second player
    while(true) {
        val regex = Regex("(\\d+) *[xX] *(\\d+)") // Regex to allow strings such as "9 x 9" and "9X9"
        println("Set the board dimensions (Rows x Columns)")
        println("Press Enter for default (6 x 7)")
        val gameDimensions = readLine()?.trim()?.lowercase() // Setting "X" to lowercase to simplify process
        val s = gameDimensions?.replace("\\s".toRegex(), "") // Remove whitespaces

        if(s?.matches(regex) == true) { // Checks if it matches regex
            val parts = s.split("x")
            if(parts[0].toInt() < 5) {

                println("Board rows should be from 5 to 9") // General limitations
            }
            else if(parts[0].toInt() > 9) {
                println("Board rows should be from 5 to 9") // General limitations
            } else {
                if(parts[1].toInt() < 5) {
                    println("Board columns should be from 5 to 9") // General limitations
                }
                else if(parts[1].toInt() > 9) {
                    println("Board columns should be from 5 to 9") // General limitations
                } else {
                    println("$player1 VS $player2") // Printing who is supposed to play
                    println("${parts[0]} X ${parts[1]} board") // Printing the size of the board
                    printBoard(parts)
                    break
                }
            }
        }
        else if(gameDimensions?.isEmpty() == true) {
            val parts = listOf("6", "7")
            println("$player1 VS $player2") // Printing who is supposed to play
            println("${parts[0]} X ${parts[1]} board") // Printing the size of the board
            printBoard(parts)
            break
        } else {
            println("Invalid input") // Letters and such break the game, we want only numbers
        }
    }
}
fun printBoard(parts: List<String>) { // Takes the parts list I generate and turns it into the board
    var game = "" // Initializing
    val rows = parts[0].toInt() + 1 // I need one more row than player mentions
    val columns = parts[1].toInt()

    for(eachRow in 0..rows) { // Checks each row so that it generates the correct number of rows
        for(eachColumn in 1..columns) { // Checks each column so that it generates the correct number of columns
            if(eachRow == rows) {
                game += when(eachColumn) { // Chooses the correct symbol needed here
                    1 -> "╚"
                    columns -> "═╩═╝"
                    else -> "═╩"
                }
            } else {
                game += if (eachRow == 0) " $eachColumn" else "║ " // Chooses the correct symbol needed here
                game += if (eachColumn == columns && eachRow != 0) "║" else "" // Chooses the correct symbol needed here
            }
        }
        game += "\n" // Want each row on a new line after column end, otherwise it breaks visually
    }
    print(game) //
}