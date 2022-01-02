// There are many duplicate lines that would fit better within a function
fun main() {
    println("Connect Four")
    println("First player's name: ")
    val player1 = readLine() // Gets first player
    println("Second player's name: ")
    val player2 = readLine() // Gets second player
    var toggleTurn = 1

    loop@while(true) {
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
                    val rows = parts[0].toInt() // Easier explicit use
                    val cols = parts[1].toInt() // Easier explicit use
                    val stateList = MutableList(rows) { MutableList(cols) { 0 } } // Using this to check positions
                    printBoard(rows, cols, stateList)
                    while(true) {
                        if(toggleTurn == 1) {
                            println("$player1\'s turn")
                        } else {
                            println("$player2\'s turn")
                        }
                        val move = readLine()?.trim()?.replace("\\s+".toRegex(), "")?.lowercase() ?: "apple" // Inputs for columns/end
                        if (move == "end") {
                            println("Game over!")
                            break@loop // Ends the full game loop
                        }
                        try {
                            if(move.toInt() !in 1..parts[1].toInt()) {
                                println("The column number is out of range (1 - ${parts[1]})")
                                continue // Continues the check loop
                            }
                        } catch(e: NumberFormatException) {
                            println("Incorrect column number")
                            continue // Continues the check loop
                        }
                        val colNo: Int = move.toInt()
                        val tempCol = getColumn(colNo, stateList) // Brings the state of columns in
                        makeMove(toggleTurn, colNo, stateList, tempCol) // Places the input in the correct area, based on player
                        if (!tempCol.contains(0)){
                            println("Column $colNo is full")
                            continue
                        }
                        val winner = gameRows(toggleTurn, stateList) // Determines if a win condition has been met
                        if (winner) {
                            printBoard(rows, cols, stateList)
                            if (toggleTurn == 1) { // Making sure the right player wins
                                println("Player $player1 won")
                            } else {
                                println("Player $player2 won")
                            }
                            println("Game over!")
                            break
                        }
                        toggleTurn = (if (toggleTurn == 1) { // Switches the player over
                            2
                        } else 1)
                        printBoard(rows, cols, stateList)
                        var tieGame = true
                        for (i in stateList){ // Checks for ties
                            if (i.contains(0)){
                                tieGame = false
                            }
                        }

                        if (tieGame) {
                            println("It is a draw")
                            println("Game over!")
                            break // Ends the game loop
                        }
                    }
                }
            }
        }
        else if(gameDimensions?.isEmpty() == true) {
            val parts = listOf("6", "7")
            println("$player1 VS $player2") // Printing who is supposed to play [This is duplicate code from here to line 160]
            println("${parts[0]} X ${parts[1]} board") // Printing the size of the board
            val rows = parts[0].toInt() // Easier explicit use
            val cols = parts[1].toInt() // Easier explicit use
            val stateList = MutableList(rows) { MutableList(cols) { 0 } } // Using this to check positions
            printBoard(rows,cols, stateList)


            while(true) {
                if(toggleTurn == 1) {
                    println("$player1\'s turn")
                } else {
                    println("$player2\'s turn")
                }
                val move = readLine()?.trim()?.replace("\\s+".toRegex(), "")?.lowercase() ?: "apple"
                if (move == "end") {
                    println("Game over!")
                    break@loop // Ends the full game loop
                }
                try {
                    if(move.toInt() !in 1..parts[1].toInt()) {
                        println("The column number is out of range (1 - ${parts[1]})")
                        continue
                    }
                } catch(e: NumberFormatException) {
                    println("Incorrect column number")
                    continue
                }
                val colNo: Int = move.toInt()
                val tempCol = getColumn(colNo, stateList)
                makeMove(toggleTurn, colNo, stateList, tempCol)
                if (!tempCol.contains(0)){
                    println("Column $colNo is full")
                    continue
                }
                val winner = gameRows(toggleTurn, stateList)
                if (winner) {
                    printBoard(rows, cols, stateList)
                    if (toggleTurn == 1) {
                        println("Player $player1 won")
                    } else {
                        println("Player $player2 won")
                    }
                    println("Game over!")
                    break
                }
                toggleTurn = (if (toggleTurn == 1) {
                    2
                } else 1)
                printBoard(rows, cols, stateList)
                var tieGame = true
                for (i in stateList){
                    if (i.contains(0)){
                        tieGame = false
                    }
                }

                if (tieGame) {
                    println("It is a draw")
                    println("Game over!")
                    break
                }

            }
        } else {
            println("Invalid input") // Letters and such break the game, we want only numbers
        }
    }
}
fun printBoard(rows: Int, cols: Int, stateList: MutableList<MutableList<Int>>) { // Takes the parts list I generate and turns it into the board
    for (i in 1..cols) {
        print(" $i")
    }
    for (i in 0 until rows) {
        println()
        for (j in 0 until cols) {
            print("║")
            if (stateList[i][j] == 1){
                print("o")
            } else if (stateList[i][j] == 2){
                print("*")
            } else {
                print(" ")
            }

        }
        print("║")
    }
    println()
    for (i in 1..cols*2) {
        if (i == 1)
            print("╚")

        if (i == cols * 2)
            print("╝ ")
        else {
            if (i % 2 == 0)
                print("╩")
            else
                print("═")
        }
    }
}
fun makeMove(toggleTurn: Int, colNo: Int, stateList: MutableList<MutableList<Int>>, colList: MutableList<Int>)  {

    var j = 0

    for (i in colList.indices.reversed() ) {

        if (colList[i] == 0) {
            stateList[i][colNo-1] = toggleTurn // 1 == "o" and 2 == "*"
            break
        }
        j++
    }
}
fun getColumn(colNo: Int, stateList: MutableList<MutableList<Int>>) : MutableList<Int> {
    val tmpArr = MutableList(stateList.size) { 0 }

    for (i in stateList.indices) tmpArr[i] =  stateList[i][colNo-1]

    return tmpArr
}
fun gameRows(player:Int, stateList: MutableList<MutableList<Int>>) :Boolean {

    // Check for 4 across
    for (row in stateList.indices) {
        for (col in 0 until stateList[0].size - 4) {
            if (stateList[row][col] == player &&
                stateList[row][col + 1] == player &&
                stateList[row][col + 2] == player &&
                stateList[row][col + 3] == player
            ) {
                return true // Win condition met
            }
        }
    }

// Check for 4 up and down
    for (row in 0 until stateList.size - 3) { // - 4
        for (col in 0 until stateList[0].size) {
            if (stateList[row + 0][col] == player &&
                stateList[row + 1][col] == player &&
                stateList[row + 2][col] == player &&
                stateList[row + 3][col] == player) {
                return true // Win condition met
            }
        }
    }
    // Check upward diagonal
    for (row in 2 until stateList.size) {
        for (col in 0 until stateList[0].size - 3) { // - 4
            if (stateList[row][col] == player &&
                stateList[row - 1][col + 1] == player &&
                stateList[row - 2][col + 2] == player &&
                stateList[row - 3][col + 3] == player) {
                return true // Win condition met
            }
        }
    }
    // Check downward diagonal
    for (row in 0 until stateList.size - 3) { // - 4
        for (col in 0 until stateList[0].size - 3) { // - 4
            if (stateList[row][col] == player &&
                stateList[row + 1][col + 1] == player &&
                stateList[row + 2][col + 2] == player &&
                stateList[row + 3][col + 3] == player) {
                return true // Win condition met
            }
        }
    }
    return false // No win conditions met
}



