import java.io.File
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.awt.Color

fun main() {
    menu()
}
fun menu() {
    var isOver = false
    while (!isOver) {
        println("Task (hide, show, exit):")
        when (val menuInput = readln()) {
            "hide" -> hide()
            "show" -> println("Obtaining message from image.")
            "exit" -> {
                println("Bye!")
                isOver = true
            }
            else -> println("Wrong task: $menuInput")
        }
    }
}
fun hide() {
    println("Input image file:")
    val inputFileName = readln()
    println("Output image file:")
    val outputFileName = readln()
    val outputImage: BufferedImage
    val inputImage: BufferedImage
    val bufferedImage: BufferedImage
    try {
        bufferedImage = ImageIO.read(File(inputFileName))
    } catch (e: Exception){
        println("Can't read input file!")
        return
    }
    println("Input Image: $inputFileName")
    println("Output Image: $outputFileName")
    inputImage = bufferedImage
    outputImage = bufferedImage
    for (i in 0 until bufferedImage.width) {
        for (j in 0 until bufferedImage.height) {
            val color = Color(inputImage.getRGB(i, j))
            val rgb = Color(
                setLeastSignificantBitToOne(color.red),
                setLeastSignificantBitToOne(color.green),
                setLeastSignificantBitToOne(color.blue)
            ).rgb
            outputImage.setRGB(i, j, rgb)
        }
    }
    println("Image $outputFileName is saved.")
    ImageIO.write(outputImage, "png", File(outputFileName))
}
fun setLeastSignificantBitToOne (pixel: Int): Int {
    return if (pixel % 2 == 0) pixel + 1 else pixel
}