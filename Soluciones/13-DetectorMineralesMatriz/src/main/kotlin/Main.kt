import kotlin.random.Random

const val SIZE = 5
const val MAX_VALUE = 20
const val PROB_MINERAL = 50
const val MAX_TIME = 30
const val PROB_TAKE_MINERAL = 50
const val PAUSE_TIME = 1000L // (1 segundo)
const val NUM_MINERALS_TAKEN = 2
const val PROB_DECISION = 30

fun main(args: Array<String>) {
    println("Detector de Minerales")
    val mapMinerales = crearMapa(SIZE, MAX_VALUE, PROB_MINERAL)
    printMapMinerales(mapMinerales)
    var time = 1
    var cantidadMineral = 0
    var direccionBusqueda = getRandomDirection()
    var posicionActual = getInitialPosition()
    do {
        println()
        println("Tiempo: $time")
        println("Cantidad de Mineral: $cantidadMineral")
        println(getPosicionActual(posicionActual))
        println(getDireccionBusqueda(direccionBusqueda))
        printTablero(posicionActual)

        // ACCION
        cantidadMineral += buscarMineral(mapMinerales, posicionActual)

        if (time % 2 == 0) {
            direccionBusqueda = getAndThinckNewDirection(direccionBusqueda)
        }

        // Si he llegado al final del mapa, cambio de direccion
        while (isEndMap(mapMinerales, posicionActual, direccionBusqueda)) {
            direccionBusqueda = getRandomDirection()
        }

        // Muevo la posicion actual
        posicionActual = getNewPosicion(posicionActual, direccionBusqueda)

        Thread.sleep(PAUSE_TIME)
        time++
    } while (hayMineral(mapMinerales) && time <= MAX_TIME)

    println()
    println("Fin de la Exploracion")
    println("Tiempo: $time")
    println("Cantidad de Mineral: $cantidadMineral")
    println(getPosicionActual(posicionActual))
    printTablero(posicionActual)
    printMapMinerales(mapMinerales)
}

fun getInitialPosition(): IntArray {
    val posicionFila = (0..<SIZE).random()
    val posicionColumna = (0..<SIZE).random()
    return intArrayOf(posicionFila, posicionColumna)
}

fun getRandomDirection(): IntArray {
    var nuevaDireccionFila = 0
    var nuevaDireccionColumna = 0
    do {
        nuevaDireccionFila = intArrayOf(-1, 0, 1).random()
        nuevaDireccionColumna = (-1..1).random()
    } while (nuevaDireccionFila == 0 && nuevaDireccionColumna == 0)
    return intArrayOf(nuevaDireccionFila, nuevaDireccionColumna)
}

fun getNewPosicion(posicionActual: IntArray, direccionBusqueda: IntArray): IntArray {
    return intArrayOf(
        posicionActual[0] + direccionBusqueda[0],
        posicionActual[1] + direccionBusqueda[1]
    )
}

private fun getAndThinckNewDirection(direccionBusqueda: IntArray): IntArray {
    if (Random.nextInt(100) < PROB_DECISION) {
        println("He decidido cambiar de Direccion, me lo pienso...")
        val direction = getRandomDirection()
        return if (direction[0] == direccionBusqueda[0] && direction[1] == direccionBusqueda[1]) {
            println("...No cambio de Direccion")
            direccionBusqueda
        } else {
            println("...Cambio de Direccion")
            direction
        }
    }
    return direccionBusqueda
}

fun getDireccionBusqueda(direccionBusqueda: IntArray): String {
    // ("Direccion de Busqueda: ${direccionBusqueda.joinToString()}")
    val direccion = StringBuilder("Direccion de Busqueda: ")
    when (direccionBusqueda[0]) {
        -1 -> direccion.append("N")
        1 -> direccion.append("S")
    }
    when (direccionBusqueda[1]) {
        -1 -> direccion.append("W")
        1 -> direccion.append("E")
    }
    return direccion.toString()
}

fun getPosicionActual(posicionActual: IntArray): String {
    return "Posicion Actual: f: ${posicionActual[0] + 1}, c: ${posicionActual[1] + 1}"
}

fun printTablero(posicionActual: IntArray) {
    for (i in 0..<SIZE) {
        for (j in 0..<SIZE) {
            if (i == posicionActual[0] && j == posicionActual[1]) {
                print("[X]")
            } else {
                print("[ ]")
            }
        }
        println()
    }
    println()
}

fun isEndMap(mapMinerales: Array<IntArray>, posicionActual: IntArray, direccionBusqueda: IntArray): Boolean {
    // Estamos en el extremo del mapa y vamos a salirnos
    // Si estamos en la posicion 0 y vamos hacia el norte
    // Si estamos en la posicion SIZE - 1 y vamos hacia el sur
    // Si estamos en la posicion 0 y vamos hacia el oeste
    // Si estamos en la posicion SIZE - 1 y vamos hacia el este
    return posicionActual[0] == 0 && direccionBusqueda[0] == -1
            || posicionActual[0] == SIZE - 1 && direccionBusqueda[0] == 1
            || posicionActual[1] == 0 && direccionBusqueda[1] == -1
            || posicionActual[1] == SIZE - 1 && direccionBusqueda[1] == 1
}

fun buscarMineral(mapMinerales: Array<IntArray>, posicionActual: IntArray): Int {
    return if (mapMinerales[posicionActual[0]][posicionActual[1]] > 0) {
        println("Mineral encontrado en la posicion f:${posicionActual[0] + 1}, c:${posicionActual[1] + 1}")
        if (Random.nextInt(100) < PROB_TAKE_MINERAL) {
            println("Mineral tomado, cantidad: $NUM_MINERALS_TAKEN")
            mapMinerales[posicionActual[0]][posicionActual[1]] -= NUM_MINERALS_TAKEN
            NUM_MINERALS_TAKEN
        } else {
            println("Mineral no tomado")
            0
        }
    } else {
        println("No hay mineral en la posicion f:${posicionActual[0] + 1}, c:${posicionActual[1] + 1}")
        0
    }
}

fun crearMapa(size: Int = SIZE, maxValue: Int = MAX_VALUE, probMineral: Int = PROB_MINERAL): Array<IntArray> {
    val map = Array(size) { IntArray(size) { 0 } }
    for (i in map.indices) {
        for (j in map.indices) {
            map[i][j] = Random.nextInt(maxValue)
        }
    }
    return map
}

fun printMapMinerales(map: Array<IntArray>) {
    // imprimo un mapa encima donde [ ] libre y [X] soy yo
    for (i in map.indices) {
        for (j in map.indices) {
            if (map[i][j] > 0) {
                print("[${map[i][j]}]")
            } else {
                print("[ ]")
            }
        }
        println()
    }
    println()
}

fun hayMineral(map: Array<IntArray>): Boolean {
    for (i in map.indices) {
        for (j in map.indices) {
            if (map[i][j] > 0) {
                return true
            }
        }
    }
    return false
}
