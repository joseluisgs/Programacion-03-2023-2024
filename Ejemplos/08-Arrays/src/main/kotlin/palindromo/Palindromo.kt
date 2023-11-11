package palindromo

fun main() {
    println("Ingrese una palabra: ")
    val palabra = readln().lowercase()

    // Pro, que lo haga Kotlin por mi y el profe le pone a Kotlin un 10
    // y a mi un 0
    if (palabra == palabra.reversed()) {
        println("La palabra es palíndromo")
    } else {
        println("La palabra no es palíndromo")
    }

    // Hacerlo con un for y los indices
    var esPalindromo = true
    for (i in 0..palabra.length / 2) {
        if (palabra[i] != palabra[palabra.length - i - 1]) {
            esPalindromo = false
            break
        }
    }

    if (esPalindromo) {
        println("La palabra es palíndromo")
    } else {
        println("La palabra no es palíndromo")
    }

    // Otra forma con un while y jugando con los indices
    // de inicio y final
    esPalindromo = true
    var inicio = 0
    var final = palabra.length - 1
    while (inicio < final) {
        if (palabra[inicio] != palabra[final]) {
            esPalindromo = false
            break
        }
        inicio++
        final--
    }

    if (esPalindromo) {
        println("La palabra es palíndromo")
    } else {
        println("La palabra no es palíndromo")
    }


}