const val NUM_RONDAS = 3
const val NUM_LANZAMIENTOS = 5

fun main() {

    val puntuacionLlul = IntArray(NUM_RONDAS) // { 0 }
    val puntuacionGasol = IntArray(NUM_RONDAS) // { 0 }

    juegoLanzamientos(puntuacionLlul, puntuacionGasol)

    imprimirResultados(puntuacionLlul, puntuacionGasol)
}

private fun juegoLanzamientos(
    puntuacionLlul: IntArray,
    puntuacionGasol: IntArray
) {
    // repetimos el juego NUM_RONDAS veces
    for (i in 0..<NUM_RONDAS) {
        println("Ronda ${i + 1}")
        // repetimos el lanzamiento NUM_LANZAMIENTOS veces
        for (j in 0..<NUM_LANZAMIENTOS) {
            println("Lanzamiento Llul: ${j + 1}")
            // Llul lanza
            if (j < NUM_LANZAMIENTOS - 1) {
                puntuacionLlul[i] += lanzamiento(probabilidad = 0.5, puntuacion = 1, lanzamiento = j)
            } else {
                puntuacionLlul[i] += lanzamiento(lanzamiento = j, probabilidad = 0.5, puntuacion = 2)
            }
            // Gasol lanza
            println("Lanzamiento Gasol: ${j + 1}")
            // Llul lanza
            if (j < NUM_LANZAMIENTOS - 1) {
                puntuacionGasol[i] += lanzamiento(lanzamiento = j, probabilidad = 0.33, puntuacion = 1)
            } else {
                puntuacionGasol[i] += lanzamiento(probabilidad = 0.33, puntuacion = 2, lanzamiento = j)
            }
        }
    }
}

fun lanzamiento(lanzamiento: Int, probabilidad: Double, puntuacion: Int): Int {
    val resultado = Math.random()
    return if (resultado < probabilidad) {
        println("Lanzamiento ${lanzamiento + 1}: acierto y tiene $puntuacion puntos")
        puntuacion
    } else {
        println("Lanzamiento ${lanzamiento + 1}: fallo")
        0
    }
}

fun imprimirResultados(puntuacionLlul: IntArray, puntuacionGasol: IntArray) {
    var rondasGanadasLlul = 0
    var rondasGanadasGasol = 0

    // imprimimos los resultados de cada jugador
    println("Llul: ${puntuacionLlul.contentToString()} puntos")
    println("Gasol: ${puntuacionGasol.contentToString()} puntos")

    // analizamos cada ronda
    for (i in puntuacionLlul.indices) {
        println("Resultados de la ronda ${i + 1}")
        // si la puntuación de Llul es mayor que la de Gasol
        if (puntuacionLlul[i] > puntuacionGasol[i]) {
            println("Gana Llul en la ronda ${i + 1} con ${puntuacionLlul[i]} puntos")
        } else if (puntuacionLlul[i] < puntuacionGasol[i]) {
            println("Gana Gasol en la ronda ${i + 1} con ${puntuacionGasol[i]} puntos")
        } else {
            println("Empate en la ronda ${i + 1} con ${puntuacionLlul[i]} puntos")
        }
    }

    // ¿Quién ha ganado más rondas?
    for (i in puntuacionLlul.indices) {
        if (puntuacionLlul[i] > puntuacionGasol[i]) {
            rondasGanadasLlul++
        } else if (puntuacionLlul[i] < puntuacionGasol[i]) {
            rondasGanadasGasol++
        }
    }

    println("Resultados finales")

    // ¿Quién ha ganado? when para no repetir el if como antes
    /*
    // necesitamos los puntos totales de cada jugador
     val puntosLull = sumaPuntosTotal(puntuacionLlul)
     val puntosGasol = sumaPuntosTotal(puntuacionGasol)
     when {
         puntosLull > puntosGasol -> println("Gana Llul con $puntosLull puntos")
         puntosLull < puntosGasol -> println("Gana Gasol con $puntosGasol puntos")
         else -> println("Empate con $puntosLull puntos")
     }*/

    // Quien ha ganado por rondas
    when {
        rondasGanadasLlul > rondasGanadasGasol -> println("Gana Llul con $rondasGanadasLlul rondas")
        rondasGanadasLlul < rondasGanadasGasol -> println("Gana Gasol con $rondasGanadasGasol rondas")
        else -> println("Empate con $rondasGanadasLlul rondas")
    }
}

fun sumaPuntosTotal(puntuacion: IntArray): Int {
    var suma = 0
    for (i in puntuacion.indices) {
        suma += puntuacion[i]
    }
    return suma
}
