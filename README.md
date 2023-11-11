# Programación - 03 Aplicación de Estructuras de Almacenamiento

Tema 03 Aplicación de Estructuras de Almacenamiento. 1DAW. Curso 2023/2024.

![imagen](https://raw.githubusercontent.com/joseluisgs/Programacion-00-2022-2023/master/images/programacion.png)

- [Programación - 03 Aplicación de Estructuras de Almacenamiento](#programación---03-aplicación-de-estructuras-de-almacenamiento)
  - [Contenidos](#contenidos)
  - [Arrays unidimensionales](#arrays-unidimensionales)
    - [Definición de un array](#definición-de-un-array)
    - [Recorrido](#recorrido)
    - [Paso por referencia](#paso-por-referencia)
    - [Devolución de arrays](#devolución-de-arrays)
    - [Identidad vs Igualdad](#identidad-vs-igualdad)
    - [Métodos y propiedades](#métodos-y-propiedades)
  - [Arrays multidimensionales](#arrays-multidimensionales)
    - [Definición](#definición)
    - [Recorrido](#recorrido-1)
  - [Cadena o Strings](#cadena-o-strings)
    - [Definición](#definición-1)
    - [Recorrido](#recorrido-2)
    - [Métodos y operadores](#métodos-y-operadores)
    - [StringBuilder](#stringbuilder)
  - [Expresiones regulares](#expresiones-regulares)
    - [Definición](#definición-2)
    - [Métodos y operadores](#métodos-y-operadores-1)
    - [Ejemplos](#ejemplos)
  - [Paso de parámetros o argumentos en la llamada de un programa](#paso-de-parámetros-o-argumentos-en-la-llamada-de-un-programa)
  - [Métodos de ordenación](#métodos-de-ordenación)
    - [Burbuja](#burbuja)
    - [Selección](#selección)
    - [Inserción](#inserción)
    - [Shell](#shell)
    - [QuickSort](#quicksort)
  - [Métodos de búsqueda](#métodos-de-búsqueda)
    - [Búsqueda secuencial](#búsqueda-secuencial)
    - [Búsqueda binaria](#búsqueda-binaria)
  - [Gestión de proyectos con Gradle](#gestión-de-proyectos-con-gradle)
    - [Plugins](#plugins)
    - [Dependencias](#dependencias)
    - [Creación de jar ejecutable (fat jar)](#creación-de-jar-ejecutable-fat-jar)
    - [Ejecución de tareas](#ejecución-de-tareas)
    - [Instalando la primera dependencia](#instalando-la-primera-dependencia)
  - [Autor](#autor)
    - [Contacto](#contacto)
  - [Licencia de uso](#licencia-de-uso)

## Contenidos
1. Arrays unidimensionales. 
2. Arrays multidimensionales. 
3. Cadenas de caracteres. 
4. Expresiones regulares. 
5. Métodos de ordenación.
6. Métodos de búsqueda.

## Arrays unidimensionales
Un array es una estructura de datos que permite almacenar un conjunto de datos contiguos del mismo tipo. Se caracterizan por tener un tamaño fijo, es decir, una vez definido el array no se puede modificar su tamaño y que su acceso de lectura y escritura se realiza mediante un índice.

Si una variable es como un cajón de un tamaño del tipo de dato (es decir, el indentificador apunta a la zona de memoria donde se almacena el valor), un array puede verse como un conjunto de cajones (una cajonera) del mismo tamaño del tipo de dato, donde cada cajón tiene un índice que nos permite acceder a él. Por tanto, un array es una estructura de datos que nos permite almacenar un conjunto de datos del mismo tipo.

En Kotlin y Java, el primer índice es el 0. Esto se debe  a la forma a de redireccionar de memoria que han ido heredando los lenguajes y compiladores (como en C), el primer elemento de un array se almacena en la posición 0. Por tanto, si tenemos un array de 10 elementos, el último elemento se almacena en la posición 9. Si no salimos del límite obtendremos una excepción (tanto si consultamos como índice un número negativo, como si consultamos un índice mayor o igual al tamaño del array). Esta excepción es ArrayIndexOutOfBoundsException. Si te ocurriera, no te preocupes, es normal, y es una excepción muy común al comenzar, revisa tu código y verás que estás accediendo a un índice que no existe.

![array](./images/arrays.jpg)

### Definición de un array
La definición de un array es muy sencilla. Para ello, debemos indicar el tipo de datos que vamos a almacenar y el tamaño del array. Su tamaño viene dado por size. Por ejemplo, si queremos almacenar 10 números enteros, lo haríamos de la siguiente forma:

```kotlin
// Array de 10 enteros
var arrayEnteros = IntArray(10) // valor inicial 0
// Otra forma si definimos su contenido
var arrayEnteros = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
// O asignarle un valor inicial a todos
var arrayEnteros = IntArray(10) { 0 } // array de 10 enteros inicializados a 0
// array de characteres
var arrayCaracteres = CharArray(10) 
// array de booleanos
var arrayBooleanos = BooleanArray(10) 
// Hay otros tipos de arrays como LongArray, DoubleArray, FloatArray, ShortArray, ByteArray

// Podemos definir un array de un tipo de dato que no tiene un constructor, podemos utilizar la función arrayOf() usando el tipo de dato
var arrayCadenas = arrayOf("Hola", "mundo")
var arrayEnteros = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

// Si necesitamos definir un array de un tipo de dato podemos usar Array<Tipo>(size) o Array<Tipo>(size) { valorInicial }. Es crucial usar el constructor de Array, ya que si no, no se creará el array, sino que se creará un objeto de tipo Array.

var arrayEnteros = Array<Int>(10) { 0 } // array de 10 enteros inicializados a 0, similar a IntArray(10) { 0 }
var arrayCadenas = Array<String>(10) { "" } // array de 10 cadenas inicializados a "", similar a arrayOf("", "", "", "", "", "", "", "", "", "")

```
### Recorrido
Para recorrer un array y trabajar con él, el índice será clave. Para ello, podemos utilizar un bucle *for* o *for-each* o *while*, que nos permitirá recorrer el array de principio a fin. En el caso de *for-each*, no tendremos que indicar el índice, sino que se recorrerá el array de principio a fin dependiendo de su dirección.  Debes elegir la que mejor se adapte a tu situación. A veces es mejor usar un bucle *for* con índices (si lo necesitamos usar), otras veces un bucle *for-each* (si queremos solo los elelemntos de principio a final) y otras veces un bucle *while* (sobre todo si el índice cambia y no es secuencial).

```kotlin
fun main() {
    val array = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    
    // Recorrido con for indices
    for (i in array.indices) {
        println(array[i])
    }
    // Con reversed con indices
    for (i in array.indices.reversed()) {
        println(array[i])
    }
    
    // Con for each
    for (i in array) {
        println(i)
    }
    // Con for each con reversed
    for (i in array.reversed()) {
        println(i)
    }

    // Saltando posiciones
    for (i in array.indices step 2) {
        println(array[i])
    }
    // Con reversed
    for (i in array.indices.reversed() step 2) {
        println(array[i])
    }

    // con for until
    for (i in 0 until array.size) {
        println(array[i])
    }

    for (i in 0..< array.size) {
        println(array[i])
    }
    // descendente saltando de dos en dos con until
    for (i in array.size - 1 downTo 0 step 2) {
        println(array[i])
    }

    // Si no queremos usar size, o until, podemos usar lastIndex
    for (i in 0..array.lastIndex) {
        println(array[i])
    }
    // descendente con lastIndex
    for (i in array.lastIndex downTo 0) {
        println(array[i])
    }

    // Con un while
    var i = 0
    while (i < array.size) {
        println(array[i])
        i++
    }

    // descente con un while
    var j = array.size - 1
    while (j >= 0) {
        println(array[j])
        j--
    }

    // With index, para obtener el indice y el valor en variables
    for (element in array.withIndex()) {
        println("El elemento ${element.value} está en la posición ${element.index}")
    }
    // Desestructurando
    for ((index, value) in array.withIndex()) {
        println("El elemento $value está en la posición $index")
    }

    // With Index Reversed
    for (element in array.withIndex().reversed()) {
        println("El elemento ${element.value} está en la posición ${element.index}")
    }
    // Desestructurando
    for ((index, value) in array.withIndex().reversed()) {
        println("El elemento $value está en la posición $index")
    }

    // withIndex y con step 2
    for (element in array.withIndex().step(2)) {
        println("El elemento ${element.value} está en la posición ${element.index}")
    }
    // Desestructurando
    for ((index, value) in array.withIndex().step(2)) {
        println("El elemento $value está en la posición $index")
    }

    // withIndex y con step 2 reversed
    for (element in array.withIndex().reversed().step(2)) {
        println("El elemento ${element.value} está en la posición ${element.index}")
    }
    // Desestructurando
    for ((index, value) in array.withIndex().reversed().step(2)) {
        println("El elemento $value está en la posición $index")
    }

}
```
### Paso por referencia
Los arrays siempre pasan por referencia en las funciones, esto quiere decir, que si modificamos un array en una función, este cambio será visible después de ejecutar la función. Por lo tanto ten precaución porque esto puede provocar efectos secundarios no deseados, pero a la vez puede ser muy útil.

```kotlin
fun main() {
    val array = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println("Array original: ${array.contentToString()}")
    // Modificamos el array
    modificarArray(array)
    println("Array modificado: ${array.contentToString()}")
}

fun modificarArray(array: IntArray) {
    array[0] = 100
    array[1] = 200
    array[2] = 300
}
```

### Devolución de arrays
Para devolver un array, podemos utilizar la palabra reservada return, pero en este caso, no devolveremos el array, sino que devolveremos la referencia a la posición de memoria donde se encuentra el array. Por lo tanto, si modificamos el array devuelto, también se modificará el array original.

```kotlin
fun main() {
    val array = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println("Array original: ${array.contentToString()}")
    // Modificamos el array
    val array2 = modificarArray(array)
    println("Array modificado: ${array.contentToString()}")
    println("Array2 modificado: ${array2.contentToString()}")
}

fun modificarArray(array: IntArray): IntArray {
    array[0] = 100
    array[1] = 200
    array[2] = 300
    return array
}
```

### Identidad vs Igualdad
Uno de los problemas fundamentales en la programación es la identidad y la igualdad.
- Se dice que dos objetos son **idénticos** si apuntan a la misma posición de memoria, es decir, si son el mismo objeto. Para comprobar si dos objetos son idénticos, podemos utilizar el operador ===.
- Se dice que dos objetos son **iguales** si tienen el mismo contenido, es decir, si tienen el mismo valor. Para comprobar si dos objetos son iguales, podemos utilizar el operador == o el método equals().

Los arrays son referencias a posiciones de memorias esto quiere decir que podemos tener dos referencias a la misma posición de memoria, por lo que si modificamos una de ellas, la otra también se verá afectada.

Dos arrays son **idénticos** si apuntan a la misma posición de memoria, para comprobar si dos arrays son idénticos, podemos utilizar el operador ===.

Dos arrays son **iguales** si tienen el mismo contenido, es decir, si tienen el mismo tamaño y los mismos elementos en la misma posición. Para comprobar si dos arrays son iguales, podemos utilizar el operador == o el método contentEquals().

Para comparar dos arrays, podemos implementar nuestro propio método igualdad o usar el método contentEquals(). Este método nos permite comparar dos arrays y nos devuelve true si son iguales y false si no lo son.

```kotlin
fun main() {
  val arrayA = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
  val arrayB = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

  println("Array A: ${arrayA.contentToString()}")
  println("Array B: ${arrayB.contentToString()}")
  // Igualdad
  println("Son iguales: ${igualdad(arrayA, arrayB)}")
  // ==
  println("Son iguales: ${arrayA == arrayB}")
  // contentEquals
  println("Son iguales: ${arrayA.contentEquals(arrayB)}")
}

fun igualdad(origen:IntArray, destino: IntArray): Boolean {
  // Early exit
  if (origen.size != destino.size) {
    return false
  }
  for (i in origen.indices) {
    if (origen[i] != destino[i]) {
      return false
    }
  }
  return true
}
```

Para crear un vector igual a otro, podemos utilizar el método copyOf() o el método copyOfRange(), o crear la función clonar().

```kotlin
fun main() {
  val arrayA = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
  val arrayB = arrayA.copyOf()
  val arrayC = arrayA.copyOfRange(0, 5)
  val arrayD = clonar(arrayA)
  println("Array A: ${arrayA.contentToString()}")
  println("Array B: ${arrayB.contentToString()}")
  println("Array C: ${arrayC.contentToString()}")
  println("Array D: ${arrayD.contentToString()}")
}

fun clonar(origen: IntArray, init: Int = 0, end: Int = arrayOne.lastIndex): IntArray {
    val inicio = if (init < 0) 0 else init
    val fin = if (end > origen.lastIndex) origen.lastIndex else end
    val arrayClonado = IntArray(fin - inicio + 1)
    for (i in inicio..fin) {
        arrayClonado[i - inicio] = origen[i]
    }
    return arrayClonado
}
```

### Métodos y propiedades
Algunos métodos interesantes
```kotlin
val numeros = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
// tamaño
println("Tamaño: ${numeros.size}")
// primer elemento
println("Primer elemento: ${numeros.first()}")
// último elemento
println("Último elemento: ${numeros.last()}")
// a string 
println("Array a string: ${numeros.contentToString()}")
// a string con separador
println("Array a string con separador: ${numeros.joinToString(" - ")}")
// saber si existe en qué índice esta
println("Índice del elemento 5: ${numeros.indexOf(5)}")
// Subarray
println("Subarray: ${numeros.sliceArray(0..4).contentToString()}")
```

## Arrays multidimensionales
Los arrays multidimensionales son arrays que contienen otros arrays, por ejemplo, un array de dos dimensiones es un array donde cada elemento es un array. Es decir, es un array de arrays. Para recorrerlo usaremos tanto índices como dimensiones tenga.
Todo lo explicado para los arrrays unidimensionales es aplicable a los arrays multidimensionales.

![Arrays multidimensionales](./images/matrix.jpg)

### Definición
Para definir un array multidimensional, podemos utilizar la función arrayOf() de la función arrayOfNulls() o Array(filas) { TipoArray(columnas) }.

```kotlin
val array = arrayOf(arrayOf(1, 2, 3), arrayOf(4, 5, 6), arrayOf(7, 8, 9))
val array2 = arrayOfNulls<Int>(3)
val array3 = Array(3) { IntArray(3) } // array de 3x 3
```

### Recorrido
Necesitaremos tantos índices como dimensiones, y por lo tanto tantos bucles definidos como dimensiones
```kotlin
// Usando índices
for (i in array.indices) {
  for (j in array[i].indices) {
    println("array[$i][$j] = ${array[i][j]}")
  }
}

// Usando rangos
for (i in 0 until array.size) {
  for (j in 0 until array[i].size) {
    println("array[$i][$j] = ${array[i][j]}")
  }
}

// con while
var i = 0
while (i < array.size) {
  var j = 0
  while (j < array[i].size) {
    println("array[$i][$j] = ${array[i][j]}")
    j++
  }
  i++
}

// Con for each
for (fila in array) {
  for (columna in fila) {
    for (element in columna) {
      println("element = $element")
    }
  }
}
``` 

## Cadena o Strings
Una cadena es una secuencia de caracteres, es decir, una cadena es un array de caracteres. En Kotlin, las cadenas son inmutables, es decir, no podemos modificar una cadena, si queremos modificar una cadena, tendremos que crear una nueva cadena.

### Definición
Para definir una cadena, podemos utilizar las comillas dobles
```kotlin
val cadena = "Hola mundo"
```

### Recorrido
Para recorrerlas podemos hacer uso del bucle for
```kotlin
for (caracter in cadena) {
  println("caracter = $caracter")
}

// Con índices
for (i in cadena.indices) {
  println("cadena[$i] = ${cadena[i]}")
}
```

### Métodos y operadores
Existen algunos métodos útiles
```kotlin
// Longitud
println("Longitud: ${cadena.length}")
// Obtener un carácter
println("Carácter: ${cadena[0]}")
// Obtener un subcadena
println("Subcadena: ${cadena.substring(0,4)}")
// Obtener un array de caracteres
println("Array de caracteres: ${cadena.toCharArray().contentToString()}")
// Obtener un array de cadenas
println("Array de cadenas: ${cadena.split(" ").contentToString()}")
// Concatenación
println("Concatenación: ${cadena + "!!!"}")
// Comparación
println("Son iguales: ${cadena == "Hola mundo"}")
// pasar a mayúsculas
println("Mayúsculas: ${cadena.toUpperCase()}")
// pasar a minúsculas
println("Minúsculas: ${cadena.toLowerCase()}")
// Contiene
println("Contiene: ${cadena.contains("Hola")}")
// primer elemento
println("Primer elemento: ${cadena.first()}")
// último elemento
println("Último elemento: ${cadena.last()}")
// array de cadenas a string
println("Array de cadenas a string: ${arrayOf("Hola", "mundo").contentToString()}")
// array de cadenas a string con separador
println("Array de cadenas a string con separador: ${arrayOf("Hola", "mundo").joinToString(" - ")}")
// Split a array 
val array = cadena.split(" ").toTypedArray()
// Eliminar espacios en blanco
println("Eliminar espacios en blanco: ${cadena.trim()}")
// saber si existe en qué índice está
println("Índice: ${cadena.indexOf("mundo")}")
// Subarray con slice
println("Subarray: ${cadena.slice(0..4)}")
// reemplazar
println("Reemplazar: ${cadena.replace("Hola", "Adiós")}")
```

### StringBuilder
StringBuilder es una clase que nos permite crear cadenas de forma mutable, es decir, podemos modificarlas. Para crear un StringBuilder, podemos utilizar la función StringBuilder() o StringBuilder(cadena).

```kotlin
val sb = StringBuilder()
val sb2 = StringBuilder("Hola mundo")
sb.append("Hola mundo")
val mensaje = sb2.toString()
```

## Expresiones regulares
Una expresión regular es una secuencia de caracteres que forma un patrón de búsqueda, principalmente utilizada para la búsqueda de patrones de cadenas de caracteres u operaciones de sustitución. Puedes definirlas, probarlas y testearlas [aquí](https://regex101.com/).

### Definición
Para definir una expresión regular, podemos utilizar la función Regex()
```kotlin
val regex = Regex("Hola")
val regex2 = "\\d+".toRegex()
```

### Métodos y operadores
Existen algunos métodos útiles
```kotlin
// Comprobar si una cadena cumple el patrón
println("Cumple el patrón: ${regex.matches("Hola mundo")}")
// Obtener un array de cadenas
println("Array de cadenas: ${regex.split("Hola mundo").contentToString()}")
// Reemplazar
println("Array de cadenas: ${regex.replace("Hola mundo", "Adiós mundo")}")
```

### Ejemplos
```kotlin
// Solo números
val regex = "\\d+".toRegex()
println("Solo números: ${regex.matches("1234567890")}")
// Letras minúsculas
val regex = "[a-z]+".toRegex()
println("Letras minúsculas: ${regex.matches("abcdefghijklmnñopqrstuvwxyz")}")
// Letras mayúsculas
val regex = "[A-Z]+".toRegex()
println("Letras mayúsculas: ${regex.matches("ABCDEFGHIJKLMNÑOPQRSTUVWXYZ")}")
// Letras minúsculas y mayúsculas
val regex = "[a-zA-Z]+".toRegex()
println("Letras minúsculas y mayúsculas: ${regex.matches("abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ")}")
// Teléfono
val regex = "\\d{9}".toRegex()
println("Teléfono: ${regex.matches("123456789")}")
// DNI
val regex = "\\d{8}[A-Z]".toRegex()
println("DNI: ${regex.matches("12345678A")}")
// Email
val regex = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}".toRegex()
println("Email: ${regex.matches("pepe@pepilandia.com")}")
// URL
val regex = "^(http|https)://[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}".toRegex()
println("URL: ${regex.matches("http://pepilandia.com")}")
// Tarjeta de crédito
val regex = "\\d{4} \\d{4} \\d{4} \\d{4}".toRegex()
println("Tarjeta de crédito: ${regex.matches("1234 5678 9012 3456")}")
// Fecha
val regex = "\\d{2}/\\d{2}/\\d{4}".toRegex()
println("Fecha: ${regex.matches("01/01/2020")}")
```

## Paso de parámetros o argumentos en la llamada de un programa
Para pasar parámetros a un programa podemos hacerlo por la línea de comandos, para ello solo tenemos que leer el contenido del vector args. Este vector contiene los parámetros que se han pasado al programa. Por ejemplo, si ejecutamos el programa de la siguiente forma:

```bash
java -jar programa.jar 1 2 3 4 5
```

El vector args contendrá los siguientes valores:
```kotlin
args[0] = "1"
args[1] = "2"
args[2] = "3"
args[3] = "4"
args[4] = "5"
```
Para ello, nuestro método main principal debe tener como parámetro un array de cadenas de caracteres (Array<String> o StringArray) llamado args. Por ejemplo:

```kotlin
fun main(args: Array<String>) {
  // ...
}
```

Y ya trabajamos como cualquier array cuyo contenido sean strings.

Por ejemplo un programa que se le pasa una lisa de nombres por parámetros y los muestra por pantalla, solo si hay más de 2 parámetros:

```kotlin
fun main(args: Array<String>) {
  if (args.size > 2) {
    for (nombre in args) {
      println("Hola $nombre")
    }
  }
}
```

## Métodos de ordenación
Los métodos de ordenación nos permite ordenar arrays. Pero ojo, debemos tener en cuenta que dependiendo del método de ordenación así será su eficiencia. Por lo que debemos tener en cuenta el tipo de datos que vamos a ordenar y el tamaño del array.

Puedes visualizarlos [aquí](https://www.cs.usfca.edu/~galles/visualization/Algorithms.html)

### Burbuja
Es un método de [ordenación](https://es.wikipedia.org/wiki/Ordenamiento_de_burbuja) con eficiencia O(n²). Es decir, el tiempo de ejecución es proporcional al cuadrado del tamaño del array. Este método consiste en ir comparando cada elemento con el siguiente, y si el elemento actual es mayor que el siguiente, se intercambian. Este proceso se repite hasta que no se producen más intercambios. Este método es muy sencillo de implementar, pero no es el más eficiente.
- https://www.youtube.com/watch?v=lyZQPjUT5B4


![imagen](https://upload.wikimedia.org/wikipedia/commons/c/c8/Bubble-sort-example-300px.gif)

```kotlin
fun burbuja(array: IntArray) {
  var aux: Int
  for (i in 0 until array.size) {
    for (j in 0 until array.size - 1) {
      if (array[j] > array[j + 1]) {
        aux = array[j]
        array[j] = array[j + 1]
        array[j + 1] = aux
      }
    }
  }
}
```

### Selección
El algoritmo de [selección](https://es.wikipedia.org/wiki/Ordenamiento_por_selecci%C3%B3n). su eficiencia es de O(n²).  Consiste en ir buscando el elemento más pequeño del array y colocarlo en la primera posición, luego el segundo más pequeño y colocarlo en la segunda posición, y así sucesivamente. Este método es más eficiente que el método de la burbuja, ya que solo hace una comparación por cada iteración. En definitiva, en cada iteración, se selecciona el menor elemento del subvector no ordenado y se intercambia con el primer elemento de este subvector.
- https://www.youtube.com/watch?v=Ns4TPTC8whw

![imagen](https://upload.wikimedia.org/wikipedia/commons/9/94/Selection-Sort-Animation.gif)

```kotlin
fun seleccion(array: IntArray) {
  var aux: Int
  var min: Int
  for (i in 0 until array.size) {
    min = i
    for (j in i + 1 until array.size) {
      if (array[j] < array[min]) {
        min = j
      }
    }
    aux = array[i]
    array[i] = array[min]
    array[min] = aux
  }
}
```

### Inserción
El algoritmo de [insercción](https://es.wikipedia.org/wiki/Ordenamiento_por_inserci%C3%B3n). Su eficiencia es O(n²). Es decir, el tiempo de ejecución es proporcional al cuadrado del tamaño del array. Inicialmente se tiene un solo elemento, que obviamente es un conjunto ordenado. Después, cuando hay k elementos ordenados de menor a mayor, se toma el elemento k+1 y se compara con todos los elementos ya ordenados, deteniéndose cuando se encuentra un elemento menor (todos los elementos mayores han sido desplazados una posición a la derecha) o cuando ya no se encuentran elementos (todos los elementos fueron desplazados y este es el más pequeño). En este punto se inserta el elemento k+1 debiendo desplazarse los demás elementos. En definitiva, nn cada iteración, se selecciona el menor elemento del subvector no ordenado y se intercambia con el primer elemento de este subvector.
- https://www.youtube.com/watch?v=ROalU379l3U

![imagen](https://upload.wikimedia.org/wikipedia/commons/0/0f/Insertion-sort-example-300px.gif)

```kotlin
fun insercion(array: IntArray) {
  var aux: Int
  var j: Int
  for (i in 1 until array.size) {
    aux = array[i]
    j = i - 1
    while (j >= 0 && array[j] > aux) {
      array[j + 1] = array[j]
      j--
    }
    array[j + 1] = aux
  }
}
```
### Shell
El algoritmo de [Shell](https://es.wikipedia.org/wiki/Ordenamiento_Shell) es una mejora del algoritmo de inserción. Su eficiencia es de O(n log n). Este método consiste en ordenar los elementos de un array de forma que los elementos que están lejos entre sí se ordenan primero.
En lugar de comparar elementos adyacentes, se comparan elementos que están separados por un intervalo. Este intervalo se va reduciendo hasta que llega a 1. Este método es más eficiente que el método de inserción, ya que en cada iteración, se comparan elementos que están separados por un intervalo. En definitiva, en cada iteración, se selecciona el menor elemento del subvector no ordenado y se intercambia con el primer elemento de este subvector.
- https://www.youtube.com/watch?v=yn0EgXHb5jc


![imagen](https://upload.wikimedia.org/wikipedia/commons/d/d8/Sorting_shellsort_anim.gif)

```kotlin
fun shell(array: IntArray) {
  var aux: Int
  var j: Int
  var intervalo = 1
  while (intervalo < array.size) {
    intervalo = intervalo * 3 + 1
  }
  while (intervalo > 0) {
    for (i in intervalo until array.size) {
      aux = array[i]
      j = i
      while (j > intervalo - 1 && array[j - intervalo] >= aux) {
        array[j] = array[j - intervalo]
        j -= intervalo
      }
      array[j] = aux
    }
    intervalo = (intervalo - 1) / 3
  }
}
```

### QuickSort
El algoritmo de [quicksort](https://es.wikipedia.org/wiki/Quicksort) es el algoritmo más rápido que veremos. Su eficiencia es de O(n log n). Consiste en dividir el array en dos partes, una con los elementos menores que el pivote y otra con los elementos mayores que el pivote. Luego se ordenan las dos partes de forma recursiva.
Trabaja de la siguiente manera:
1. Se toma un elemento arbitrario del vector, al que denominaremos pivote (p).
2. Se divide el vector de tal forma que todos los elementos a la izquierda del pivote sean menores que él, mientras que los que quedan a la derecha son mayores que él.
3. Ordenamos, por separado, las dos zonas delimitadas por el pivote.
4. Es recursivo, de ahí su gran ventaja. Repetimos el proceso recursivamente conc ad aparte, hasta que al salir todas las llamadas tenemos el vector completo.
- https://www.youtube.com/watch?v=ywWBy6J5gz8

![imagen](https://upload.wikimedia.org/wikipedia/commons/6/6a/Sorting_quicksort_anim.gif)

![imagen](https://lizardorodriguez.files.wordpress.com/2012/06/ordenamiento-quicksort.png)

```kotlin
fun pivote(array: IntArray, izq: Int, der: Int): Int {
  var i = izq
  var j = der
  var pivote = array[izq]
  while (i < j) {
    while (array[i] <= pivote && i < j) {
      i++
    }
    while (array[j] > pivote) {
      j--
    }
    if (i < j) {
      val aux = array[i]
      array[i] = array[j]
      array[j] = aux
    }
  }
  array[izq] = array[j]
  array[j] = pivote
  return j
}

fun quicksort(array: IntArray, izq: Int, der: Int) {
  var piv: Int
  if (izq < der) {
    piv = pivote(array, izq, der)
    quicksort(array, izq, piv - 1)
    quicksort(array, piv + 1, der)
  }
}

```

## Métodos de búsqueda
Los métodos de búsqueda nos servirán para encontrar un elemento en un array.

Puedes visualizarlos [aquí](https://www.cs.usfca.edu/~galles/visualization/Algorithms.html)

### Búsqueda secuencial
La búsqueda [secuencial o lineal](https://es.wikipedia.org/wiki/B%C3%BAsqueda_lineal) consiste en recorrer el vector hasta devolver el elemento buscado. Su eficiencia es de O(n). Es el método más sencillo de búsqueda, pero no es el más eficiente.
- https://www.youtube.com/watch?v=-PuqKbu9K3U

![imagen](https://jorgecontrerasp.files.wordpress.com/2012/06/d1.png)

```kotlin
fun busquedaSecuencial(array: IntArray, elemento: Int): Int {
  for (i in array.indices) {
    if (array[i] == elemento) {
      return i
    }
  }
  return -1
}
```

### Búsqueda binaria
En la [búsqueda binaria](https://es.wikipedia.org/wiki/B%C3%BAsqueda_binaria) partimos de un array ordenado. Su eficiencia es de O(n log n).
Se compara el dato buscado con el elemento en el centro del vector:
1. Si coinciden, hemos encontrado el dato buscado.
2. Si el dato es mayor que el elemento central del vector, tenemos que buscar el dato en segunda mitad del vector (mejor recursivamente).
3. Si el dato es menor que el elemento central del vector, tenemos que buscar el dato en la primera mitad del vector (mejor recursivamente).
- https://www.youtube.com/watch?v=iP897Z5Nerk

![imagen](http://2.bp.blogspot.com/-t8Ra7s0Usvc/TthYTUMUbvI/AAAAAAAAAFA/Ztuh8WzYqaE/s1600/secuen.jpg)

```kotlin
// Versión Iterativa
fun busquedaBinariaIterativa(array: IntArray, elemento: Int): Int {
  var centro: Int
  var inf = 0
  var sup = array.size - 1
  while (inf <= sup) {
    centro = (sup + inf) / 2
    if (array[centro] == elemento) {
      return centro
    } else if (elemento < array[centro]) {
      sup = centro - 1
    } else {
      inf = centro + 1
    }
  }
  return -1
}
// versión recursiva
fun busquedaBinariaRecursiva(array: IntArray, elemento: Int, inf: Int, sup: Int): Int {
  if (inf > sup) {
    return -1
  }
  val centro = (sup + inf) / 2
  return if (array[centro] == elemento) {
    centro
  } else if (elemento < array[centro]) {
    busquedaBinariaRecursiva(array, elemento, inf, centro - 1)
  } else {
    busquedaBinariaRecursiva(array, elemento, centro + 1, sup)
  }
}
```
## Gestión de proyectos con Gradle
Gradle es una herramienta de automatización de proyectos, que nos permite gestionar las dependencias de nuestro proyecto, compilarlo, ejecutarlo, etc. Gradle utiliza un fichero de configuración llamado build.gradle.kts, donde se especifican las tareas que queremos que realice. Este fichero se encuentra en la raíz del proyecto.

Importante, cada vez que modifiquemos el fichero build.gradle.kts, tenemos que sincronizar el proyecto para que se apliquen los cambios. Para ello, tenemos que pulsar en el botón Sync Now que aparece en la parte superior derecha de la ventana de Gradle.

### Plugins
Los plugins son módulos que nos permiten añadir funcionalidades a nuestro proyecto. Para añadir un plugin, tenemos que añadir la siguiente línea al fichero build.gradle.kts:
```kotlin
plugins {
  kotlin("jvm") version "1.9.0"
}
```

### Dependencias
Las dependencias son librerías que utilizamos en nuestro proyecto. Para añadir una dependencia, tenemos que añadir la siguiente línea al fichero build.gradle.kts. Tenemos dependencias de distintos tipos:
- **implementation**: Son las dependencias que se incluyen en el proyecto.
- **testImplementation**: Son las dependencias que se incluyen en los test.
- **testRuntimeOnly**: Son las dependencias que se incluyen en tiempo de ejecución de los test.

```kotlin
dependencies {
  implementation(kotlin("stdlib"))
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
}
```

### Creación de jar ejecutable (fat jar)
Para crear un jar ejecutable, tenemos que añadir la siguiente tarea al fichero build.gradle.kts:
```kotlin
// tarea para el fat jar
tasks.jar {
    manifest {
        // definimos el punto de entrada del manifest
        // Es la clase que contiene el método main que queremos ejecutar
        // Recuerda que no se pone el punto del fichero, si no en mayusuculas
        // Tampoco se pone .class
        attributes["Main-Class"] = "MainKt"
    }
    // Añadimos las dependencias de compilación al jar
    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
    // configuramos la estrategia de duplicados
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}
```

### Ejecución de tareas
Para ejecutar una tarea, tenemos que ejecutar el siguiente comando:
```bash
./gradlew nombreTarea
```
Por ejemplo, para ejecutar la tarea jar, ejecutamos el siguiente comando:
```bash
./gradlew jar
```

### Instalando la primera dependencia
Vamos a instalar la primera dependenciapara manejar la consola de una forma más bonita, es decir darle color, etc. Para ello, usaremos [Mordant](https://github.com/ajalt/mordant), vamos a añadir la siguiente dependencia al fichero build.gradle.kts:
```kotlin
dependencies {
  // ...
    implementation("com.github.ajalt.mordant:mordant:2.0.0-beta9")
}
```

Recuerda revisar la versión en su [página de GitHub](https://github.com/ajalt/mordant) o en [Maven](https://mvnrepository.com/artifact/com.github.ajalt.mordant/mordant).


## Autor

Codificado con :sparkling_heart: por [José Luis González Sánchez](https://twitter.com/JoseLuisGS_)

[![Twitter](https://img.shields.io/twitter/follow/JoseLuisGS_?style=social)](https://twitter.com/JoseLuisGS_)
[![GitHub](https://img.shields.io/github/followers/joseluisgs?style=social)](https://github.com/joseluisgs)
[![GitHub](https://img.shields.io/github/stars/joseluisgs?style=social)](https://github.com/joseluisgs)

### Contacto

<p>
  Cualquier cosa que necesites házmelo saber por si puedo ayudarte 💬.
</p>
<p>
 <a href="https://joseluisgs.dev" target="_blank">
        <img src="https://joseluisgs.github.io/img/favicon.png" 
    height="30">
    </a>  &nbsp;&nbsp;
    <a href="https://github.com/joseluisgs" target="_blank">
        <img src="https://distreau.com/github.svg" 
    height="30">
    </a> &nbsp;&nbsp;
        <a href="https://twitter.com/JoseLuisGS_" target="_blank">
        <img src="https://i.imgur.com/U4Uiaef.png" 
    height="30">
    </a> &nbsp;&nbsp;
    <a href="https://www.linkedin.com/in/joseluisgonsan" target="_blank">
        <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/c/ca/LinkedIn_logo_initials.png/768px-LinkedIn_logo_initials.png" 
    height="30">
    </a>  &nbsp;&nbsp;
    <a href="https://g.dev/joseluisgs" target="_blank">
        <img loading="lazy" src="https://googlediscovery.com/wp-content/uploads/google-developers.png" 
    height="30">
    </a>  &nbsp;&nbsp;
<a href="https://www.youtube.com/@joseluisgs" target="_blank">
        <img loading="lazy" src="https://upload.wikimedia.org/wikipedia/commons/e/ef/Youtube_logo.png" 
    height="30">
    </a>  
</p>

## Licencia de uso

Este repositorio y todo su contenido está licenciado bajo licencia **Creative Commons**, si desea saber más, vea
la [LICENSE](https://joseluisgs.dev/docs/license/). Por favor si compartes, usas o modificas este proyecto cita a su
autor, y usa las mismas condiciones para su uso docente, formativo o educativo y no comercial.

<a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/4.0/"><img alt="Licencia de Creative Commons" style="border-width:0" src="https://i.creativecommons.org/l/by-nc-sa/4.0/88x31.png" /></a><br /><span xmlns:dct="http://purl.org/dc/terms/" property="dct:title">
JoseLuisGS</span>
by <a xmlns:cc="http://creativecommons.org/ns#" href="https://joseluisgs.dev/" property="cc:attributionName" rel="cc:attributionURL">
José Luis González Sánchez</a> is licensed under
a <a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/4.0/">Creative Commons
Reconocimiento-NoComercial-CompartirIgual 4.0 Internacional License</a>.<br />Creado a partir de la obra
en <a xmlns:dct="http://purl.org/dc/terms/" href="https://github.com/joseluisgs" rel="dct:source">https://github.com/joseluisgs</a>.