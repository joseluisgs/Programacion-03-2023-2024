const val TAM = 5
const val LIBRE = false
const val OCUPADO = true
const val PRECIO = 5.50

typealias Sala = Array<BooleanArray>

fun main(args: Array<String>) {
    var opcion = -1
    val sala: Sala = Array(TAM) { BooleanArray(TAM) { LIBRE } }
    println("Bienvenido al cine")
    println("==================")
    println()
    do {
        println("1. Mostrar sala")
        println("2. Comprar entrada")
        println("3. Devolver entrada")
        println("4. Recaudación total")
        println("5. Salir")
        print("Ingrese una opción: ")
        opcion = readln().toIntOrNull() ?: -1
        when (opcion) {
            1 -> mostrarSala(sala)
            2 -> comprarEntrada(sala)
            3 -> devolverEntrada(sala)
            4 -> recaudacion(sala)
            5 -> println("¡Hasta luego!")
            else -> println("Opción no válida")
        }
        println()
    } while (opcion != 5)
}

fun recaudacion(sala: Sala) {
    println()
    println("Recaudación")
    var total: Double = 0.0
    // Recorremos
    for (fila in sala.indices) {
        for (columna in sala[fila].indices) {
            if (sala[fila][columna] == OCUPADO) {
                total += PRECIO
            }
        }
    }
    println("Total de recaudació1n: $total €")
    println()
}

fun devolverEntrada(sala: Sala) {
    var fila = -1
    var columna = -1
    var isCorrecto = true
    println()
    do {
        println("Ingrese la fila y la columna a devolver (nºFila;nºColumna, Ejemplo: 1;1): ")
        val input = readln().trim().split(";").toTypedArray()
        if (input.size != 2) {
            println("No has introducido una butaca válida")
            isCorrecto = false
        } else {
            fila = input[0].trim().toIntOrNull() ?: -1
            columna = input[1].trim().toIntOrNull() ?: -1
            if (fila !in (1..TAM)) {
                println("Fila incorrecta, debe ser un valor entre 1 y $TAM")
                isCorrecto = false
            }
            if (columna !in (1..TAM)) {
                println("Columna incorrecta, debe ser un valor entre 1 y $TAM")
                isCorrecto = false
            }
        }
    } while (!isCorrecto)
    // Podemos comprar la entrada
    fila--
    columna--
    if (sala[fila][columna] == LIBRE) {
        println("No hay una entrada asignada a esta butaca")
    } else {
        sala[fila][columna] = LIBRE
        println("Entrada devuelta correctamente")
    }
    println()
}

fun comprarEntrada(sala: Sala) {
    println()
    if (!hayButacaLibre(sala)) {
        println("La sala está completa")
        return
    }
    var fila = -1
    var columna = -1
    var isCorrecto = true
    do {
        println("Ingrese la fila y la columna a comprar (nºFila;nºColumna, Ejemplo: 1;1): ")
        val input = readln().trim().split(";").toTypedArray()
        if (input.size != 2) {
            println("No has introducido una butaca válida")
            isCorrecto = false
        } else {
            fila = input[0].trim().toIntOrNull() ?: -1
            columna = input[1].trim().toIntOrNull() ?: -1
            if (fila !in (1..TAM)) {
                println("Fila incorrecta, debe ser un valor entre 1 y $TAM")
                isCorrecto = false
            }
            if (columna !in (1..TAM)) {
                println("Columna incorrecta, debe ser un valor entre 1 y $TAM")
                isCorrecto = false
            }
        }
    } while (!isCorrecto)
    // Podemos comprar la entrada
    fila--
    columna--
    sala[fila][columna] = OCUPADO
    println()
}

fun mostrarSala(sala: Sala) {
    println()
    println("Estado de la sala de cine")
    for (fila in sala.indices) {
        for (columna in sala[fila].indices) {
            if (sala[fila][columna] == OCUPADO) {
                print("| X |")
            } else {
                print("| _ |")
            }
        }
        println()
    }
    println()
}

fun hayButacaLibre(sala: Sala): Boolean {
    for (fila in sala.indices) {
        for (columna in sala[fila].indices) {
            if (sala[fila][columna] == LIBRE) {
                return true
            }
        }

    }
    return false
}
