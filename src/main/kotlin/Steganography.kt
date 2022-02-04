fun main() {
    menu()
}
fun menu() {
    var isOver = false
    while (!isOver) {
        println("Task (hide, show, exit):")
        when (val menuInput = readln()) {
            "hide" -> println("Hiding message in image.")
            "show" -> println("Obtaining message from image.")
            "exit" -> {
                println("Bye!")
                isOver = true
            }
            else -> println("Wrong task: $menuInput")
        }
    }
}