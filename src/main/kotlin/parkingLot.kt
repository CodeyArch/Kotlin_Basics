object Parking {
    private var spots = MutableList(0) { "" }
    fun create(size: Int) {
        // Sets the size of the car park
        spots = MutableList(size) { "" }
        println("Created a parking lot with $size spots.")
    }
    fun park(colour: String, reg: String) {
        // Puts a car in the first spot available
        if (spots.size == 0) {
            println("Sorry, a parking lot has not been created.")
            return
        }
        for (i in spots.indices) {
            if (spots[i].isEmpty()) {
                val carInfo = listOf(reg, colour).joinToString().replace(",", "")
                spots[i] = carInfo
                println("$colour car parked in spot ${i + 1}.")
                return
            }
        }
        println("Sorry, the parking lot is full.")
    }
    fun leave(spot: Int) {
        // Removes a car from the car park
        if (spots.size == 0) {
            println("Sorry, a parking lot has not been created.")
            return
        }
        if ((spot - 1) in spots.indices) {
            spots[spot - 1] = ""
            println("Spot $spot is free.")
        }
    }
    fun status() {
        // Prints the entire car park and positions of cars
        if (spots.size == 0) {
            println("Sorry, a parking lot has not been created.")
            return
        }
        var emptyCount = 0
        for (i in spots.indices) {
            if (spots[i].isNotEmpty()) {
                println("${i + 1} ${spots[i]}")
            } else if (spots[i].isEmpty()) {
                emptyCount++
            }
        }
        if (emptyCount == spots.size) {
            println("Parking lot is empty.")
        }
    }
    fun spotByValue(value: String, regOrColour: Boolean = true) {
        // Finds all cars by colour OR reg
        if (spots.size == 0) {
            println("Sorry, a parking lot has not been created.")
            return
        }
        val carColourMatchSpots = mutableListOf<Int>()
        val carRegMatchSpots = mutableListOf<Int>()
        for (i in spots.indices) {
            if (spots[i].isNotEmpty()) {
                val (carReg, carColour) = spots[i].split(" ")
                if (value.lowercase() == carColour.lowercase()) {
                    carColourMatchSpots.add(i + 1)
                } else if (value.lowercase() == carReg.lowercase()) {
                    carRegMatchSpots.add(i + 1)
                }
            }
        }
        if (carColourMatchSpots.isNotEmpty() && regOrColour) {
            println(carColourMatchSpots.joinToString())
        } else if(carColourMatchSpots.isEmpty() && regOrColour) {
            println("No cars with color $value were found.")
        }
        if (carRegMatchSpots.isNotEmpty() && !regOrColour) {
            println(carRegMatchSpots.joinToString())
        } else if(carRegMatchSpots.isEmpty() && !regOrColour) {
            println("No cars with registration number $value were found.")
        }
    }
    fun regByColor(colour: String) {
        // Function to find all registered cars of a certain colour
        if (spots.size == 0) {
            println("Sorry, a parking lot has not been created.")
            return
        }
        val registeredCarsByColour = mutableListOf<String>()
        for (i in spots.indices) {
            if (spots[i].isNotEmpty()) {
                val (carReg, carColour) = spots[i].split(" ")
                if (colour.lowercase() == carColour.lowercase()) {
                    registeredCarsByColour.add(carReg)
                }
            }
        }
        if (registeredCarsByColour.isEmpty()) {
            println("No cars with color $colour were found.")
        } else {
            println(registeredCarsByColour.joinToString())
        }
    }
}

fun main() {
    // No input validation added here as it was not a requirement for the project
    var input: List<String> = emptyList()
    while(!input.contains("exit")) {
        input = readln().split(" ")
        when(input[0]) {
            "park" -> Parking.park(input[2], input[1])
            "leave" -> Parking.leave(input[1].toInt())
            "status" -> Parking.status()
            "create" -> Parking.create(input[1].toInt())
            "spot_by_color" -> Parking.spotByValue(input[1]) // Project required it to say "Color" instead of "Colour" :(
            "spot_by_reg" -> Parking.spotByValue(input[1], false)
            "reg_by_color" -> Parking.regByColor(input[1]) // Project required it to say "Color" instead of "Colour" :(
        }
    }
}
