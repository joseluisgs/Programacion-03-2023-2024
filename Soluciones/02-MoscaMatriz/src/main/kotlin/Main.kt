import com.github.ajalt.mordant.rendering.TextColors.green
import com.github.ajalt.mordant.rendering.TextColors.red
import com.github.ajalt.mordant.terminal.Terminal
import mosca.analizarArgumentos
import mosca.imprimirMatriz
import mosca.jugarCazarMosca

typealias Matriz = Array<IntArray>


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
    val tamMatriz = inputs[0]
    val numIntentos = inputs[1]

    // Presentación del juego
    println("Iniciando el juego de la mosca con los siguientes parámetros:")
    println("Tamaño del vector: $tamMatriz")
    println("Número de intentos: $numIntentos")

    // Creamos el vector
    val matriz = Matriz(tamMatriz) { IntArray(tamMatriz) }
    val result = jugarCazarMosca(matriz, numIntentos)
    if (result) {
        terminal.println(green("Has ganado y has cazado la mosca en menos de $numIntentos intentos"))
    } else {
        terminal.println(red("Has perdido y no has podido cazar la mosca en $numIntentos intentos"))
    }
    // Imprimimos el vector
    imprimirMatriz(matriz)
}
