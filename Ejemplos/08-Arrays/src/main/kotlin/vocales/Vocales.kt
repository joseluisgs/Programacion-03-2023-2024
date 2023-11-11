package vocales

fun main() {
    println("Introduce una cadena: ")
    val cadena = readln().lowercase()
    var numVcales = 0
    for (i in cadena.indices) {
        if (cadena[i] == 'a' || cadena[i] == 'e' || cadena[i] == 'i' || cadena[i] == 'o' || cadena[i] == 'u') {
            numVcales++
        }
    }
    println("La cadena $cadena tiene $numVcales vocales")
    println()
    numVcales = 0

    var vocales = charArrayOf('a', 'e', 'i', 'o', 'u')
    for (i in cadena.indices) {
        if (vocales.contains(cadena[i])) {
            numVcales++
        }
    }
    println("La cadena $cadena tiene $numVcales vocales")
    println()
    numVcales = 0

    // Forma pro!!
    for (i in cadena.indices) {
        if (cadena[i] in vocales) {
            numVcales++
        }
    }
    println("La cadena $cadena tiene $numVcales vocales")

    // Cuatas veces aparece cada vocal
    val numVecesVocales = IntArray(5)
    for (i in cadena.indices) {
        if (cadena[i] == 'a') {
            numVecesVocales[0]++
        } else if (cadena[i] == 'e') {
            numVecesVocales[1]++
        } else if (cadena[i] == 'i') {
            numVecesVocales[2]++
        } else if (cadena[i] == 'o') {
            numVecesVocales[3]++
        } else if (cadena[i] == 'u') {
            numVecesVocales[4]++
        }
    }

    // Con when
    for (i in cadena.indices) {
        when (cadena[i]) {
            'a' -> numVecesVocales[0]++
            'e' -> numVecesVocales[1]++
            'i' -> numVecesVocales[2]++
            'o' -> numVecesVocales[3]++
            'u' -> numVecesVocales[4]++
        }
    }

    // Forma pro!! (ojo que puede que no sea tan pro!!)
    for (i in cadena.indices) {
        if (cadena[i] in vocales) {
            numVecesVocales[vocales.indexOf(cadena[i])]++
        }
    }


}