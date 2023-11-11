import kotlin.random.Random

const val SIZE = 10
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
    var direccionBusqueda = 1
    var posicionActual = 0
    do {
        println()
        println("Tiempo: $time")
        println("Cantidad de Mineral: $cantidadMineral")
        println("Posicion Actual: ${posicionActual + 1}")
        println("Direccion de Busqueda: ${if (direccionBusqueda == 1) "Derecha" else "Izquierda"}")
        printTablero(posicionActual)

        // ACCION
        cantidadMineral += buscarMineral(mapMinerales, posicionActual)

        if (time % 2 == 0) {
            if (Random.nextInt(100) < PROB_DECISION) {
                println("He decidido cambiar de Direccion, me lo pienso...")
                val nuevaDireccion = Random.nextInt(2)
                //val otra = intArrayOf(-1, 1)
                var nuevaDireccionBusqueda = if (nuevaDireccion == 0) -1 else 1
                if (nuevaDireccionBusqueda == direccionBusqueda) {
                    println("...No cambio de Direccion")
                } else {
                    println("...Cambio de Direccion")
                    direccionBusqueda = nuevaDireccionBusqueda
                }
            }
        }

        // Si he llegado al final del mapa, cambio de direccion
        if (isEndMap(mapMinerales, posicionActual, direccionBusqueda)) {
            direccionBusqueda *= -1
        }

        // Muevo la posicion actual
        posicionActual += direccionBusqueda

        Thread.sleep(PAUSE_TIME)
        time++
    } while (hayMineral(mapMinerales) && time <= MAX_TIME)

    println()
    println("Fin de la Exploracion")
    println("Tiempo: $time")
    println("Cantidad de Mineral: $cantidadMineral")
    println("Posicion Actual: ${posicionActual + 1}")
    printTablero(posicionActual)
    printMapMinerales(mapMinerales)
}

fun printTablero(posicionActual: Int) {
    for (i in 0..<SIZE) {
        if (i == posicionActual) {
            print("[X]")
        } else {
            print("[ ]")
        }
    }
    println()
}

fun isEndMap(mapMinerales: IntArray, posicionActual: Int, direccionBusqueda: Int): Boolean {
    // Estamos en el extremo del mapa y vamos en la direccion de la pared
    // al principio y vamos a la izquierda o al final y vamos a la derecha
    return (posicionActual == 0 && direccionBusqueda == -1) || (posicionActual == SIZE - 1 && direccionBusqueda == 1)
}

fun buscarMineral(mapMinerales: IntArray, posicionActual: Int): Int {
    return if (mapMinerales[posicionActual] >= NUM_MINERALS_TAKEN) {
        println("Mineral encontrado en la posicion ${posicionActual + 1}")
        if (Random.nextInt(100) < PROB_TAKE_MINERAL) {
            println("Mineral tomado")
            mapMinerales[posicionActual] -= NUM_MINERALS_TAKEN
            NUM_MINERALS_TAKEN
        } else {
            println("Mineral no tomado")
            0
        }
    } else {
        println("No hay mineral en la posicion ${posicionActual + 1}")
        0
    }
}

fun crearMapa(size: Int = SIZE, maxValue: Int = MAX_VALUE, probMineral: Int = PROB_MINERAL): IntArray {
    val map = IntArray(size)
    for (i in map.indices) {
        if (Random.nextInt(100) < probMineral) {
            map[i] = Random.nextInt(maxValue)
        }
    }
    return map
}

fun printMapMinerales(map: IntArray) {
    // imprimo un mapa encima donde [ ] libre y [X] soy yo
    for (i in map.indices) {
        print("[${map[i]}]")
    }
    println()
}

fun hayMineral(map: IntArray): Boolean {
    for (i in map.indices) {
        if (map[i] > 0) {
            return true
        }
    }
    return false
}
