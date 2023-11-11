package argumentos

fun main(args: Array<String>) {
    println(args.contentToString())
    if (args.isEmpty()) {
        println("No hay argumentos")
    } else {
        println("Hay argumentos")
        for (arg in args) {
            println(arg)
        }
    }
}