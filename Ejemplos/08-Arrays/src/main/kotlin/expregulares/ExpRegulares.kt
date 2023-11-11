package expregulares

const val NOMBRE = "Pepe"

fun main() {
    // Sin expresiones regulares
    /*var numero: Int
    do {
        println("Introduce un numero entre 1 y 6")
        numero = readln().toIntOrNull() ?: 0
    } while (numero !in 1..6)

    println("El numero introducido es $numero")*/

    // Con expresiones regulares
    /*var cad = ""
    var correcto = false
    var inputRegex = "^[0-9]$".toRegex()
    do {
        println("Introduce un numero entre 1 y 6")
        cad = readln()
        correcto = cad.sp
    } while (!correcto)
    println("El numero introducido es $cad")*/

    // Saber si ha pulsado s o S o n o N
    /*var cad = ""
    var correcto = false
    var inputRegex = "^[sSnN]$".toRegex()
    do {
        println("Introduce s/S o n/N")
        cad = readln()
        correcto = cad.matches(inputRegex)
    } while (!correcto)

    if (cad == "s" || cad == "S") {
        println("Has pulsado s")
    } else if (cad == "n" || cad == "N") {
        println("Has pulsado n")
    }*/

    // Solo sirve las palabras magicas CASA o variaciones de la misma y Gato o variaciones de la misma en mayuscula y minuscula
    /*var cad = ""
    var correcto = false
    var inputRegex = "^(?i)(casa|gato)$".toRegex()
    do {
        println("Introduce casa/Gato")
        cad = readln()
        correcto = cad.matches(inputRegex)
    } while (!correcto)

    println("Has introducido $cad")*/

    // DNI Español, 8 numeros y una letra Mayúscula la letra no puede ser: I, Ñ, O, U
    /*var cad = ""
    var correcto = false
    val inputRegex = "^[0-9]{8}[A-HJ-NP-TV-Z]$".toRegex()
    do {
        println("Introduce DNI")
        cad = readln()
        correcto = cad.matches(inputRegex)
    } while (!correcto)

    println("Has introducido $cad")*/

    // Matrícula de coche Española
    /*var cad = ""
    var correcto = false
    var inputRegex = "^[0-9]{4}[A-Z]{3}$".toRegex()
    do {
        println("Introduce Matrícula")
        cad = readln()
        correcto = cad.matches(inputRegex)
    } while (!correcto)
    println("Has introducido $cad")*/

    // Mac Address
    /*var cad = ""
    var correcto = false
    var inputRegex = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$".toRegex()
    do {
        println("Introduce Mac Address")
        cad = readln()
        correcto = cad.matches(inputRegex)
    } while (!correcto)
    println("Has introducido $cad")*/

    // Correo Electrónico
    /*var cad = ""
    var correcto = false
    var inputRegex =
        "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$".toRegex()
    do {
        println("Introduce Correo Electrónico")
        cad = readln()
        correcto = cad.matches(inputRegex)
    } while (!correcto)
    println("Has introducido $cad")*/

    // Ejemplo para saber si un DNI Español es válido y la letra es correcta
    /* val dni = leerDniDesdeConsola()
     if (isDniCorrecto(dni)) {
         println("El DNI $dni es correcto")
     } else {
         println("El DNI $dni no es correcto")
     }*/


    var cadena =
        """El signo de interrogación tiene varias funciones dentro del lenguaje pepe@alumno.iesluisvives.org de las expresiones regulares. La primera de ellas es especificar que una parte de la búsqueda es opcional. Por ejemplo, la expresión regular ob?scuridad permite encontrar tanto \"oscuridad\" como \"obscuridad\". En conjunto con los paréntesis redondos permite especificar que un conjunto mayor de caracteres es opcional; por ejemplo Nov(\\.|iembre|ember)? permite encontrar tanto \"Nov\" como \"Nov.\", \"Noviembre\" y \"November\"."""
    println(cadena)

    var numPalabras = cadena.split("\\s+".toRegex()).toTypedArray()
    println("El número de palabras es ${numPalabras.size}")
    for (palabra in numPalabras) {
        println(palabra)
    }

    var emailLuisVivesRegex = "[a-z]{4,}@alumno.iesluisvives.org".toRegex()
    if (emailLuisVivesRegex.matches(cadena)) {
        println("El correo electrónico es válido")
    } else {
        println("El correo electrónico no es válido")
    }

    // Aparece? Find
    var res = emailLuisVivesRegex.find(cadena)
    if (res != null) {
        println("El correo electrónico si está en toda la cadena")
        println("El correo electrónico es ${res.value}")
    } else {
        println("El correo electrónico no está en la cadena")
    }


}


/**
 * Función para leer un DNI desde consola
 * @return DNI que se ha leído desde consola
 * @author José Luis González
 * @since 1.0
 */
fun leerDniDesdeConsola(): String {
    var cad: String
    var correcto: Boolean
    var inputRegex = "^/d{8}[A-HJ-NP-TV-Z]$".toRegex()
    do {
        println("Introduce DNI")
        cad = readln()
        correcto = cad.matches(inputRegex)
    } while (!correcto)
    return cad
}

/**
 * Función para saber si un DNI es correcto en base a su letra
 * @param dni DNI que se quiere comprobar
 * @return true si el DNI es correcto, false en caso contrario
 * @see leerDniDesdeConsola
 * @sample 56789987A
 */
fun isDniCorrecto(dni: String): Boolean {
    val letras = "TRWAGMYFPDXBNJZSQVHLCKE"
    val num = dni.substring(0, 8).toInt()
    val resto = num % 23
    val miLetra = dni.last()
    return letras[resto] == miLetra
}

