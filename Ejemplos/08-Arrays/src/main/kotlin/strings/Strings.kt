package strings

fun main() {
    var myString = "Hola 2ºDAW soy un string"
    println(myString)
    println(myString.length)
    var primeraLetra = myString[0]
    println(primeraLetra)
    var ultimaLetra = myString[myString.length - 1]
    println(ultimaLetra)
    primeraLetra = myString.first()
    println(primeraLetra)
    ultimaLetra = myString.last()
    println(ultimaLetra)

    // Substring
    var subString = myString.substring(5, 10)
    println(subString)

    subString = myString.slice(5..10)
    println(subString)

    var subArray = myString.toCharArray()
    println(subArray.contentToString())

    // mayusculas
    println(myString.uppercase())
    // minusculas
    println(myString.lowercase())

    // trim
    myString = "   Hola 2ºDAW soy un string   "
    println(myString.trim())

    var mySplit = myString.trim().split(" ").toTypedArray()
    println(mySplit.contentToString())

    // replace
    myString = "Hola 2ºDAW soy un string"
    println(myString.replace("soy", "estoy"))

    // contains
    println(myString.contains("soy"))

    // indexOf
    println(myString.indexOf("soy"))

    // startsWith
    println(myString.startsWith("Hola"))

    // endsWith
    println(myString.endsWith("string"))

    // Recorre el string
    for (c in myString) {
        println(c)
    }



    myString = ""
    println(myString.isEmpty())
    println(myString.isBlank())
    myString = " "
    println(myString.isEmpty())
    println(myString.isBlank())

    // Debes evitar concatenar strings de esta forma o cambiar
    // Su valor constantemente o crearás muchos objetos en memoria
    myString = ""
    for (i in 0..10) {
        myString += i
    }
    println(myString)

    // Para operar con un string mucho lo mejor es usar un StringBuilder
    val myStringBuilder = StringBuilder()
    for (i in 0..10) {
        myStringBuilder.append(i)
    }
    myString = myStringBuilder.toString()
    println(myString)


}