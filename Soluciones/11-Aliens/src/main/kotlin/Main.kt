import kotlin.random.Random

const val SPACE_SIZE = 5
const val NUM_ALIENS = 10
const val ALIEN = 1
const val VACIO = 0
const val MAX_TIME = 30
const val PAUSE_TIME = 1000L // (1 segundo)
const val MAX_LIVES = 5
const val TIME_ATACK = 5
const val PROB_ATACK = 40
const val PROB_ACCURACY = 70
const val MAX_TRIES_TO_MOVE = 16


typealias Space = Array<IntArray>


fun main(args: Array<String>) {
    println("La guerra contra los Aliens ha comenzado!")
    var space = createSpace()
    var time = 1
    var lives = MAX_LIVES
    var aliens = NUM_ALIENS
    do {
        // Doble buffer, origen --> destino
        var spaceBuffer = copySpace(space)
        println()
        println("Tiempo: $time")
        println("Vidas: $lives")
        println("Aliens restantes: $aliens")
        printSpace(space)

        // otras cosas
        if (aimAndFire(space, spaceBuffer)) {
            aliens -= 1
        }

        // Condiciones tiempo
        if (time % TIME_ATACK == 0) {
            if (alienAtack(space)) {
                lives -= 1
                println("Los aliens han atacado!")
                println("Vidas restantes: $lives")
            } else {
                println("Los aliens han fallado al atacar!")
            }
        }

        // Cada 2 segundos cambiamos de posición los alines
        if (time % 2 == 0) {
            // Los aliens se mueven aleatoramente (version cutre para aprobar)
            // spaceBuffer = createSpace(aliens)
            // Los aliens se mueven a posiciones adyacentes (version pro)
            moveAliens(space, spaceBuffer)
            println("Los aliens se han movido!")
        }


        Thread.sleep(PAUSE_TIME)
        time += 1
        // Doble buffer, destino --> origen
        space = copySpace(spaceBuffer)

    } while (time <= MAX_TIME && lives > 0 && aliens > 0)
    println()
    printSpace(space)
    println("Tiempo: $time")
    if (aliens == 0) {
        println("Has aniquilado a todos los aliens!")
    } else {
        println("Hay aliens vivos que regresarán a por ti!")
    }
    if (lives == 0) {
        println("Has muerto en esta batalla!")
    } else {
        println("Has sobrevivido, y vives para luchar otro día!")
    }

    println()
}

fun moveAliens(space: Array<IntArray>, spaceBuffer: Space) {
    // Recorremos el espacion
    for (i in space.indices) {
        for (j in space[i].indices) {
            if (space[i][j] == ALIEN) {
                // println("Alien se desplaza desde [${i + 1},${j + 1}]")
                moveAlienToANewPosition(spaceBuffer, i, j)
            }
        }
    }
}

fun moveAlienToANewPosition(spaceBuffer: Array<IntArray>, fil: Int, col: Int) {
    // Obtenemos las posiciones adyacentes
    var isStored = false
    var newFil: Int
    var newCol: Int
    var intentos = 0
    do {
        newFil = (-1..1).random() + fil
        newCol = (-1..1).random() + col
        // Cuidado que es spaceBuffer porque comparamos con la nueva posición
        // si no se daría el caso de que el alien se mueve a una posición
        // que ya está ocupada por otro alien
        if (isValidPosition(newFil, newCol) && spaceBuffer[newFil][newCol] == VACIO) {
            spaceBuffer[fil][col] = VACIO
            spaceBuffer[newFil][newCol] = ALIEN
            isStored = true
        }
        intentos++
    } while (!isStored && intentos++ < MAX_TRIES_TO_MOVE)
    if (!isStored) {
        println("El alien en la posición [${fil + 1},${col + 1}] no se ha podido mover por estar bloqueado!")
    } else {
        println("Alien se desplaza desde [${fil + 1},${col + 1}] a [${newFil + 1},${newCol + 1}]")
    }
}

fun isValidPosition(fil: Int, col: Int): Boolean {
    return fil in 0..<SPACE_SIZE && col in 0..<SPACE_SIZE
}

fun aimAndFire(space: Space, spaceBuffer: Space): Boolean {
    println("Apuntando...")
    val x = Random.nextInt(SPACE_SIZE)
    val y = Random.nextInt(SPACE_SIZE)
    if (space[x][y] == VACIO) {
        println("Has disparado a la posición [${x + 1},${y + 1}] y y es una posición vacía!")
        return false
    }
    return if (Random.nextInt(100) < PROB_ACCURACY) {
        println("Has disparado a la posición [${x + 1},${y + 1}] y has acertado!")
        spaceBuffer[x][y] = VACIO
        true
    } else {
        println("Has disparado a la posición [${x + 1},${y + 1}] y has fallado!")
        false
    }
}

fun alienAtack(space: Space): Boolean {
    return Random.nextInt(100) < PROB_ATACK
}

fun copySpace(space: Space): Space {
    val copy = Array(SPACE_SIZE) { IntArray(SPACE_SIZE) }
    for (i in space.indices) {
        for (j in space[i].indices) {
            copy[i][j] = space[i][j]
        }
    }
    return copy

}

fun printSpace(space: Space) {
    for (row in space) {
        for (cell in row) {
            if (cell == ALIEN) {
                print("[A]")
            } else {
                print("[ ]")
            }
        }
        println()
    }
}

fun createSpace(numAlies: Int = NUM_ALIENS): Space {
    val space = Array(SPACE_SIZE) { IntArray(SPACE_SIZE) }
    repeat(numAlies) {
        // No podemos poner dos aliens en la misma posición
        var isStored = false
        do {
            val x = Random.nextInt(SPACE_SIZE)
            val y = Random.nextInt(SPACE_SIZE)
            // Si la posición está vacía, la ocupamos
            if (space[x][y] == VACIO) {
                space[x][y] = ALIEN
                isStored = true
            }
        } while (!isStored)
    }
    return space
}
