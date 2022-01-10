import kotlin.random.Random

fun main() {
    val game = MineSweeper()
    game.setGameBoard()
    while(!game.gameEnd) {
        game.playGame()
    }
}
class MineSweeper {
    private val rows = 9 // Easier explicit use, later will allow us to generate larger boards
    private val cols = 9// Easier explicit use, later will allow us to generate larger boards
    private val listy = MutableList(rows) { MutableList(cols) {0} } // Creating a "state list" of sorts
    private var countMines = 0
    private var correctMarks = 0
    var gameEnd = false
    fun setGameBoard() {
        println("How many mines do you want on the field? ")
        val mines = readLine()?.toInt() ?: 0
        while(countMines < mines) { // runs until mines maxed
            val x = Random.nextInt(1, 9) // selects a random row
            val y = Random.nextInt(1, 9) // selects a random column
            if(listy[x][y] != 15 ) { // changes it from not a mine to a mine
                listy[x][y] = 15 // Mines are set to 15 due to it being impossible for 15 mines to be near a block, useful later
                countMines++ // Increments to end the loop eventually
            }
        }
        for(x in listy.indices) {
            for(y in listy[x].indices) {
                var minesNear = 0
                if(listy[x][y] != 15) {
                    if (y < 8 && listy[x][y + 1] == 15) minesNear++ // Checks the right
                    if (y > 0 && listy[x][y - 1] == 15) minesNear++ // Checks the left
                    if (x < 8 && listy[x + 1][y] == 15) minesNear++ // Checks the bottom
                    if (x > 0 && listy[x - 1][y] == 15) minesNear++ // Checks the top
                    if (x > 0 && y < 8 && listy[x - 1][y + 1] == 15) minesNear++ // Checks the top right
                    if (x > 0 && y > 0 && listy[x - 1][y - 1] == 15) minesNear++ // Checks the top left
                    if (x < 8 && y < 8 && listy[x + 1][y + 1] == 15) minesNear++ // Checks the bottom right
                    if (x < 8 && y > 0 && listy[x + 1][y - 1] == 15) minesNear++ // Checks the bottom left
                    if(minesNear > 0) listy[x][y] = minesNear
                }
            }
        }
        drawBoard() // Prints the board
    }
    private fun drawBoard() {
        print(" |")
        for (i in 1..9){
            print(i)
        }
        println("|")
        print("-|")
        for (i in 1..9){
            print("-")
        }
        println("|")
        var rowCount = 0
        for(i in listy) { // Printing a visual board
            rowCount++
            print(rowCount)
            print("|")
            for(j in i) {
                when (j) {
                    15 -> {
                        print(".")
                    }
                    0 -> {
                        print(".")
                    }
                    20 -> {
                        print("*")
                    }
                    else -> {
                        print("$j")
                    }
                }
            }
            println("|")
        }
        print("-|")
        for (i in 1..9){
            print("-")
        }
        println("|")
    }
    fun playGame() {
        while(true) {
            println("Set/delete mine marks (x and y coordinates):")
            val (xinput, yinput) = readLine()?.split(" ") ?: throw IllegalArgumentException("Got Invalid data, wanted positions")
            val x = yinput.toInt() - 1 // I can format it the way I prefer this way
            val y = xinput.toInt() - 1

            if(listy[x][y] == 15) {
                listy[x][y] = 20 // I chose 20 as my marked number because I like the number, no other reason
                correctMarks++
                break
            } else if (listy[x][y] in 1..8) {
                println("There is a number here!")
            } else if(listy[x][y] == 20) {
                listy[x][y] = 0
                break
            } else {
                listy[x][y] = 20 // Sets it to marked
                break
            }
        }
        drawBoard()
        if(correctMarks == countMines) {
            println("Congratulations! You found all the mines!")
            gameEnd = true // Ends the game
        }
    }
}