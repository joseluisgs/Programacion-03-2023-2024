fun main(args: Array<String>) {
    println("Hola Arrays")
    // Array de enteros de tamaño 6
    val enteros = IntArray(6) // No es obliatorio decirle el valor por defecto

    println("tamaño: " + enteros.size) // 6
    /*


     // No por dios!!!!
     for (i in 0..(enteros.size - 1)) {
         println(enteros[i])
     }

     println()
     // Menor estricto!!!
     for (i in 0 until enteros.size) {
         println(enteros[i])
     }

     println()
     // Menor estricto pero más bonito!!!
     for (i in 0..<enteros.size) {
         println(enteros[i])
     }

     println()
     for (i in enteros.indices) {
         println(enteros[i])
     }

     // Ahora lo recorremos hacia antrás
     for (i in enteros.size - 1 downTo 0) {
         println(enteros[i])
     }

     println()
     for (i in enteros.lastIndex downTo 0) {
         println(enteros[i])
     }
     println()
     for (i in enteros.reversed()) {
         println(enteros[i])
     }

     println()
     for (i in enteros.indices.reversed()) {
         println(enteros[i])
     }

     // paso para ir hacia adelante de 2
     println()
     for (i in enteros.indices step 2) {
         println(enteros[i])
     }

     println()
     // paso para ir hacia atras de 2
     for (i in enteros.lastIndex downTo 0 step 2) {
         println(enteros[i])
     }
     println()
     // También puedo usar una desestructuracion
     for (i in enteros.indices) {
         println("$i: ${enteros[i]}")
     }
     println()
     // También puedo usar una desestructuracion
     for ((index, value) in enteros.withIndex()) {
         println("$index: $value")
     }*/

    /*imprimirArray(enteros)
    println()
    initArray(enteros)
    imprimirArray(enteros)
    var nuevoArray = crearArray(10)
    println()
    imprimirArray(nuevoArray)
    var nuevoArray2 = IntArray(10)
    crearArray2(nuevoArray2)
    imprimirArray(nuevoArray2)*/
    // tablaMultiplicar()

    val numeros = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    // tamaño
    println("Tamaño: ${numeros.size}")
    // primer elemento
    println("Primer elemento: ${numeros.first()}") // en vez de numeros[0]
// último elemento
    println("Último elemento: ${numeros.last()}") // en vez de numeros[numeros.size - 1]
// a string
    println("Array a string: ${numeros.contentToString()}") // 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
// a string con separador
    println("Array a string con separador: ${numeros.joinToString(" - ")}") // 1 - 2 - 3 - 4 - 5 - 6 - 7 - 8 - 9 - 10
// saber si existe en qué índice esta
    println("Índice del elemento 5: ${numeros.indexOf(5)}") // 4
// Subarray
    println("Subarray: ${numeros.sliceArray(0..4).contentToString()}")
    println(
        "Array a string con separador: ${
            numeros.joinToString(
                separator = " - ",
                prefix = "(",
                postfix = ")"
            )
        }"
    ) // (1 - 2 - 3 - 4 - 5 - 6 - 7 - 8 - 9 - 10)

}

fun crearArray2(miArray: IntArray) {
    for (i in miArray.indices) {
        miArray[i] = i
    }
}

fun crearArray(tam: Int): IntArray {
    val array = IntArray(tam)
    for (i in array.indices) {
        array[i] = i + 1
    }
    return array
}

fun imprimirArray(enteros: IntArray) {
    println("Imprimir Array")
    for (i in enteros.indices) {
        println("$i: ${enteros[i]}")
    }
}

fun initArray(enteros: IntArray) {
    println("Inicializar Array")
    for (i in enteros.indices) {
        enteros[i] = (1..10).random()
    }
}

fun tablaMultiplicar() {
    // introduce por teclad el número a multiplicar por los 10 primeros valors
    val intArray = IntArray(10)

    println("Introduce un número: ")
    val numero = readln().toInt()

    for (i in intArray.indices) {
        intArray[i] = numero * (i + 1)
    }

    for (i in intArray.indices) {
        println("$numero x ${i + 1} = ${intArray[i]}")
    }

    println()
    // otra forma con withIndex
    for ((index, value) in intArray.withIndex()) {
        println("$numero x ${index + 1} = $value")
    }
}

