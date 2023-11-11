package mosca

import GOLPEO_ACERTADO
import GOLPEO_CASI
import GOLPEO_FALLADO
import MOSCA
import NUM_INTENTOS_DEFAULT
import TAM_DEFAULT
import VACIO
import com.github.ajalt.mordant.rendering.TextColors.*
import terminal
import kotlin.system.exitProcess

/**
 * Juega a cazar la mosca en un vector
 * @param vector Vector de enteros
 * @param numIntentos Número de intentos para cazar la mosca
 * @return true si se ha cazado la mosca, false si no se ha cazado
 */
fun jugarCazarMosca(vector: IntArray, numIntentos: Int): Boolean {
    var intentos = 0
    var moscaMuerta = false
    do {
        // Aumentamos el número de intentos
        intentos++
        // Pedimos la posición valida paara analizar el vector
        val posicion = pedirPosicionValida(vector.size)
        // analizamos la posición
        val resultado = analizarGolpeo(vector, posicion)
        when (resultado) {
            GOLPEO_ACERTADO -> {
                terminal.println(green("☠️ ¡TE LA HAS CARGADO! has acertado en el intento $intentos"))
                moscaMuerta = true
            }

            GOLPEO_CASI -> {
                terminal.println(yellow("🫤 ¡CASI! Has estado cerca en el intento $intentos"))
                terminal.println(yellow("🪰 La mosca revoltea y cambia de posición"))
                sortearPosicionMosca(vector)
                // imprimirVector(vector) // TODO Quitar en producción
            }

            GOLPEO_FALLADO -> {
                terminal.println(red("Has fallado en el intento $intentos"))
            }
        }

    } while (!moscaMuerta && intentos < numIntentos)
    return moscaMuerta
}

/**
 * Analiza el golpeo de la mosca en el vector
 * @param vector Vector de enteros
 * @param posicion Posición del vector a analizar
 * @return GOLPEO_ACERTADO si la mosca está en esa posición, GOLPEO_FALLADO si no está en esa posición
 */
fun analizarGolpeo(vector: IntArray, posicion: Int): Int {
    // Si la posición es MOSCA, la mosca está en esa posición, devolvemos GOLPEO_ACERTADO
    if (vector[posicion] == MOSCA) {
        return GOLPEO_ACERTADO
    }
    // Si la posición anterior o posterior es MOSCA, la mosca está en esa posición, devolvemos GOLPEO_CASI
    if (posicion > 0 && vector[posicion - 1] == MOSCA) {
        return GOLPEO_CASI
    }
    if (posicion < vector.size - 1 && vector[posicion + 1] == MOSCA) {
        return GOLPEO_CASI
    }
    // Si no, la mosca no está en esa posición, devolvemos GOLPEO_FALLADO
    return GOLPEO_FALLADO
}

/**
 * Pide una posición válida para el vector
 * @param size Tamaño del vector
 * @return Posición válida del vector
 */
fun pedirPosicionValida(size: Int): Int {
    var inputIsOk = false
    val inputRegex = "^[1-$size]$".toRegex()
    do {
        print("Introduce una posición válida: ")
        val input = readln()
        if (input.matches(inputRegex)) {
            inputIsOk = true
            return input.toInt() - 1 // Restamos 1 para que la posición sea 0..size-1
        } else {
            terminal.println(red("La posición no es válida. Inténtalo de nuevo con valores entre 1 y $size"))
        }
    } while (!inputIsOk)
    return -1
}

/**
 * Imprime un vector de enteros
 * @param vector Vector de enteros
 */
fun imprimirVector(vector: IntArray) {
    for (i in vector.indices) {
        if (vector[i] == MOSCA) {
            print("[🪰]")
        } else {
            print("[ ]")
        }
    }
    println()
}

/**
 * Sortea la posición de la mosca en el vector
 * @param vector Vector de enteros
 */
fun sortearPosicionMosca(vector: IntArray) {
    // Ponemos todo al valor por defecto
    for (i in vector.indices) {
        vector[i] = VACIO
    }
    // Sorteamos la posición de la mosca
    val posicionMosca = vector.indices.random()
    vector[posicionMosca] = MOSCA
}

/**
 * Analiza los argumentos de entrada y devuelve un array con los valores
 * @param args Array de argumentos de entrada
 * @return Array de enteros con los valores de los argumentos para el juego con tam y numIntentos
 */
fun analizarArgumentos(args: Array<String>): IntArray {
    if (args.size != 4) {
        terminal.println(red("Error en los argumentos"))
        terminal.println("Uso: java -jar mosca.jar -tam 8 -numIntentos 5")
        exitProcess(1)
    }
    // Hay que analizar los argumentos
    // Buscamos que dentro del argumentos exista -tam y el siguiente es su valor
    // Si no existe damos un warning y ponemos el valor por defecto
    // Si existe y el valor no es un entero damos un warning y ponemos el valor por defecto
    // Si existe y el valor es un entero lo guardamos
    val existeTam = buscarEnArgs(args, "-tam")
    var tam: Int = TAM_DEFAULT
    if (existeTam == -1) {
        terminal.println(yellow("No existe el argumento -tam"))
        terminal.println("Uso: java -jar mosca.jar -tam 8 -numIntentos 5")
        exitProcess(1)
    }
    if (args[existeTam + 1].toIntOrNull() == null) {
        terminal.println(yellow("El valor de -tam no es un entero"))
        println("usando el valor por defecto $TAM_DEFAULT")
    } else {
        tam = args[existeTam + 1].toInt()
    }
    // Buscamos que dentro del argumentos exista -numIntentos y el siguiente es su valor
    // Si no existe damos un warning y ponemos el valor por defecto
    // Si existe y el valor no es un entero damos un warning y ponemos el valor por defecto
    // Si existe y el valor es un entero lo guardamos
    val existeNumIntentos = buscarEnArgs(args, "-numIntentos")
    var numIntentos: Int = NUM_INTENTOS_DEFAULT
    if (existeNumIntentos == -1) {
        terminal.println(yellow("No existe el argumento -numIntentos"))
        terminal.println("Uso: java -jar mosca.jar -tam 8 -numIntentos 5")
        exitProcess(1)
    }
    if (args[existeNumIntentos + 1].toIntOrNull() == null) {
        terminal.println(yellow("El valor de -numIntentos no es un entero"))
        println("usando el valor por defecto $NUM_INTENTOS_DEFAULT")
    } else {
        numIntentos = args[existeNumIntentos + 1].toInt()
    }
    return intArrayOf(tam, numIntentos)
}

/**
 * Busca una cadena dentro de un array de cadenas
 * @param args Array de cadenas
 * @param cadena Cadena a buscar
 * @return Posición de la cadena dentro del array o -1 si no existe
 */
fun buscarEnArgs(args: Array<String>, cadena: String): Int {
    for (i in args.indices) {
        if (args[i] == cadena) {
            return i
        }
    }
    return -1
}
