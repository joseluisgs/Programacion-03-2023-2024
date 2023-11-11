package mosca

import Matriz
import com.github.ajalt.mordant.rendering.TextColors.*
import com.github.ajalt.mordant.terminal.Terminal
import terminal
import kotlin.system.exitProcess

const val TAM_DEFAULT = 8
const val NUM_INTENTOS_DEFAULT = 5
const val VACIO = 0
const val MOSCA = 1
const val GOLPEO_ACERTADO = 0
const val GOLPEO_FALLADO = 1
const val GOLPEO_CASI = 2

val terminal = Terminal()


/**
 * Juega a cazar la mosca en un vector
 * @param matriz Matriz de enteros
 * @param numIntentos Número de intentos para cazar la mosca
 * @return true si se ha cazado la mosca, false si no se ha cazado
 */
fun jugarCazarMosca(matriz: Matriz, numIntentos: Int): Boolean {
    var intentos = 0
    var moscaMuerta = false
    // La primera vez sorteamos la posición de la mosca
    sortearPosicionMosca(matriz)
    imprimirMatriz(matriz) // TODO Quitar en producción

    // Comenzamos el juego
    do {
        // Aumentamos el número de intentos
        intentos++
        // Pedimos la posición valida paara analizar el vector
        val posicion = pedirPosicionValida(matriz.size)
        // analizamos la posición
        val resultado = analizarGolpeo(matriz, posicion)
        when (resultado) {
            GOLPEO_ACERTADO -> {
                terminal.println(green("☠️ ¡TE LA HAS CARGADO! has acertado en el intento $intentos"))
                moscaMuerta = true
            }

            GOLPEO_CASI -> {
                terminal.println(yellow("🫤 ¡CASI! Has estado cerca en el intento $intentos"))
                terminal.println(yellow("🪰 La mosca revoltea y cambia de posición"))
                sortearPosicionMosca(matriz)
                imprimirMatriz(matriz) // TODO Quitar en producción
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
 * @param matriz Vector de enteros
 * @param posicion Posición del vector a analizar
 * @return GOLPEO_ACERTADO si la mosca está en esa posición, GOLPEO_FALLADO si no está en esa posición
 */
fun analizarGolpeo(matriz: Matriz, posicion: IntArray): Int {
    val fila = posicion[0]
    val columna = posicion[1]

    // Si la posición es MOSCA, la mosca está en esa posición, devolvemos GOLPEO_ACERTADO
    if (matriz[fila][columna] == MOSCA) {
        return GOLPEO_ACERTADO
    }

    // Mirar la mosca en una posición adyacente valida de las 8 direcciones posibles,
    // devolvemos GOLPEO_CASI si esta es una posición válida
    // Filas: fila-1, fila, fila+1
    for (i in -1..1) {
        // Columnas: columna-1, columna, columna+1
        for (j in -1..1) {
            // Obtenemos la nueva posición a analizar
            val nuevaFila = fila + i
            val nuevaColumna = columna + j
            // Comprobamos que la nueva posición es válida dentro de los límites
            if (nuevaFila in matriz.indices && nuevaColumna in matriz[0].indices) {
                // Analizamos si la nueva posición es MOSCA
                if (matriz[nuevaFila][nuevaColumna] == MOSCA) {
                    return GOLPEO_CASI
                }
            }
        }
    }

    // Si no, la mosca no está en esa posición, devolvemos GOLPEO_FALLADO
    return GOLPEO_FALLADO
}

/**
 * Pide una posición válida para el vector
 * @param size Tamaño del vector
 * @return Posición válida del vector
 */
fun pedirPosicionValida(size: Int): IntArray {
    var inputIsOk = false
    val inputRegex = "^[0-9]+:[0-9]+\$".toRegex()
    val posicion = IntArray(2)
    do {
        print("Introduce una posición válida como fila:columna-> ")
        val input = readln().trim()
        if (input.matches(inputRegex)) {
            // Cogemos cada parte del input y la convertimos a entero usando el split de :
            val fila = input.split(":")[0].toInt()
            val columna = input.split(":")[1].toInt()

            // Comprobamos que la fila y la columna están dentro del rango del vector
            if (fila in 1..size && columna in 1..size) {
                posicion[0] = fila - 1
                posicion[1] = columna - 1
                inputIsOk = true
            }
        }
        if (!inputIsOk) {
            terminal.println(red("La posición no es válida. Inténtalo de nuevo con valores entre 1 y $size"))
        }
    } while (!inputIsOk)
    return posicion
}

/**
 * Imprime un vector de enteros
 * @param matriz Matriz de enteros
 */
fun imprimirMatriz(matriz: Matriz) {
    for (i in matriz.indices) {
        for (j in matriz[i].indices) {
            if (matriz[i][j] == MOSCA) {
                print("[🪰]")
            } else {
                print("[  ]")
            }
        }
        println()
    }
    println()
}

/**
 * Sortea la posición de la mosca en el vector
 * @param matriz Vector de enteros
 */
fun sortearPosicionMosca(matriz: Matriz) {
    // Ponemos todas las posiciones a VACIO
    for (i in matriz.indices) {
        for (j in matriz[i].indices) {
            matriz[i][j] = VACIO
        }
    }
    // Sorteamos la posición de la mosca
    val posicionMoscaFila = matriz.indices.random()
    val posicionMoscaColumna = matriz[posicionMoscaFila].indices.random()
    matriz[posicionMoscaFila][posicionMoscaColumna] = MOSCA
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
