package matrices.buscaminas

import java.util.*

// Constantes
const val MINE = -1 // Casilla con mina
const val FREE = -2 // Casilla libre
const val MARKED = -3 // Casilla marcada
const val BOARD_SIZE = 3 // Tamaño del tablero


fun main() {
    var playAgain: Boolean  // Variable para saber si se quiere jugar de nuevo

    do {
        // Se juega una partida
        partida()
        // Se imprime un mensaje de fin de juego
        println("¡Juego terminado!")
        // Preguntamos si se quiere jugar de nuevo
        playAgain = askPlayAgain()
    } while (playAgain)
}

/**
 * Función que ejecuta una partida
 */
private fun partida() {
    val totalMines = getNumberOfMines()
    val board = initializeBoard(BOARD_SIZE, totalMines)
    val visibleBoard = Array(BOARD_SIZE) { IntArray(BOARD_SIZE) { FREE } }
    var gameOver = false // Variable para saber si el juego ha terminado
    var cellsRevealed = 0 // Número de casillas reveladas
    var minesMarked = 0 // Número de minas marcadas

    // Mientras el juego no haya terminado, se sigue jugando hasta que se haya ganado o perdido
    while (!gameOver && !isGameWon(cellsRevealed, minesMarked, totalMines)) {
        printBoard(visibleBoard) // Imprimimos el tablero visible

        // Leemos la acción que se desea realizar
        val action = getAction(BOARD_SIZE)
        val row = action[0].toInt() - 1
        val col = action[1].toInt() - 1
        val mark = action[2]

        // Dependiendo de la acción, se marca o se revela la casilla
        when (mark) {
            "marcar" -> {
                // Si la casilla está libre, se marca
                if (visibleBoard[row][col] == FREE) {
                    minesMarked = markCell(board, visibleBoard, row, col, minesMarked)
                } else if (visibleBoard[row][col] == MARKED) {
                    // Si la casilla está marcada, se desmarca
                    minesMarked = unmarkCell(board, visibleBoard, row, col, minesMarked)
                    cellsRevealed--
                }
            }

            "liberar" -> {
                // Si la casilla está marcada no se puede revelar
                if (visibleBoard[row][col] == MARKED) {
                    println("Primero desmarque la casilla.")
                } else {
                    // número de casillas reveladas en el turno
                    val cellsRevealedThisTurn = revealCell(board, visibleBoard, row, col)
                    // Si se reveló una mina, se pierde el juego
                    if (cellsRevealedThisTurn == -1) {
                        gameOver = true
                    } else {
                        // Si no se reveló una mina, se aumenta el número de casillas reveladas en base al turno
                        cellsRevealed += cellsRevealedThisTurn
                    }
                }
            }
        }
    }
    // Si se ganó el juego, se imprime un mensaje de victoria, si no, se imprime un mensaje de derrota
    resultado(cellsRevealed, minesMarked, totalMines)
    // Imprimimos el tablero
    printBoard(board)
}

/**
 * Función que imprime un mensaje de victoria o derrota
 * @param cellsRevealed Número de casillas reveladas
 * @param minesMarked Número de minas marcadas
 * @param totalMines Número total de minas
 */
private fun resultado(cellsRevealed: Int, minesMarked: Int, totalMines: Int) {
    if (isGameWon(cellsRevealed, minesMarked, totalMines)) {
        println("¡Has ganado!")
    } else {
        println("¡Has perdido!")
    }
}

/**
 * Función que verifica si el juego ha sido ganado
 * @param cellsRevealed Número de casillas reveladas
 * @param minesMarked Número de minas marcadas
 * @param totalMines Número total de minas
 * @return true si el juego ha sido ganado, false si no
 */
fun isGameWon(cellsRevealed: Int, minesMarked: Int, totalMines: Int): Boolean {
    // Si el número de casillas reveladas es igual al número de casillas menos el número de minas
    // y el número de minas marcadas es igual al número de minas, se ha ganado el juego
    return cellsRevealed == (BOARD_SIZE * BOARD_SIZE - totalMines) && minesMarked == totalMines
}


/**
 * Función que lee el número de minas que se desea en el tablero
 * @return Número de minas
 */
fun getNumberOfMines(): Int {
    // Porcentaje de minas entre 15% y 85%
    val minMines = (BOARD_SIZE * BOARD_SIZE * 0.15).toInt()
    val maxMines = (BOARD_SIZE * BOARD_SIZE * 0.85).toInt()
    var numberOfMines = 0
    while (numberOfMines !in minMines..maxMines) {
        print("Ingrese el número de minas (entre $minMines y $maxMines): ")
        numberOfMines = readln().toIntOrNull() ?: 0
    }
    return numberOfMines
}

/**
 * Función que inicializa el tablero con las minas y los números
 * @param size Tamaño del tablero
 * @param mines Número de minas
 * @return Tablero inicializado
 */
fun initializeBoard(size: Int, mines: Int): Array<IntArray> {
    val board = Array(size) { IntArray(size) } // Tablero vacío
    var minesToPlace = mines // Minas que faltan por colocar

    // Colocamos las minas
    while (minesToPlace > 0) {
        val row = (0..<size).random()
        val col = (0..<size).random()

        // Si la casilla no tiene mina, la colocamos
        if (board[row][col] != MINE) {
            board[row][col] = MINE
            minesToPlace-- // Disminuimos el número de minas que faltan por colocar
        }
    }

    // Colocamos los números adyacentes a las minas
    for (row in 0..<size) {
        for (col in 0..<size) {
            if (board[row][col] != MINE) {
                board[row][col] = countAdjacentMines(board, row, col)
            }
        }
    }
    // Retornamos el tablero inicializado
    return board
}

/**
 * Función que cuenta el número de minas adyacentes a una casilla
 * @param board Tablero
 * @param row Fila de la casilla
 * @param col Columna de la casilla
 * @return Número de minas adyacentes
 */
fun countAdjacentMines(board: Array<IntArray>, row: Int, col: Int): Int {
    val size = board.size
    var count = 0

    // Recorremos las casillas adyacentes
    for (i in -1..1) {
        for (j in -1..1) {
            val newRow = row + i
            val newCol = col + j
            // Si la casilla está dentro del tablero y tiene una mina, aumentamos el contador
            if (newRow in board.indices && newCol in board.indices && board[newRow][newCol] == MINE) {
                count++ // Aumentamos el contador
            }
        }
    }

    return count
}

/**
 * Función que lee la acción que se desea realizar
 * @param size Tamaño del tablero
 * @return Arreglo con la acción
 */
fun getAction(size: Int): Array<String> {
    var validAction = false
    var action: Array<String> = arrayOf()

    while (!validAction) {
        print("Ingrese la acción (fila columna marcar/liberar): ")
        val input = readlnOrNull()?.trim() ?: ""
        val regex = Regex("""(\d+)\s+(\d+)\s+(marcar|liberar)""")

        if (input.matches(regex)) {
            action = input.split(" ").toTypedArray()
            val row = action[0].toInt() - 1
            val col = action[1].toInt() - 1
            if (row in 0 until size && col in 0 until size) {
                validAction = true
            } else {
                println("Coordenadas fuera de los límites. Intente nuevamente.")
            }
        } else {
            println("Acción inválida. Intente nuevamente.")
        }
    }

    return action
}

/**
 * Función que marca una casilla
 * @param board Tablero
 * @param visibleBoard Tablero visible
 * @param row Fila de la casilla
 * @param col Columna de la casilla
 * @param minesMarked Número de minas marcadas
 * @return Número de minas marcadas
 */
fun markCell(board: Array<IntArray>, visibleBoard: Array<IntArray>, row: Int, col: Int, minesMarked: Int): Int {
    visibleBoard[row][col] = MARKED
    return if (board[row][col] == MINE) minesMarked + 1 else minesMarked
}


/**
 * Función que revela una casilla
 * @param board Tablero
 * @param visibleBoard Tablero visible
 * @param row Fila de la casilla
 * @param col Columna de la casilla
 * @param minesMarked Número de minas marcadas
 * @return Número de minas marcadas
 */
fun unmarkCell(board: Array<IntArray>, visibleBoard: Array<IntArray>, row: Int, col: Int, minesMarked: Int): Int {
    visibleBoard[row][col] = FREE
    return if (board[row][col] == MINE) minesMarked - 1 else minesMarked
}


/**
 * Función que revela una casilla mostrando el número o la mina que contiene
 * @param board Tablero
 * @param visibleBoard Tablero visible
 * @param row Fila de la casilla
 * @param col Columna de la casilla
 * @return Número de celdas reveladas, o -1 si se reveló una mina
 */

// Versión no recursiva, es compleja por la busqueda en anchura
/*fun revealCell(board: Array<IntArray>, visibleBoard: Array<IntArray>, row: Int, col: Int): Int {
    // Si la casilla tiene una mina, se pierde el juego
    if (board[row][col] == MINE) {
        return -1
    }

    // Inicializamos una cola para hacer un recorrido en anchura (BFS)
    val queueRow = IntArray(BOARD_SIZE * BOARD_SIZE)
    val queueCol = IntArray(BOARD_SIZE * BOARD_SIZE)
    var queueStart = 0
    var queueEnd = 0

    // Agregamos la casilla inicial a la cola
    queueRow[queueEnd] = row
    queueCol[queueEnd] = col
    queueEnd++

    // Revelamos la casilla inicial
    visibleBoard[row][col] = board[row][col]
    var cellsRevealed = 1

    // Mientras la cola no esté vacía
    while (queueStart < queueEnd) {
        val currentRow = queueRow[queueStart] // Fila actual
        val currentCol = queueCol[queueStart] // Columna actual
        queueStart++ // Aumentamos el inicio de la cola

        // Si la casilla está vacía, se agregan las casillas adyacentes a la cola
        if (board[currentRow][currentCol] == 0) {
            // Recorremos las casillas adyacentes a la casilla actual (8 vecinos)
            for (dr in -1..1) { // Recorremos las filas adyacentes
                for (dc in -1..1) { // Recorremos las columnas adyacentes
                    val newRow = currentRow + dr
                    val newCol = currentCol + dc

                    // Si la casilla adyacente es válida y no ha sido revelada, se agrega a la cola
                    if (isValid(newRow, newCol) && visibleBoard[newRow][newCol] == FREE) {
                        queueRow[queueEnd] = newRow
                        queueCol[queueEnd] = newCol
                        queueEnd++
                        visibleBoard[newRow][newCol] = board[newRow][newCol]
                        cellsRevealed++
                    }
                }
            }
        }
    }

    return cellsRevealed
}*/
// Versión recursiva, es más sencilla y eficiente
fun revealCell(board: Array<IntArray>, visibleBoard: Array<IntArray>, row: Int, col: Int): Int {
    // Si la casilla tiene una mina, se pierde el juego y se devuelve -1
    if (board[row][col] == MINE) {
        return -1
    }
    // si no se revela las casillas adyacentes
    return revealAdjacentCells(board, visibleBoard, row, col)
}

/**
 * Función que revela las casillas adyacentes a una casilla
 * @param board Tablero
 * @param visibleBoard Tablero visible
 * @param row Fila de la casilla
 * @param col Columna de la casilla
 * @return Número de celdas reveladas
 */
fun revealAdjacentCells(board: Array<IntArray>, visibleBoard: Array<IntArray>, row: Int, col: Int): Int {
    // Si la casilla no es válida o ya ha sido revelada, se devuelve 0
    if (!isValid(row, col) || visibleBoard[row][col] != FREE) {
        return 0
    }

    visibleBoard[row][col] = board[row][col] // Revelamos la casilla
    var cellsRevealed = 1 // Número de casillas reveladas


    // Si la casilla está vacía, se revelan las casillas adyacentes (8 vecinos recursivamente)
    if (board[row][col] == 0) {
        for (dr in -1..1) {
            for (dc in -1..1) {
                // Se revelan las casillas adyacentes recursivamente
                cellsRevealed += revealAdjacentCells(board, visibleBoard, row + dr, col + dc)
            }
        }
    }
    // Se devuelve el número de casillas reveladas
    return cellsRevealed
}

/**
 * Función que verifica si una casilla es válida, es decir, si está dentro del tablero
 * @param row Fila de la casilla
 * @param col Columna de la casilla
 * @return true si la casilla es válida, false si no
 */
fun isValid(row: Int, col: Int): Boolean {
    return row in 0 until BOARD_SIZE && col in 0 until BOARD_SIZE
}

/**
 * Función que imprime el tablero
 * @param board Tablero
 */
fun printBoard(board: Array<IntArray>) {
    val size = board.size

    print("    ")
    for (i in 1..size) {
        print(" $i ")
    }
    println()

    print("   ")
    repeat(size) { print("---") } // es igual que un for, pero no se usa el indice
    println("--")

    for (i in board.indices) {
        print("${i + 1} | ")
        for (cell in board[i]) {
            val value = when (cell) {
                MINE -> "*"
                FREE -> "-"
                MARKED -> "M"
                else -> cell.toString() // Número
            }
            print(" $value ")
        }
        println(" |")
    }

    print("   ")
    repeat(size) { print("---") } // es igual que un for, pero no se usa el indice
    println("--")

    println()
}

/**
 * Función que pregunta si se quiere jugar de nuevo
 * @return true si se quiere jugar de nuevo, false si no
 */
fun askPlayAgain(): Boolean {
    var validResponse = false
    var playAgain = false

    do {
        print("¿Quieres jugar de nuevo? (s/n): ")
        val response = readln().trim().lowercase(Locale.getDefault())

        when (response) {
            "s", "si", "sí" -> {
                playAgain = true
                validResponse = true
            }

            "n", "no" -> {
                playAgain = false
                validResponse = true
            }

            else -> println("Respuesta inválida. Por favor, responde con 's' o 'n'.")
        }
    } while (!validResponse)

    return playAgain
}