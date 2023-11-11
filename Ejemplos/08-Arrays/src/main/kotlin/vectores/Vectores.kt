package vectores

fun main() {
    /*val vector = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)

    val maximo = maximo(vector)
    println("El máximo es $maximo")
    val minimo = minimo(vector)
    println("El mínimo es $minimo")
    val suma = suma(vector)
    println("La suma es $suma")
    val tamaño = tamaño(vector)
    println("El tamaño es $tamaño")
    val numPares = numPares(vector)
    println("El número de pares es $numPares")
    val numImpares = numImpares(vector)
    println("El número de impares es $numImpares")
    val sumaPosicionParesHumano = sumaPosicionParesHumano(vector)
    println("La suma de las posiciones pares es $sumaPosicionParesHumano")
    val sumaParesImpares = sumaParesImpares(vector)
    println("La suma de pares es ${sumaParesImpares[0]} y la de impares es ${sumaParesImpares[1]}")
    val media = media(vector)
    println("La media es $media")
    val array = crearArrayPorTeclado()
    imprimirArrayDos(array)
    val arrayAsString = miJoinToString(array, inicio = "[", separador = " - ", fin = "]")
    println(arrayAsString)
    var existe = existe(array, 5)
    println(existe)
    existe = existe(array, 99)
    println(existe)*/

    val arrayOne = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    val arrayTwo = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    val sonIguales = sonIguales(arrayOne, arrayTwo)
    println(sonIguales)
    println(clonar(arrayOne).joinToString())
    println(clonar(arrayOne, 2).joinToString())
    println(clonar(arrayOne, 2, 5).joinToString())
}

fun clonar(arrayOne: IntArray, init: Int = 0, end: Int = arrayOne.lastIndex): IntArray {
    val inicio = if (init < 0) 0 else init
    val fin = if (end > arrayOne.lastIndex) arrayOne.lastIndex else end
    val arrayClonado = IntArray(fin - inicio + 1)
    for (i in inicio..fin) {
        arrayClonado[i - inicio] = arrayOne[i]
    }
    return arrayClonado
}

fun sonIguales(arrayOne: IntArray, arrayTwo: IntArray): Boolean {
    // early return
    if (arrayOne.size != arrayTwo.size) {
        return false
    }
    for (i in arrayOne.indices) {
        if (arrayOne[i] != arrayTwo[i]) {
            return false
        }
    }
    return true
}

fun existe(array: IntArray, elemento: Int): Int {
    // -1 si no existe en el vector
    // posicion si existe en el vector
    var posicion = -1
    for (i in array.indices) {
        if (array[i] == elemento) {
            posicion = i
        }
    }
    return posicion
}

fun miJoinToString(array: IntArray, inicio: String = "", fin: String = "", separador: String = ","): String {
    var resultado = inicio
    for (i in array.indices) {
        resultado += array[i]
        if (i < array.lastIndex) {
            resultado += separador
        }
    }
    resultado += fin
    return resultado
}

fun crearArrayPorTeclado(): IntArray {
    var tam = 0
    do {
        println("Introduce el tamaño del array")
        tam = readln().toIntOrNull() ?: 0
        if (tam <= 0) {
            println("El tamaño debe ser mayor a 0")
        }
    } while (tam <= 0)
    val array = IntArray(tam)
    for (i in array.indices) {
        var input = 0
        do {
            println("Introduce el elemento ${i + 1} del array")
            input = readln().toIntOrNull() ?: 0
            if (input <= 0) {
                println("El elemento debe ser mayor a 0")
            } else {
                array[i] = input
            }
        } while (input <= 0)
    }
    return array
}

fun imprimirArrayDos(array: IntArray) {
    println("El array es:")
    for (numero in array) {
        print("$numero ")
    }
    println()
}

fun media(vector: IntArray): Double {
    var suma = 0
    for (numero in vector) {
        suma += numero
    }
    val med = suma.toDouble() / vector.size
    return med
}

fun sumaParesImpares(vector: IntArray): IntArray {
    var sumaPares = 0
    var sumaImpares = 0
    var sumaParImpar = IntArray(2)
    for (numero in vector) {
        if (numero % 2 == 0) {
            sumaPares += numero
        } else {
            sumaImpares += numero
        }
    }
    sumaParImpar[0] = sumaPares
    sumaParImpar[1] = sumaImpares
    return sumaParImpar
}

fun sumaPosicionParesHumano(vector: IntArray): Int {
    var suma = 0
    for (i in vector.indices) {
        if (i < vector.lastIndex && vector[i + 1] % 2 == 0) {
            suma += vector[i + 1]
        }
    }
    return suma
}

fun numImpares(vector: IntArray): Int {
    var numImpares = 0
    for (numero in vector) {
        if (numero % 2 != 0) {
            numImpares++
        }
    }
    return numImpares
}

fun numPares(vector: IntArray): Int {
    var numPares = 0
    for (numero in vector) {
        if (numero % 2 == 0) {
            numPares++
        }
    }
    return numPares
}

fun tamaño(vector: IntArray): Int {
    var tamaño = 0
    for (numero in vector) {
        tamaño++
    }
    return tamaño
}

fun sumaPosicionPares(vector: IntArray): Int {
    var suma = 0
    for (i in vector.indices) {
        if (i % 2 == 0) {
            suma += vector[i]
        }
    }
    /*for ((i, v) in vector.withIndex()) {
        if (i % 2 == 0) {
            suma += v // vector[i]
        }
    }*/
    return suma

}

fun suma(vector: IntArray): Int {
    var suma = 0
    for (numero in vector) {
        suma += numero
    }
    return suma
}

fun minimo(vector: IntArray): Int {
    var minimoLocal = vector[0]
    for (numero in vector) {
        if (numero < minimoLocal) {
            minimoLocal = numero
        }
    }
    return minimoLocal
}

fun minimiHaciaAtras(vector: IntArray): Int {
    var minimoLocal = vector.last()
    for (numero in vector.reversed()) {
        if (numero < minimoLocal) {
            minimoLocal = numero
        }
    }
    return minimoLocal
}

fun maximo(vector: IntArray): Int {
    var maxLocal = vector.first() // vector[0]
    for (numero in vector) {
        if (numero > maxLocal) {
            maxLocal = numero
        }
    }
    return maxLocal
}
