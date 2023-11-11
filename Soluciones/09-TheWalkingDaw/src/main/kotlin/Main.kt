import kotlin.random.Random

const val TAM_TABLERO = 5 // Tamaño del mundo
const val NUM_BLOQUES = 7 // Cantidad de bloques con los que chocamos
const val NUM_ENEMIGOS = 15 // Cantidad de enemigos
const val NUM_VIDAS = 2 // Cantidad de vidas
const val SALUD_INICIAL = 10 // Vida inicial
const val MUNICION_INICIAL = 10 // Municiones iniciales
const val PROB_RECIBIR_ATAQUE = 50 // probabilidad de ataque
const val DURACION_JUEGO = 30_000 // 10 segundos
const val PAUSA = 1000 // 1 segundo
const val ENEMIGOS_INTERVALO = 5000 // 5 segundos
const val NORTE = 1
const val SUR = 2
const val ESTE = 3
const val OESTE = 4
const val SALIDA = 1 // Salida del juego 1
const val CONTINUAR = 0
const val ENEMIGO = 'Z'
const val BLOQUE = 'B'
const val VIDA = 'V'
const val JUGADOR = 'P'
const val MUNICION = 'M'

fun main() {
    var tablero = Array(TAM_TABLERO) { CharArray(TAM_TABLERO) { ' ' } }
    var tableroBuffer = Array(TAM_TABLERO) { CharArray(TAM_TABLERO) { ' ' } }
    var saludo = SALUD_INICIAL
    var municion = MUNICION_INICIAL
    var posFil = tablero.indices.random() // 0
    var posCol = tablero.indices.random() // 0
    var enemigosMuertos = 0
    var tiempo = 0
    var salida = CONTINUAR
    var direccion = arrayOf(NORTE, SUR, ESTE, OESTE).random()

    // Inicializar el mundo
    tablero[posFil][posCol] = 'P' // Posición inicial del jugador
    tablero = inicializarTablero(tablero) // Inicializamos el mundo
    println("Bienvenido al juego de los zombies!")

    // Bucle principal del juego
    do {
        println("Tiempo: $tiempo")
        println("Vida: $saludo")
        println("Municion: $municion")
        println("Direccion: ${miDireccion(direccion)}")
        imprimirTablero(tablero)
        println()

        // Doble buffer, para no modificar el mundo original, los cambios se hacen en el buffer
        tableroBuffer = copiarTablero(tablero, tableroBuffer)
        val results = accionJuego(tablero, tableroBuffer, saludo, municion, posFil, posCol, direccion)

        // Actualizamos los datos del jugador
        saludo = results[0]
        municion = results[1]
        posFil = results[2]
        posCol = results[3]
        enemigosMuertos += results[4]
        direccion = results[5]
        salida = results[6]

        // Copiamos el buffer al mundo original
        tablero = copiarTablero(tableroBuffer, tablero)

        // Esperamos un segundo
        Thread.sleep(PAUSA.toLong())
        tiempo += PAUSA

        // Cada cinco segundos, aparece un enemigo nuevo
        if (tiempo % ENEMIGOS_INTERVALO == 0 && hayEspacioLibre(tablero)) { // Verificamos si hay espacio para un nuevo enemigo
            println("Aparece un nuevo enemigo!")
            tablero = colocarEnPosicionAleatoria('E', tablero)
        }

        println()

        // Ciclo hasta que se acabe el tiempo o la vida
    } while (tiempo < DURACION_JUEGO && saludo > 0 && salida != SALIDA)

    // Informacion final
    println()
    println("\nFin del juego!")
    if (saludo <= 0) {
        println("Has muerto!")
        saludo = 0
    } else {
        println("Has sobrevivido!")
    }
    if (salida == SALIDA) {
        println("Has escapado!")
    }
    println("Has matado a $enemigosMuertos enemigos.")
    println("Tu vida final fue $saludo.")
    println("Tu municion final fue $municion.")
}

// Inicializa el mundo con los enemigos, bloques y vidas
fun inicializarTablero(tablero: Array<CharArray>): Array<CharArray> {
    var nuevoTablero = copiarTablero(tablero, Array(TAM_TABLERO) { CharArray(TAM_TABLERO) { ' ' } })
    repeat(NUM_VIDAS) {
        nuevoTablero = colocarEnPosicionAleatoria(VIDA, nuevoTablero)
    }
    repeat(NUM_BLOQUES) {
        nuevoTablero = colocarEnPosicionAleatoria(BLOQUE, nuevoTablero)
    }
    repeat(NUM_ENEMIGOS) {
        nuevoTablero = colocarEnPosicionAleatoria(ENEMIGO, nuevoTablero)
    }
    return nuevoTablero
}

// Coloca un item aleatoriamente en el mundo
fun colocarEnPosicionAleatoria(item: Char, tablero: Array<CharArray>): Array<CharArray> {
    var fila: Int
    var columna: Int

    val nuevoTablero = copiarTablero(tablero, Array(TAM_TABLERO) { CharArray(TAM_TABLERO) { ' ' } })
    // Soretamos una posición aleatoria hasta que encontremos una vacía
    do {
        fila = (0 until TAM_TABLERO).random()
        columna = (0 until TAM_TABLERO).random()
    } while (nuevoTablero[fila][columna] != ' ')
    nuevoTablero[fila][columna] = item
    return nuevoTablero
}

// Verifica si hay algún espacio vacío en el mundo
fun hayEspacioLibre(tablero: Array<CharArray>): Boolean {
    for (i in tablero.indices) {
        for (j in tablero[i].indices) {
            if (tablero[i][j] == ' ') {
                return true
            }
        }
    }
    return false
}

fun accionJuego(
    tablero: Array<CharArray>,
    tableroBuffer: Array<CharArray>,
    salud: Int,
    municio: Int,
    posFil: Int,
    posCol: Int,
    direccion: Int
): Array<Int> {
    var saludActual = salud
    var muncionActual = municio
    var filaActual = posFil
    var posActual = posCol
    var enemigosMuertos = 0

    // Calculamos la nueva posición
    var nuevaPosFil = filaActual
    var nuevaPosCol = posActual
    var direccionActual =
        arrayOf(NORTE, SUR, ESTE, OESTE).random() // Cambiamos la dirección aleatoriamente en cada turno // direccion
    println("Nueva dirección, nos movemos en ${miDireccion(direccionActual)}")
    when (direccionActual) {
        NORTE -> if (filaActual > 0) nuevaPosFil-- // Movimiento hacia el norte, decrementamos la fila
        SUR -> if (filaActual < TAM_TABLERO - 1) nuevaPosFil++ // Movimiento hacia el sur, incrementamos la fila
        ESTE -> if (posActual < TAM_TABLERO - 1) nuevaPosCol++ // Movimiento hacia el este, incrementamos la columna
        OESTE -> if (posActual > 0) nuevaPosCol-- // Movimiento hacia el oeste, decrementamos la columna
    }

    // Si la nueva posición está fuera del mundo, cambiamos la dirección
    if (nuevaPosFil !in tablero.indices || nuevaPosCol !in tablero.indices) {
        direccionActual = cambiarDireccionEnLimiteTablero(tablero, filaActual, posActual, direccionActual)
        nuevaPosFil = filaActual
        nuevaPosCol = posActual
    }

    // Actualizamos la posición del jugador
    filaActual = nuevaPosFil
    posActual = nuevaPosCol

    // Si se encuentra con un enemigo o una vida, se ejecuta la acción correspondiente
    when (tablero[filaActual][posActual]) {
        ' ' -> println(
            "No encontramos nada en $filaActual, $posActual, seguimos avanzado en ${
                miDireccion(
                    direccionActual
                )
            }"
        )

        'B' -> {
            // Si se encuentra con un bloque y tiene al menos 2 municiones, dispara al bloque
            if (muncionActual >= 2) {
                muncionActual -= 2
                tableroBuffer[filaActual][posActual] = ' '
                println("Hemos disparado al bloque en $filaActual, $posActual y lo hemos destruido gastando dos balas.")
                tableroBuffer[filaActual][posActual] = ' '
            } else {
                // Si no tiene suficiente munición, intenta cambiar de dirección
                val newDirection =
                    cambiarDireccionCuandoHayBloque(tablero, filaActual, posActual, direccionActual)
                if (newDirection != direccionActual) {
                    direccionActual = newDirection
                } else {
                    // Si todas las direcciones están bloqueadas, el jugador se queda en su posición actual
                    println("Estamos atrapados por bloques y no tenemos suficientes balas. Nos quedamos en nuestra posición actual.")
                }
            }
        }

        'E' -> {
            println("Encontramos un enemigo en $filaActual, $posActual")
            // Si tenemos munición, atacamos al enemigo
            if (muncionActual > 0) {
                // El enemigo tiene una probabilidad del 60% de atacarnos antes de que podamos disparar
                if (Random.nextInt(100) < PROB_RECIBIR_ATAQUE) {
                    saludActual--
                    println("El enemigo nos ha atacado antes! Perdemos una vida.")
                }
                // Disparamos al enemigo
                muncionActual--
                tableroBuffer[filaActual][posActual] = ' '
                enemigosMuertos++
                println("Hemos matado al enemigo en $filaActual, $posActual")
            } else {
                // Si no hay munición, el jugador puede moverse a una casilla con un enemigo, pero pierde dos vidas
                saludActual -= 2
                tableroBuffer[filaActual][posActual] = ' '
                println("Hemos atacado al enemigo con el machete y hemos perdido dos vidas.")
            }
        }

        'V' -> {
            // Si se encuentra con una vida, la recoge
            saludActual++
            tableroBuffer[filaActual][posActual] = ' '
            println("Hemos encontrado una vida en $filaActual, $posActual")
        }
    }

    // Actualizamos la posición del jugador en el buffer
    tableroBuffer[posFil][posCol] = ' '
    tableroBuffer[filaActual][posActual] = 'P'
    // Hemos salido si la posición nueva es [4,4]
    var salida = CONTINUAR
    if (filaActual == TAM_TABLERO - 1 && posActual == TAM_TABLERO - 1) {
        println("Hemos salido del mundo!")
        salida = SALIDA
    }

    return arrayOf(saludActual, muncionActual, filaActual, posActual, enemigosMuertos, direccionActual, salida)
}


// Cambia la dirección de movimiento a una dirección no bloqueada cuando se sale del mundo
fun cambiarDireccionEnLimiteTablero(tablero: Array<CharArray>, posFil: Int, posCol: Int, direccion: Int): Int {
    // Debemos cambiar la dirección a una dirección no bloqueada y que no sea la actual
    var nuevaDireccion = direccion
    val direcciones = arrayOf(NORTE, SUR, ESTE, OESTE)
    var direccionesSinActual = arrayOf<Int>()
    for (dir in direcciones) {
        if (dir != direccion) {
            direccionesSinActual += dir
        }
    }
    // Barajamos las direcciones
    val direccionesShuffled = barajarArray(direccionesSinActual)

    for (dir in direccionesShuffled) {
        val nuevaFil =
            if (dir == NORTE) posFil - 1 else if (dir == SUR) posFil + 1 else posFil
        val nuevaCol =
            if (dir == ESTE) posCol + 1 else if (dir == OESTE) posCol - 1 else posCol

        // if (nuevaFil in tablero.indices && nuevaCol in tablero.indices && tablero[nuevaFil][nuevaCol] != 'B' && tablero[nuevaFil][nuevaCol] != 'E') {
        if (nuevaFil in tablero.indices && nuevaCol in tablero.indices) {
            nuevaDireccion = dir
            break // Salimos del bucle
        }
    }
    println("Nos hemos salido del mundo y cambiamos de dirección a ${miDireccion(nuevaDireccion)}")
    return nuevaDireccion
}

// Cambia la dirección de movimiento cuando se encuentra un bloque
fun cambiarDireccionCuandoHayBloque(tablero: Array<CharArray>, posFil: Int, posCol: Int, direccion: Int): Int {
    var nuevaDireccion = direccion
    val direcciones = barajarArray(arrayOf(NORTE, SUR, ESTE, OESTE))
    var estaTodoBloqueado = true

    for (dir in direcciones) {
        val nuevaFila =
            if (dir == NORTE) posFil - 1 else if (dir == SUR) posFil + 1 else posFil
        val nuevaColumna =
            if (dir == ESTE) posCol + 1 else if (dir == OESTE) posCol - 1 else posCol

        // Si llegamos aquí, es porque la nueva posición está dentro del mundo
        // Hacemos un movimiento seguro, es decir, si la nueva posición es un bloque o un enemigo, no cambiamos de dirección
        if (nuevaFila in tablero.indices && nuevaColumna in tablero.indices && tablero[nuevaFila][nuevaColumna] != 'B' && tablero[nuevaFila][nuevaColumna] != 'E') {
            // if (newPosFil in world.indices && newPosCol in world.indices) {
            nuevaDireccion = dir
            estaTodoBloqueado = false
            break // Salimos del bucle
        }
    }

    if (estaTodoBloqueado) {
        println("Todas las direcciones están bloqueadas. El jugador se queda en su posición actual.")
    } else {
        println("Chocamos con un bloque y cambiamos de dirección a ${miDireccion(nuevaDireccion)}")
    }

    return nuevaDireccion
}

// Cambia la dirección de movimiento cuando se encuentra un enemigo y no tiene munición
fun cambiaDireccionCuandoHayEnemigo(tablero: Array<CharArray>, posFil: Int, posCol: Int, direccion: Int): Int {
    var currentPosFil = posFil
    var currentPosCol = posCol
    var nuevaDireccion = direccion
    do {
        nuevaDireccion = arrayOf(NORTE, SUR, ESTE, OESTE).random()
        when (direccion) {
            NORTE -> {
                if (posFil > 0) currentPosFil--
            }

            SUR -> {
                if (posFil < TAM_TABLERO - 1) currentPosFil++
            }

            OESTE -> {
                if (posCol > 0) currentPosCol--
            }

            ESTE -> {
                if (posCol < TAM_TABLERO - 1) currentPosCol++
            }
        }
    } while (tablero[currentPosFil][currentPosCol] == 'B' || tablero[currentPosFil][currentPosCol] == 'E')
    println("Hemos huido del enemigo y cambiamos de dirección a ${miDireccion(nuevaDireccion)}")
    return nuevaDireccion
}

// Copia el mundo a un nuevo mundo
fun copiarTablero(origen: Array<CharArray>, destino: Array<CharArray>): Array<CharArray> {
    for (i in origen.indices) {
        for (j in origen[i].indices) {
            destino[i][j] = origen[i][j]
        }
    }
    return destino
}

// Imprime el mundo
fun imprimirTablero(world: Array<CharArray>) {
    println("╔═" + "═══".repeat(TAM_TABLERO) + "═╗")
    for (i in world.indices) {
        print("║ ")
        for (j in world[i].indices) {
            print(" ${world[i][j]} ")
        }
        println(" ║")
    }
    println("╚═" + "═══".repeat(TAM_TABLERO) + "═╝")
}


fun miDireccion(direction: Int): String {
    return when (direction) {
        NORTE -> "Norte"
        SUR -> "Sur"
        ESTE -> "Este"
        OESTE -> "Oeste"
        else -> "Desconocida"
    }
}

// Desordena un array
fun barajarArray(array: Array<Int>): Array<Int> {
    val rand = Random
    for (i in array.size - 1 downTo 1) {
        val j = rand.nextInt(i + 1)
        // Intercambio
        val temp = array[i]
        array[i] = array[j]
        array[j] = temp
    }
    return array
}
