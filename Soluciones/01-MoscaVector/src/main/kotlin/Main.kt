import com.github.ajalt.mordant.rendering.TextColors.green
import com.github.ajalt.mordant.rendering.TextColors.red
import com.github.ajalt.mordant.terminal.Terminal
import mosca.analizarArgumentos
import mosca.imprimirVector
import mosca.jugarCazarMosca
import mosca.sortearPosicionMosca

const val TAM_DEFAULT = 8
const val NUM_INTENTOS_DEFAULT = 5
const val VACIO = 0
const val MOSCA = 1
const val GOLPEO_ACERTADO = 0
const val GOLPEO_FALLADO = 1
const val GOLPEO_CASI = 2

val terminal = Terminal()

/**
 * Juego de cazar la mosca en un vector
 * @author JoseLuisGS
 * @version 1
 */
fun main(args: Array<String>) {
    // Analizamos los argumentos
    val inputs = analizarArgumentos(args)

    // Recogemos los valores de los argumentos
    val tamVector = inputs[0]
    val numIntentos = inputs[1]

    // Presentación del juego
    println("Iniciando el juego de la mosca con los siguientes parámetros:")
    println("Tamaño del vector: $tamVector")
    println("Número de intentos: $numIntentos")

    // Creamos el vector
    val vector = IntArray(inputs[0])
    sortearPosicionMosca(vector) // El vector se pasa por referencia
    // imprimirVector(vector) // TODO Quitar en producción
    val result = jugarCazarMosca(vector, numIntentos)
    if (result) {
        terminal.println(green("Has ganado y has cazado la mosca en menos de $numIntentos intentos"))
    } else {
        terminal.println(red("Has perdido y no has podido cazar la mosca en $numIntentos intentos"))
    }
    // Imprimimos el vector
    imprimirVector(vector)
}
