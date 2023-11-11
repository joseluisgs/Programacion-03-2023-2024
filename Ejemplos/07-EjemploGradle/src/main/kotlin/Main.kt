import com.github.ajalt.mordant.rendering.TextColors.green
import com.github.ajalt.mordant.rendering.TextColors.red
import com.github.ajalt.mordant.table.table
import com.github.ajalt.mordant.terminal.Terminal

/**
 * Comprueba si un dni es válido en base a su longitud y letra
 * @param args El primer argumento debe ser el dni a comprobar
 * @return true si el dni es válido, false en caso contrario
 * @see comprobarDniConExpresionesRegulares
 *  @since 1.0
 *  @author José Luis González
 */
val terminal = Terminal()

fun main(args: Array<String>) {

    if (args.size != 1) {
        // System.err.println("No se ha introducido un dni")
        println("Por favor, introduce un dni, ejemplo: 12345678Z")
        return
    }

    val res = comprobarDniConExpresionesRegulares(args[0])
    terminal.println(
        table {
            header { row("DNI", "Resultado") }
            body { row(args[0], if (res) green("Dni Válido") else red("Dni No válido")) }
        })

    val response = terminal.prompt("¿Desea salir?", default = "S")
    terminal.println("Tu respuesta: $response")
}

/**
 * Comprueba si un dni es válido en base a su longitud y letra
 * @param dni El dni a comprobar
 * @return true si el dni es válido, false en caso contrario
 * @since 1.0
 * @author José Luis González
 */
fun comprobarDniSinExpresionesRegulares(dni: String): Boolean {
    if (dni.length != 9) {
        return false
    }
    val letra = dni[8].uppercaseChar()
    val numero = dni.substring(0, 8)
    if (numero.toIntOrNull() == null) {
        return false
    }
    val resto = numero.toInt() % 23
    val letras = "TRWAGMYFPDXBNJZSQVHLCKE"
    val letraCorrecta = letras[resto]
    return letra == letraCorrecta
}

/**
 * Comprueba si un dni es válido en base a su longitud y letra
 * @param dni El dni a comprobar
 * @return true si el dni es válido, false en caso contrario
 * @since 1.0
 * @author José Luis González
 */
fun comprobarDniConExpresionesRegulares(dni: String): Boolean {
    if (!Regex("[0-9]{8}[A-Z]").matches(dni)) {
        return false
    }
    val letra = dni.last()
    val numero = dni.substring(0, 8).toInt()
    val resto = numero.toInt() % 23
    val letras = "TRWAGMYFPDXBNJZSQVHLCKE"
    val letraCorrecta = letras[resto]
    return letra == letraCorrecta
}