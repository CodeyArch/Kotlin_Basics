import kotlin.random.Random

fun main() {
    val game = MineSweeper()
    game.setGameBoard()
    while(!game.gameEnd) {
        game.playGame()
    }
}
class Hidden {
    var default: Int = 0 // To store the values
    var opened: Boolean = false // To make numbers visible to the player
    var hasMark: Boolean = false // To prevent marks overwriting my values
}
class MineSweeper {
    private val rows = 9 // Easier explicit use, could be used to generate larger boards
    private val cols = 9// Easier explicit use, could be used to generate larger boards
    private val listy = MutableList(rows) { MutableList(cols) {Hidden()} } // Creating a "state list" of sorts
    private var countMines = 0
    private var correctMarks = 0
    var gameEnd = false
    fun setGameBoard() {
        println("How many mines do you want on the field? ")
        val mines = readLine()?.toInt() ?: 0
        while(countMines < mines) { // runs until mines maxed
            val x = Random.nextInt(9) // selects a random row
            val y = Random.nextInt( 9) // selects a random column
            if(listy[x][y].default != 15 ) { // changes it from not a mine to a mine
                listy[x][y].default = 15 // Mines are set to 15 due to it being impossible for 15 mines to be near a block, useful later
                countMines++ // Increments to end the loop eventually
            }
        }
        drawBoard() // Prints the board
        checkMinesNear() // Places the hints but leaves them invisible to the player for now
    }
    private fun checkMinesNear() {
        for(x in listy.indices) {
            for(y in listy[x].indices) {
                var minesNear = 0
                if(listy[x][y].default != 15) {
                    if (y < 8 && listy[x][y + 1].default == 15) minesNear++ // Checks the right
                    if (y > 0 && listy[x][y - 1].default == 15) minesNear++ // Checks the left
                    if (x < 8 && listy[x + 1][y].default == 15) minesNear++ // Checks the bottom
                    if (x > 0 && listy[x - 1][y].default == 15) minesNear++ // Checks the top
                    if (x > 0 && y < 8 && listy[x - 1][y + 1].default == 15) minesNear++ // Checks the top right
                    if (x > 0 && y > 0 && listy[x - 1][y - 1].default == 15) minesNear++ // Checks the top left
                    if (x < 8 && y < 8 && listy[x + 1][y + 1].default == 15) minesNear++ // Checks the bottom right
                    if (x < 8 && y > 0 && listy[x + 1][y - 1].default == 15) minesNear++ // Checks the bottom left
                    if(minesNear > 0) listy[x][y].default = minesNear // Places the number in the state list
                }
            }
        }
    }
    private fun floodFill(x: Int, y: Int, free: Int) {
        if(x > 8 || x < 0) {
            return
        } // Prevents index out of bounds errors
        if(y > 8 || y < 0) {
            return
        } // Prevents index out of bounds errors
        if(listy[x][y].default in 1..8 || listy[x][y].default == 25) {
            return
        } // Acts as the walls to bounce away from
        listy[x][y].default = free // Changes the value to our free value
        openNumbers(x, y) // Open numbers in an 8 by 8 area around space
        floodFill(x + 1, y, free)
        floodFill(x -1, y, free)
        floodFill(x,y + 1, free)
        floodFill(x,y - 1, free)
        return
    }
    private fun openNumbers(x: Int, y: Int) {
        if(x > 8 || x < 0) {
            return
        } // Prevents index out of bounds errors
        if(y > 8 || y < 0) {
            return
        } // Prevents index out of bounds errors
        if (y < 8 && listy[x][y + 1].default in 1..8) listy[x][y + 1].opened = true // Checks the right
        if (y > 0 && listy[x][y - 1].default in 1..8) listy[x][y - 1].opened = true // Checks the left
        if (x < 8 && listy[x + 1][y].default in 1..8) listy[x + 1][y].opened = true // Checks the bottom
        if (x > 0 && listy[x - 1][y].default in 1..8) listy[x - 1][y].opened = true // Checks the top
        if (x > 0 && y < 8 && listy[x - 1][y + 1].default in 1..8) listy[x - 1][y + 1].opened = true // Checks the top right
        if (x > 0 && y > 0 && listy[x - 1][y - 1].default in 1..8) listy[x - 1][y - 1].opened = true // Checks the top left
        if (x < 8 && y < 8 && listy[x + 1][y + 1].default in 1..8) listy[x + 1][y + 1].opened = true // Checks the bottom right
        if (x < 8 && y > 0 && listy[x + 1][y - 1].default in 1..8) listy[x + 1][y - 1].opened = true // Checks the bottom left
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
                if(j.hasMark && j.default != 25 && j.default !in 1..8) { // Real minesweeper doesn't let you mark numbers or free
                    print("*")
                } else {
                    when (j.default) {
                        15 -> {
                            print(".")
                        }
                        16 -> {
                            print("x")
                        }
                        0 -> {
                            print(".")
                        }
                        25 -> {
                            print("/")
                        }
                        else -> {
                            if(j.opened) {
                                print("${j.default}")
                            } else {
                                if(j.hasMark) print("*")
                                else print(".")
                            }
                        }
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
            println("Set/unset mine marks or claim a cell as free:")
            val (xinput, yinput, claim) = readLine()?.split(" ") ?: throw IllegalArgumentException("Got Invalid data, wanted positions")
            val x = yinput.toInt() - 1 // I can format it the way I prefer this way
            val y = xinput.toInt() - 1
            if(claim == "mine") {
                if(listy[x][y].hasMark) {
                    listy[x][y].hasMark = false
                    if(listy[x][y].default == 15) {
                        correctMarks--
                        break
                    }
                    break
                } else {
                    listy[x][y].hasMark = true
                    if(listy[x][y].default == 15) {
                        correctMarks++
                        break
                    }
                    break
                }
            } else if(claim == "free") {
                if(listy[x][y].default == 15) {
                    println("You stepped on a mine and failed!")
                    for(i in listy.indices) {
                        for(j in listy[i].indices) {
                            if(listy[i][j].default == 15) {
                                listy[i][j].default = 16 // Makes the mines visible
                            }
                        }
                    }
                    gameEnd = true
                    break
                } else if(listy[x][y].default in 1..8) {
                    listy[x][y].opened = true
                    break
                } else {
                    floodFill(x, y, 25) // Runs the flood fill algorithm to keep filling spaces
                    break
                }
            }
        }
        drawBoard()
        if(correctMarks == countMines) {
            println("Congratulations! You found all the mines!")
            gameEnd = true // Ends the game
        }
    }
}