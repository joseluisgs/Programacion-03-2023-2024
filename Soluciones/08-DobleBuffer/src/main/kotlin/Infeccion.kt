const val DIMENSION = 5
const val NUM_CEROS = 8
const val LIBRE = -1
const val SANA = 0
const val INFECTADA = 1
const val TIEMPO_MAXIMO = 10

fun main(args: Array<String>) {
    println("Simulador de infección con Doble Buffer")
    // Para el doble buffer necesitamos dos tableros (actual y futuro)
    var tableroActual = crearTablero(DIMENSION, NUM_CEROS)
    var tableroFuturo = crearTablero(DIMENSION, NUM_CEROS)
    var tiempo = 0
    println("Tablero inicial:")
    imprimirTablero(tableroActual)
    do {
        // Copiamos el tablero actual en el futuro
        tableroFuturo = copiarTablero(tableroActual)
        println()
        println("Tablero a los $tiempo segundos:")
        imprimirTablero(tableroActual)
        // Logica de la simulacion
        // Recorremos el tablero actual en busca de infectados
        realizarAccion(tableroActual, tableroFuturo)
        Thread.sleep(1000)
        tiempo++
        // Copiamos el tablero futuro en el actual
        tableroActual = copiarTablero(tableroFuturo)
        // Numero de sanos
        val numSanos = buscarNumeroEnTablero(tableroActual, SANA)
    } while (tiempo < TIEMPO_MAXIMO && numSanos > 0)
    println("\nFin de la simulación")
    imprimirInformeResultados(tableroActual, tiempo)
}

// En una matriz solo de lectura y la otra de escritura
private fun realizarAccion(tableroLectura: Array<IntArray>, tableroEscritura: Array<IntArray>) {
    for (i in tableroLectura.indices) {
        for (j in tableroLectura[i].indices) {
            if (tableroLectura[i][j] == INFECTADA) {
                cambiarPosicioInfectar(tableroEscritura, i, j)
            }
        }
    }
}

private fun imprimirInformeResultados(tablero: Array<IntArray>, tiempo: Int) {
    imprimirTablero(tablero)
    println("Tiempo: $tiempo")
    val numSanos = buscarNumeroEnTablero(tablero, SANA)
    println("Numero de sanos: ${numSanos}")
    if (numSanos == 0) {
        println("No se han encontrado sanos")
    }
    val numInfectados = buscarNumeroEnTablero(tablero, INFECTADA)
    println("Numero de infectados: $numInfectados")
}

fun cambiarPosicioInfectar(tablero: Array<IntArray>, fila: Int, columna: Int) {
    // Lo primero es borrar la posición anterior
    tablero[fila][columna] = LIBRE
    // Ahora buscamos una posición aleatoria
    var posFila = -1
    var posColumna = -1
    var asignado = false

    while (!asignado) {
        posFila = tablero.indices.random()
        posColumna = tablero.indices.random()
        if (tablero[posFila][posColumna] == LIBRE) {
            tablero[posFila][posColumna] = INFECTADA
            asignado = true
        }
    }
    // println("Posicion origen: $fila, $columna")
    // println("Posicion destino: $posFila, $posColumna")
    // Infectamos a los vecinos adayacentes
    infectarVecinos(tablero, posFila, posColumna)

}

fun infectarVecinos(tablero: Array<IntArray>, filaActual: Int, columnaActual: Int) {
    // Recorremos las posiciones adyacentes (8 posiciones)
    for (df in filaActual - 1..filaActual + 1) {
        for (dc in columnaActual - 1..columnaActual + 1) {
            // Si no nos salimos de los límites del tablero
            if (isPosicionValida(tablero, df, dc)) {
                // Si no es la posición  actual
                // Con las leyes de Morgan se puede simplificar
                // if (df != filaActual || dc != columnaActual) {
                if (!(df == filaActual && dc == columnaActual)) {
                    // Si la posición es una infectada
                    if (tablero[df][dc] == SANA) {
                        // Infectamos la posición actual
                        // Sorteamos que solo puede infectar a un vecino el 20% de las veces
                        if (sorteo(20)) {
                            println("Estoy en la posición $filaActual, $columnaActual y he infectado a $df, $dc")
                            tablero[df][dc] = INFECTADA
                        }
                    }
                }
            }
        }
    }
}

// No nos salimos de los limites
fun isPosicionValida(tablero: Array<IntArray>, fila: Int, columna: Int): Boolean {
    return (fila in tablero.indices && columna in tablero.indices) // && tablero[fila][columna] == LIBRE
}

fun crearTablero(dimension: Int, numCeros: Int): Array<IntArray> {
    val tablero = Array(dimension) { IntArray(dimension) }
    for (i in tablero.indices) {
        for (j in tablero.indices) {
            tablero[i][j] = LIBRE
        }
    }

    // Se rellena los ceros en posición aleatoria
    for (i in 1..numCeros) {
        var asignado = false
        do {
            var fila = (0..<(dimension)).random()
            var columna = (0..<(dimension)).random()
            if (tablero[fila][columna] == LIBRE) {
                tablero[fila][columna] = SANA
                asignado = true
            }
        } while (!asignado)
    }

    // Ponemos un infectado en posición aleatoria
    var asignado = false
    do {
        var fila = (0..<(dimension)).random()
        var columna = (0..<(dimension)).random()
        if (tablero[fila][columna] == LIBRE) {
            tablero[fila][columna] = INFECTADA
            asignado = true
        }
    } while (!asignado)
    return tablero
}

fun imprimirTablero(tablero: Array<IntArray>) {
    for (i in tablero.indices) {
        for (j in tablero.indices) {
            when (tablero[i][j]) {
                LIBRE -> print(" _ ")
                SANA -> print(" 0 ")
                INFECTADA -> print(" 1 ")
            }
        }
        println()
    }
}

fun copiarTablero(tablero: Array<IntArray>): Array<IntArray> {
    val copia = Array(tablero.size) { IntArray(tablero.size) }
    for (i in tablero.indices) {
        for (j in tablero.indices) {
            copia[i][j] = tablero[i][j]
        }
    }
    return copia
}

fun buscarNumeroEnTablero(tablero: Array<IntArray>, numero: Int): Int {
    var contador = 0
    for (i in tablero.indices) {
        for (j in tablero.indices) {
            if (tablero[i][j] == numero) {
                contador++
            }
        }
    }
    return contador
}

fun sorteo(limite: Int): Boolean {
    val numero = (0..100).random()
    return numero <= limite
}