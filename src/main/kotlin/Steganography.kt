import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import java.awt.Color
fun main() {
    // Menu to access hide and show
    var isOver = false
    while (!isOver) {
        println("Task (hide, show, exit):")
        when (val menuInput = readln()) {
            "hide" -> hide()
            "show" -> show()
            "exit" -> {
                println("Bye!")
                isOver = true
            }
            else -> println("Wrong task: $menuInput")
        }
    }
}
fun hide() {
    // IO
    println("Input image file:")
    val inputFileName = readln()
    println("Output image file:")
    val outputFileName = readln()
    println("Message to hide:")
    val message = readln()
    println("Password:")
    val password = readln()
    val inputImage: BufferedImage
    try {
        inputImage = ImageIO.read(File(inputFileName))
    } catch (e: Exception){
        println("Can't read input file!")
        return
    }
    if (inputImage.height * inputImage.width < message.length * 8 + 24) {
        println("The input image is not large enough to hold this message.")
        return
    } else {
        val newMessage = encodeOrDecodeMessage(password, message) // Encrypts message
        val encodedMessage = newMessage.encodeToByteArray() + byteArrayOf(0,0,3) // To find message in image
        encodedMessage.forEachIndexed { idx, byte ->
            for (bitCount in (Byte.SIZE_BITS - 1) downTo 0) {
                val bit = byte.toInt() shr bitCount and 1

                val index = bitsRead(idx, bitCount)
                val y = index / inputImage.height
                val x = index % inputImage.height

                val color = Color(inputImage.getRGB(x, y))
                val blue = changeLastBit(color.blue, bit)
                // Hiding message in blue pixels
                val colorNew = Color(
                    color.red,
                    color.green,
                    blue
                )
                inputImage.setRGB(x, y, colorNew.rgb)
            }
        }
    }
    println("Message saved in $outputFileName image.")
    ImageIO.write(inputImage, "png", File(outputFileName))
}
private fun bitsRead(idx: Int, bitCount: Int) =
    idx + (idx * (Byte.SIZE_BITS - 1)) + ((Byte.SIZE_BITS - 1) - bitCount)

private fun changeLastBit(byte: Int, bit: Int) = byte shr 1 shl 1 or bit

fun encodeOrDecodeMessage(password: String, message: String, decode: Boolean = false): String {
    var newPassword = ""
    // Validation to ensure password is same length as message
    return if (password.length < message.length) {
        while(newPassword.length < message.length) {
            newPassword += password.repeat(1)
        }
        newPassword = newPassword.dropLast(newPassword.length - message.length)
        if (decode) {
            message xor newPassword
        } else {
            newPassword xor message
        }

    } else {
        newPassword = password.dropLast(password.length - message.length)
        if (decode) {
            message xor newPassword
        } else {
            newPassword xor message
        }
    }
}
fun show () {
    // IO
    println("Input image file:")
    val inputFileName = readln()
    println("Password:")
    val password = readln()
    val bufferedImage: BufferedImage
    try {
        bufferedImage = ImageIO.read(File(inputFileName))
    } catch (e: Exception){
        println("Can't read input file!")
        return
    }
    var message = byteArrayOf()
    var bitBuffer = 0
    var bitCount = Byte.SIZE_BITS - 1
    // Finding message in image
    loop@for (y in 0 until bufferedImage.height) {
        for (x in 0 until bufferedImage.width) {
            val color = Color(bufferedImage.getRGB(x, y))

            val bit = color.blue and 1
            bitBuffer += bit shl bitCount
            bitCount--
            if (bitCount < 0) {
                message += bitBuffer.toByte()
                bitBuffer = 0
                bitCount = Byte.SIZE_BITS - 1
            }
            if(message.takeLast(2).containsAll(listOf(0,0,3))) {
                break@loop // Message found, stop searching
            }
        }
    }
    val fullMessage = message.dropLast(3).toByteArray().toString(Charsets.UTF_8) // Remove locator
    val newFullMessage = encodeOrDecodeMessage(password, fullMessage, true) // Decode
    println("Message: $newFullMessage")
}
// XOR algorithm
infix fun String.xor(that: String) = mapIndexed { index, c ->
    that[index].code.xor(c.code)
}.joinToString(separator = "") {
    it.toChar().toString()
}