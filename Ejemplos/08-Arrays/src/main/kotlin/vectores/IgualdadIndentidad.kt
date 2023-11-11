package vectores

fun main() {
    val a = arrayListOf(1, 2, 3, 4, 5)
    val b = arrayListOf(1, 2, 3, 4, 5)
    val c = b

    println("a == b: ${a == b}") // igualdad de contenido, true
    println("a === b: ${a === b}") // igualdad de identidad, false

    println("a == c: ${a == c}") // igualdad de contenido, true
    println("a === c: ${a === c}") // igualdad de identidad, false

    println("b == c: ${b == c}") // igualdad de contenido, true
    println("b === c: ${b === c}") // igualdad de identidad, true

    if (a === b) {
        println("a y b son el mismo objeto")
    } else {
        println("a y b no son el mismo objeto")
    }

    if (a == b) {
        println("a y b tienen el mismo contenido")
    } else {
        println("a y b no tienen el mismo contenido")
    }


}