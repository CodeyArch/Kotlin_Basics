import kotlin.random.Random

fun main() {
    setGameBoard()
}
fun setGameBoard(dftRows: Int = 9, dftCols: Int = 9) {
    val rows = 9 // Easier explicit use, later will allow us to generate larger boards
    val cols = 9// Easier explicit use, later will allow us to generate larger boards
    var listy = MutableList(rows) { MutableList(cols) {0} } // Creating a "statelist" of sorts
    // println(listy)
    var countMines = 0
    println("How many mines do you want on the field? ")
    val mines = readLine()?.toInt() ?: 0
    while(countMines < mines) { // runs until mines maxed
        var x = Random.nextInt(1, 9) // selects a random row
        var y = Random.nextInt(1, 9) // selects a random column
        if(listy[x][y] != 1 ) { // changes it from not a mine to a mine
            listy[x][y] = 1
            countMines++ // Increments to end the loop eventually
        }
    }
    for(i in listy) { // Printing a visual board
        for(j in i) {
            if(j == 1) {
                print("X")
            } else {
                print(".")
            }
        }
        println()
    }
}