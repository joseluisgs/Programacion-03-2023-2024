package matrices

// Types Aliases (alias de tipos)
typealias Matriz = Array<IntArray>

fun main() {
    //var matriz = Array(3) { IntArray(3) } // Matriz de 3x3
    //var matriz2: Matriz = Matriz(3) { IntArray(3) } // Matriz de 3x3
    val matriz: Array<IntArray> = arrayOf(
        intArrayOf(1, 2, 3),
        intArrayOf(4, 5, 6),
        intArrayOf(7, 8, 9)
    )


    // Inicializando a matriz, aunque ya tiene un 0 por defecto
    // Recorro las filas
    /*for (i in matriz.indices) {
        // Recorro las columnas
        for (j in matriz[i].indices) {
            matriz[i][j] = (1..9).random()
        }
    }*/

    // Imprimo la matriz filas x columnas
    /* for (i in matriz.indices) {
         for (j in matriz[i].indices) {
             print(matriz[i][j])
         }
         println()
     }

     println()

     // Imprimo la matriz columnas x filas
     // Primero recorro las columnas
     for (j in matriz[0].indices) {
         for (i in matriz.indices) {
             print(matriz[i][j])
         }
         println()
     }*/

    // imprimo filas ascedentes x columnas ascendentes
    for (i in matriz.indices) {
        for (j in matriz[i].indices) {
            print(matriz[i][j])
        }
    }
    println()
    // imprimo filas descendentes x columnas ascendentes
    for (i in matriz.indices.reversed()) {
        for (j in matriz[i].indices) {
            print(matriz[i][j])
        }
    }
    println()
    // imprimo filas ascendentes x columnas descendentes
    for (i in matriz.indices) {
        for (j in matriz[i].indices.reversed()) {
            print(matriz[i][j])
        }
    }
    println()
    // imprimo filas descendentes x columnas descendentes
    for (i in matriz.indices.reversed()) {
        for (j in matriz[i].indices.reversed()) {
            print(matriz[i][j])
        }
    }
    println()

    // columnas ascendentes y filas ascendentes
    for (j in matriz[0].indices) {
        for (i in matriz.indices) {
            print(matriz[i][j])
        }
    }
    println()

    // columnas descendentes y filas ascendentes
    for (j in matriz[0].indices.reversed()) {
        for (i in matriz.indices) {
            print(matriz[i][j])
        }
    }
    println()
    // columnas ascendentes y filas descendentes
    for (j in matriz[0].indices) {
        for (i in matriz.indices.reversed()) {
            print(matriz[i][j])
        }
    }
    println()
    // columnas descendentes y filas descendentes
    for (j in matriz[0].indices.reversed()) {
        for (i in matriz.indices.reversed()) {
            print(matriz[i][j])
        }
    }
    println()

    // Imprimir las 8 valores que hay adyacente a una posición de una matriz, por ejemplo, la posición 1,1
    // PEro no nos podemos salir, si no hay imprimir solo los válidos
    imprimirValoresAdyacentes(matriz, 0, 0)


}

fun imprimirValoresAdyacentes(matriz: Array<IntArray>, fila: Int, columna: Int) {
    for (i in -1..1) {
        for (j in -1..1) {
            val nuevaFila = fila + i
            val nuevaColumna = columna + j
            if (nuevaFila in matriz.indices && nuevaColumna in matriz[0].indices) {
                // ¡¡ Si entro aquí es que la posición es válida
                // Realizo la acción que necesite !!
                // Evitamos imprimir el valor de la celda central
                if (i != 0 || j != 0) {
                    print(matriz[nuevaFila][nuevaColumna])
                }
            }
        }
    }
}


fun imprimirMatriz(matriz: Array<IntArray>) {
    for (i in matriz.indices) {
        for (j in matriz[i].indices) {
            print(matriz[i][j])
        }
    }
}

fun imprimirMatrizAlias(matriz: Matriz) {
    for (i in matriz.indices) {
        for (j in matriz[i].indices) {
            print(matriz[i][j])
        }
        println()
    }
}