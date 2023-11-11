package parametro_funciones

fun main() {
    cincoPorElX(1, "hola", 2.0, true)
    cincoPorElX(num = 1, cadena = "hola", double = 2.0, otro = true)
    // Con parametros normbrados puedo cambiar el orden
    cincoPorElX(cadena = "hola", double = 2.0, otro = true, num = 1)
    // Si tienen valores por defecto no es necesario pasarlos
    cincoPorElX(cadena = "hola", double = 2.0)
    // llamamos a numeroVariableParametros con 6 parametros
    numeroVariableParametros(1, 2, 3, 4, 5, 6)
// llamamos a numeroVariableParametros con 2 parametros
    numeroVariableParametros(1, 2)
    // llamamos a numeroVariableParametros con 0 parametros
    numeroVariableParametros()
    // Le puedo pasar un array
    val array = intArrayOf(1, 2, 3, 4, 5)
    numeroVariableParametros(1, *array)
    // Media de infinitos numeros --- Ni de coña!!!
    mediaInfinitosNumeros(
        1,
        2,
        3,
        4,
        5,
        6,
        7,
        8,
        9,
        10,
        11,
        12,
        13,
        14,
        15,
        16,
        17,
        18,
        19,
        20,
        21,
        22,
        23,
        24,
        25,
        26,
        27,
        28,
        29,
        30,
        31,
        32,
        33,
        34,
        35,
        36,
        37,
        38,
        39,
        40,
        41,
        42,
        43,
        44,
        45,
        46,
        47,
        48,
        49,
        50,
        5
    )
    val array2 = intArrayOf(1, 2, 3, 4, 5)
    mediaInfinitosNumeros(*array2)
}

fun cincoPorElX(num: Int = 1, cadena: String, double: Double, otro: Boolean = false) {
    println()
    println("num: $num")
    println("cadena: $cadena")
    println("double: $double")
    println("otro: $otro")
}

// vararg indica que puede recibir un numero variable de parametros del mismo tipo
fun numeroVariableParametros(num: Int = 1, vararg numeros: Int) {
    println()
    println("num: $num")
    println("numeros: ${numeros.contentToString()}")
}

fun mediaInfinitosNumeros(vararg numeros: Int) {
    var suma = 0
    for (numero in numeros) {
        suma += numero
    }
    val media = suma / numeros.size
    println("La media es: $media")
}