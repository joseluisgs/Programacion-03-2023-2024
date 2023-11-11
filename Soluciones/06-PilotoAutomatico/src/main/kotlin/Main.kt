const val ALTITUD_INDEX = 0
const val VELOCIDAD_INDEX = 1

const val MAX_TIME = 50000
const val PROBABILIDAD_FALLO = 10 // 1%

fun main() {
    val parametrosVuelo = IntArray(2)
    val constantesVuelo = IntArray(2)


    var cronometro = 0

    // Pedimos los parámetros de vuelo
    pedirParametrosDeVuelo(constantesVuelo)

    // Experimento es que empiece cerca de la alitud fijada por arriba o abajo
    parametrosVuelo[ALTITUD_INDEX] = constantesVuelo[ALTITUD_INDEX] + (-200..200).random()
    parametrosVuelo[VELOCIDAD_INDEX] = constantesVuelo[VELOCIDAD_INDEX] + (-200..200).random()

    do {
        println("Hola soy el piloto automático mi tiempo es $cronometro")
        // Comprobar y corregir altitud
        val altitud = altitud(parametrosVuelo) // Por utilizar estas funciones
        if (constantesVuelo[ALTITUD_INDEX] > altitud) {
            println("Subiendo el avión de altura")
            aumentar('a', parametrosVuelo)
        } else if (constantesVuelo[ALTITUD_INDEX] < altitud) {
            println("Bajando el avión de altura")
            disminuir('a', parametrosVuelo)
        }

        // Comprobar y corregir velocidad
        // Es una locura hacerlo así se haría como la altura!!!!
        val velocidad = IntArray(1)  // Por utilizar estas funciones
        velocidad(velocidad, parametrosVuelo)
        if (constantesVuelo[VELOCIDAD_INDEX] > velocidad[0]) {
            println("Acelerando el avión de velocidad")
            aumentar('v', parametrosVuelo)
        } else if (constantesVuelo[VELOCIDAD_INDEX] < velocidad[0]) {
            println("Desacelerando el avión de velocidad")
            disminuir('v', parametrosVuelo)
        }

        println("Altura actual: ${parametrosVuelo[ALTITUD_INDEX]}")
        println("Velocidad actual: ${parametrosVuelo[VELOCIDAD_INDEX]}")


        // Al final
        esperar(2000)
        cronometro += 2000
    } while (cronometro <= MAX_TIME && !falloSistema())

    if (cronometro > MAX_TIME) {
        println("El piloto automático ha terminado su tiempo de ejecución")
    } else {
        println("El piloto automático ha detectado un fallo en el sistema volviendo a control manual")
    }
}

fun pedirParametrosDeVuelo(constantesVuelo: IntArray) {
    do {
        println("Introduce la altura de vuelo (entre 1000 y 5000 metros)")
        constantesVuelo[ALTITUD_INDEX] = readln().toIntOrNull() ?: 0
    } while (constantesVuelo[ALTITUD_INDEX] < 1000 || constantesVuelo[ALTITUD_INDEX] > 5000)

    do {
        println("Introduce la velocidad de vuelo (entre 500 y 700 km/h)")
        constantesVuelo[VELOCIDAD_INDEX] = readln().toIntOrNull() ?: 0
    } while (constantesVuelo[VELOCIDAD_INDEX] < 500 || constantesVuelo[VELOCIDAD_INDEX] > 700)
}

fun falloSistema() = ((1..100).random() <= PROBABILIDAD_FALLO)


fun altitud(parametrosVuelo: IntArray): Int {
    return parametrosVuelo[ALTITUD_INDEX]
}

// Esto es una locura hacerlo así para un solo parámetro y se haría como la altura
fun velocidad(velocidad: IntArray, parametrosVuelo: IntArray) {
    velocidad[0] = parametrosVuelo[VELOCIDAD_INDEX]
}

fun esperar(tiempo: Int) {
    Thread.sleep(tiempo.toLong())
}

fun aumentar(parametro: Char, parametrosVuelo: IntArray) {
    when (parametro) {
        'a' -> parametrosVuelo[ALTITUD_INDEX] += 10
        'v' -> parametrosVuelo[VELOCIDAD_INDEX] += 10
    }
}

fun disminuir(parametro: Char, parametrosVuelo: IntArray) {
    when (parametro) {
        'a' -> parametrosVuelo[ALTITUD_INDEX] -= 10
        'v' -> parametrosVuelo[VELOCIDAD_INDEX] -= 10
    }
}
