fun main() {
    var str = "_________"
    printboard(str)
    var player = "X"
    do {
        str = getUserInput(str, player) // Coordinates
        player = togglePlayer(player) // switches from X to O and O to X
        printboard(str) // prints the current board
        val gameOver = checkState(str) // Is the game over yet? Who knows!
    } while(!gameOver) //ends the game
}
fun printboard(str: String){
    val charArray = str.toCharArray() // Who needs mutable lists eh
    println("""
        ---------
        | ${charArray[0]} ${charArray[1]} ${charArray[2]} |
        | ${charArray[3]} ${charArray[4]} ${charArray[5]} |
        | ${charArray[6]} ${charArray[7]} ${charArray[8]} |
        ---------
    """.trimIndent()) // The game board
}
fun togglePlayer(player: String): String {
    return when (player) {
        "X" -> "O"
        "O" -> "X"
        else -> throw Exception("How are there now 3 of you?")
    }
}

fun getUserInput(str: String, player: String): String {
    while(true) {
        print("Enter coordinates: ")
        val str2 = readLine()?.trim()?.split(" ")

        if(str2?.size != 2) {
            println("You should enter numbers!")
            continue
        }
        try {
            val row = Integer.parseInt(str2[0])
            val col = Integer.parseInt(str2[1])

            if (row in 1..3 && col in 1..3) {
                val index =  3 * (row - 1) + (col - 1)
                val cell = str[index]
                if (cell == '_') {
                    return str.replaceRange(index..index, player)
                } else if (cell in "XO") {
                    println("This cell is occupied! Choose another one!")
                }
            } else {
                println("Coordinates should be from 1 to 3!")
            }
        } catch (ex: NumberFormatException) {
            println("You should enter numbers!")
        }
    }
}
fun checkDiagonals(str: String): String? { // Gives me the win conditions for diagonals without manual work
    if (str[0] != '_' && str[0] == str[4] && str[4] == str[8]) { // top left to bottom right
        return "${str[0]} wins"
    }
    if (str[2] != '_' && str[2] == str[4] && str[4] == str[6]) { // top right to bottom left
        return "${str[2]} wins"
    }
    return null
}
fun checkColumns(str: String): String? {
    for (col in 0..2) {
        if (getCol(str, col) == "XXX") {
            for (otherCol in 0..2) {
                if (col != otherCol) {
                    if (getCol(str, otherCol) == "OOO") {
                        return "Impossible"
                    }
                }
            }
            return "X wins"
        }
        else if (getCol(str, col) == "OOO") {
            for (otherCol in 0..2) {
                if (col != otherCol) {
                    if (getCol(str, otherCol) == "XXX") {
                        return "Impossible"
                    }
                }
            }
            return "O wins"
        }
    }
    return null
}
fun checkRows(str: String): String? {
    for (row in 0..2) {
        if (getRow(str, row) == "XXX") {
            for (otherRow in 0..2) {
                if (row != otherRow) {
                    if (getRow(str, otherRow) == "OOO") {
                        return "Impossible"
                    }
                }
            }
            return "X wins"
        }
        else if (getRow(str, row) == "OOO") {
            for (otherRow in 0..2) {
                if (row != otherRow) {
                    if (getRow(str, otherRow) == "XXX") {
                        return "Impossible"
                    }
                }
            }
            return "O wins"
        }
    }
    return null
}
fun checkState(str: String): Boolean {
    val countX = str.filter { it == 'X' }.length
    val countO = str.filter { it == 'O' }.length
    if (kotlin.math.abs(countO - countX) > 1) {
        println("Impossible")
        return true
    }

    val rowResult = checkRows(str)
    if (rowResult != null) {
        println(rowResult)
        return true
    }

    val colResult = checkColumns(str)
    if (colResult != null) {
        println(colResult)
        return true
    }

    val diagResult = checkDiagonals(str)
    if (diagResult != null) {
        println(diagResult)
        return true
    }

    if (str.contains("_")) {

        return false
    } else {
        println("Draw")
        return true
    }
    return false
}
fun getRow(str: String, row: Int): String { // returns row to be used later
    return "${str[row * 3]}${str[row * 3 + 1]}${str[row * 3 + 2]}"
}

fun getCol(str: String, col: Int): String { // returns column to be used later
    return "${str[col]}${str[col + 3]}${str[col + 6]}"
}